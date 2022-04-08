package com.example.pki.service;

import com.example.pki.certificates.CertificateGenerator;
import com.example.pki.model.data.CertificateDataDTO;
import com.example.pki.model.data.IssuerData;
import com.example.pki.model.data.SubjectData;
import com.example.pki.repository.CertificateRepository;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class CertificateServiceImpl implements CertificateService {
    @Autowired
    private CertificateRepository certificateRepository;


    @Override
    public void issueRootCertificate(CertificateDataDTO certificateDataDTO) {
        try{
            SubjectData subjectData = generateSubjectData(certificateDataDTO);

            KeyPair keyPairIssuer = generateKeyPair();
            IssuerData issuerData = generateIssuerData(keyPairIssuer.getPrivate());

            //Generise se sertifikat za subjekta, potpisan od strane issuer-a
            CertificateGenerator cg = new CertificateGenerator();
            X509Certificate cert = cg.generateCertificate(subjectData, issuerData);


            //Moguce je proveriti da li je digitalan potpis sertifikata ispravan, upotrebom javnog kljuca izdavaoca
            cert.verify(keyPairIssuer.getPublic());
            System.out.println("\nValidacija uspesna :)");

            //Ovde se desava exception, jer se validacija vrsi putem drugog kljuca
            KeyPair anotherPair = generateKeyPair();
            cert.verify(anotherPair.getPublic());
        } catch(CertificateException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            System.out.println("\nValidacija neuspesna :(");
            e.printStackTrace();
        }
    }

    private IssuerData generateIssuerData(PrivateKey issuerKey) {
        //TODO: pronaci issuer-a i upisati njegove podatke
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Nikola Luburic");
        builder.addRDN(BCStyle.SURNAME, "Luburic");
        builder.addRDN(BCStyle.GIVENNAME, "Nikola");
        builder.addRDN(BCStyle.O, "UNS-FTN");
        builder.addRDN(BCStyle.OU, "Katedra za informatiku");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "nikola.luburic@uns.ac.rs");
        //UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, "654321");

        //Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
        // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
        // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
        return new IssuerData(issuerKey, builder.build());  //TODO: privatan kljuc treba da bude u keystore-u
    }

    private SubjectData generateSubjectData(CertificateDataDTO dto) {
        try {
            KeyPair keyPairSubject = generateKeyPair();

            //Datumi od kad do kad vazi sertifikat
            SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = iso8601Formater.parse(dto.getStartDate());
            Date endDate = iso8601Formater.parse(dto.getEndDate());

            //Serijski broj sertifikata
            Random randNum = new Random();
            BigInteger serialNumber = new BigInteger(new BigInteger("1000000").bitLength(), randNum);
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
            builder.addRDN(BCStyle.UID, dto.getSubjectAlias().toString());
            builder.addRDN(BCStyle.PSEUDONYM, "root");

            //Kreiraju se podaci za sertifikat, sto ukljucuje:
            // - javni kljuc koji se vezuje za sertifikat
            // - podatke o vlasniku
            // - serijski broj sertifikata
            // - od kada do kada vazi sertifikat
            return new SubjectData(keyPairSubject.getPublic(), builder.build(), serialNumber.toString(), startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
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
