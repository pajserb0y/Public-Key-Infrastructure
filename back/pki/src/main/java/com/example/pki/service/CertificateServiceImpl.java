package com.example.pki.service;

import com.example.pki.certificates.CertificateGenerator;
import com.example.pki.keystores.KeyStoreReader;
import com.example.pki.keystores.KeyStoreWriter;
import com.example.pki.mapper.CertificateAdapter;
import com.example.pki.model.CertificateInDatabase;
import com.example.pki.model.data.CertificateDataDTO;
import com.example.pki.model.data.IssuerData;
import com.example.pki.model.data.SubjectData;
import com.example.pki.model.dto.CertificateDTO;
import com.example.pki.repository.CertificateRepository;
import com.example.pki.util.Base64Utility;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.security.cert.Certificate;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CertificateServiceImpl implements CertificateService {
    public static final String KEYSTORE_JKS_FILE_NAME = "keystore.jks";
    public static final String JKS_PASS = "pass";
    @Autowired
    private CertificateRepository certificateRepository;


    @Override
    public void issueCertificate(CertificateDataDTO certificateDataDTO) {
        certificateDataDTO.setIssuerAlias(certificateRepository.getAliasForCertificate(certificateDataDTO.getIssuerAlias()));
        KeyStoreReader reader = new KeyStoreReader();
        KeyPair keyPairIssuer = generateKeyPair();
        SubjectData subjectData = generateSubjectData(certificateDataDTO, keyPairIssuer.getPublic());
        IssuerData issuerData;
        if (certificateDataDTO.getType() == 1)
            issuerData = generateRootIssuerData(certificateDataDTO, keyPairIssuer.getPrivate());
        else
            issuerData = reader.readIssuerFromStore(KEYSTORE_JKS_FILE_NAME, certificateDataDTO.getIssuerAlias(), JKS_PASS.toCharArray(), certificateDataDTO.getKeyPass().toCharArray());

        //Generise se sertifikat za subjekta, potpisan od strane issuer-a
        CertificateGenerator cg = new CertificateGenerator();
        X509Certificate cert = cg.generateCertificate(subjectData, issuerData, certificateDataDTO.getKeyUsages());

        //Keystore
        KeyStoreWriter writer = new KeyStoreWriter();
        writer.loadKeyStore(KEYSTORE_JKS_FILE_NAME, JKS_PASS.toCharArray());
        writer.write(certificateDataDTO.getSubjectAlias(), keyPairIssuer.getPrivate(), certificateDataDTO.getKeyPass().toCharArray(), cert);
        writer.saveKeyStore(KEYSTORE_JKS_FILE_NAME, JKS_PASS.toCharArray());

        //Database
        SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");

        if (certificateDataDTO.getIssuerAlias() == null)   //root
            certificateRepository.save(new CertificateInDatabase(null, subjectData.getSerialNumber(), certificateDataDTO.getCn(), certificateDataDTO.getOn(), certificateDataDTO.getOu(),
                    certificateDataDTO.getSurname(), certificateDataDTO.getGivenName(), certificateDataDTO.getO(), certificateDataDTO.getC(), certificateDataDTO.getE(), certificateDataDTO.getS(),
                    certificateDataDTO.getSubjectAlias(), certificateDataDTO.getStartDate(), certificateDataDTO.getEndDate(), certificateDataDTO.getKeyPass(), false,
                    certificateDataDTO.getType(), null));
        else    //sub
            certificateRepository.save(new CertificateInDatabase(null, subjectData.getSerialNumber(), certificateDataDTO.getCn(), certificateDataDTO.getOn(), certificateDataDTO.getOu(),
                    certificateDataDTO.getSurname(), certificateDataDTO.getGivenName(), certificateDataDTO.getO(), certificateDataDTO.getC(), certificateDataDTO.getE(), certificateDataDTO.getS(),
                    certificateDataDTO.getSubjectAlias(), certificateDataDTO.getStartDate(), certificateDataDTO.getEndDate(), certificateDataDTO.getKeyPass(), false, certificateDataDTO.getType(),
                    certificateRepository.findBySubjectAlias(certificateDataDTO.getIssuerAlias())));

        //Moguce je proveriti da li je digitalan potpis sertifikata ispravan, upotrebom javnog kljuca izdavaoca

    }
    @Override
    public List<CertificateDTO> getAll() {
        return CertificateAdapter.convertToCertDTOList(certificateRepository.findAll());
    }

    @Override
    public void revoke(String serialNumber) throws KeyStoreException, CertificateEncodingException {
        KeyStoreWriter writer = new KeyStoreWriter();

        String alias = certificateRepository.getAliasForCertificate(serialNumber);

        writer.loadKeyStore(KEYSTORE_JKS_FILE_NAME, JKS_PASS.toCharArray());
        List<Certificate> subCerts = writer.findAllSubs(alias);
        writer.delete(alias);
        writer.saveKeyStore(KEYSTORE_JKS_FILE_NAME, JKS_PASS.toCharArray());
        certificateRepository.revokeCertificate(alias);

        if(subCerts.size() != 0)
            for(Certificate sub : subCerts) {
                X500Name issuer = new JcaX509CertificateHolder((X509Certificate) sub).getSubject();
                RDN subjectAliasRDN = issuer.getRDNs(BCStyle.UID)[0];
                String subjectAlias = IETFUtils.valueToString(subjectAliasRDN.getFirst().getValue());
                revoke(subjectAlias);
            }
    }

    @Override
    public List<CertificateDTO> getAllValidCACertificates() {
        List<CertificateInDatabase> validCerts = new ArrayList<>();
        KeyStoreReader reader = new KeyStoreReader();
        for(CertificateInDatabase cert : certificateRepository.findAllCAs()) {
            if(reader.isValid((X509Certificate) reader.readCertificate(KEYSTORE_JKS_FILE_NAME, JKS_PASS, cert.getSubjectAlias())))
                validCerts.add(cert);
        }

        return CertificateAdapter.convertToCertDTOList(validCerts);
    }

    @Override
    public List<CertificateDTO> allCertificatesForUser(String email) {
        return CertificateAdapter.convertToCertDTOList(certificateRepository.getByE(email));
    }

    @Override
    public Resource getCertificateToDownload(CertificateDTO certToDownload) {
        try {
            KeyStoreReader reader = new KeyStoreReader();
            X509Certificate cert = (X509Certificate) reader.readCertificate(KEYSTORE_JKS_FILE_NAME, JKS_PASS, certificateRepository.getAliasFromSerialNumber(certToDownload.getSerialNumber()));
            String digitalSignature = Base64Utility.encode(cert.getEncoded());

            return new ByteArrayResource(digitalSignature.getBytes());
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<CertificateInDatabase> filterValid(List<CertificateInDatabase> certificates) {
        ArrayList<CertificateInDatabase> result = new ArrayList<CertificateInDatabase>();

        for(CertificateInDatabase cert : certificates){
            // Odraditi proveru validnosti sertifikata
        }

        return result;
    }

    private IssuerData generateRootIssuerData(CertificateDataDTO dto, PrivateKey issuerKey) {
        //TODO: pronaci issuer-a i upisati njegove podatke
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, dto.getCn());
        builder.addRDN(BCStyle.SURNAME, dto.getSurname());
        builder.addRDN(BCStyle.GIVENNAME, dto.getGivenName());
        builder.addRDN(BCStyle.O, dto.getO());
        builder.addRDN(BCStyle.OU, dto.getOu());
        builder.addRDN(BCStyle.C, dto.getC());
        builder.addRDN(BCStyle.E, dto.getE());
        //UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, dto.getSubjectAlias());
        builder.addRDN(BCStyle.PSEUDONYM, "root");

        //Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
        // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
        // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
        IssuerData issuerData = new IssuerData(issuerKey, null);
        issuerData.setX500name(builder.build());
        return issuerData;
    }

    private SubjectData generateSubjectData(CertificateDataDTO dto, PublicKey publicKey) {
        //try {
            //KeyPair keyPairSubject = generateKeyPair();
//            byte[] array = new byte[7]; // length is bounded by 7
            Random random = new Random();
            dto.setSubjectAlias(random.ints(97, 123)
                    .limit(7)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString());

            //Datumi od kad do kad vazi sertifikat
            //SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
            //Date startDate = iso8601Formater.format(dto.getStartDate().toString());
            // Date endDate = iso8601Formater.parse(dto.getEndDate());

            //Serijski broj sertifikata
            Random randNum = new Random();
            BigInteger serialNumber = new BigInteger(new BigInteger("1000000").bitLength(), randNum);   //TODO: proveriti da li je serialNumber unique
            //klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
            X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
            builder.addRDN(BCStyle.CN, dto.getCn());
            builder.addRDN(BCStyle.SURNAME, dto.getSurname());
            builder.addRDN(BCStyle.GIVENNAME, dto.getGivenName());
            builder.addRDN(BCStyle.O, dto.getO());
            builder.addRDN(BCStyle.OU, dto.getOu());
            builder.addRDN(BCStyle.C, dto.getC());
            builder.addRDN(BCStyle.E, dto.getE());
            //UID (USER ID) je ID korisnika
            builder.addRDN(BCStyle.UID, dto.getSubjectAlias());
            if(dto.getType() == 1)
                builder.addRDN(BCStyle.PSEUDONYM, "root");
            else if (dto.getType() == 2)
                builder.addRDN(BCStyle.PSEUDONYM, "inter");
            else
                builder.addRDN(BCStyle.PSEUDONYM, "end");

            //Kreiraju se podaci za sertifikat, sto ukljucuje:
            // - javni kljuc koji se vezuje za sertifikat
            // - podatke o vlasniku
            // - serijski broj sertifikata
            // - od kada do kada vazi sertifikat
            return new SubjectData(publicKey, builder.build(), serialNumber.toString(), dto.getStartDate(), dto.getEndDate());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    //    return null;
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }


}
