package com.example.pki.mapper;

import com.example.pki.model.CertificateInDatabase;
import com.example.pki.model.data.CertificateDataDTO;
import com.example.pki.model.dto.CertificateDTO;
import com.example.pki.model.dto.KeyUsageDTO;
import org.bouncycastle.asn1.x509.KeyUsage;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

public class CertificateAdapter {
    public static List<CertificateDataDTO> convertToDtoList(List<CertificateInDatabase> certs) {
        List<CertificateDataDTO> dtos = new ArrayList<>();
        for (CertificateInDatabase cert : certs)
            dtos.add(covertToDto(cert));
        return dtos;
    }

    public static List<CertificateDTO> convertToCertDTOList(List<CertificateInDatabase> certs) {
        List<CertificateDTO> dtos = new ArrayList<>();
        for (CertificateInDatabase cert : certs)
            dtos.add(covertCertToDto(cert));
        return dtos;
    }


    private static CertificateDTO covertCertToDto(CertificateInDatabase cert) {
        CertificateDTO dto = new CertificateDTO();

        dto.setIssuer(cert.getIssuer().getSubjectAlias());
        dto.setSerialNumber(cert.getSerialNumber());
        dto.setCountry(cert.getC());
        dto.setOrganizationUnit(cert.getOu());
        dto.setOrganization(cert.getO());
        dto.setSurname(cert.getSurname());
        dto.setCommonName(cert.getCn());
        dto.setEmail(cert.getE());
        dto.setStartDate(cert.getStartDate());
        dto.setEndDate(cert.getEndDate());
        //dto.setKeyUsage(convertKeyUsageDTO(cert.getKeyUsage()));
        dto.setCertificateType(cert.getType());

        return dto;
    }

    private static CertificateDataDTO covertToDto(CertificateInDatabase cert) {
        CertificateDataDTO dto = new CertificateDataDTO();

        dto.setC(cert.getC());
        dto.setCn(cert.getCn());
        dto.setE(cert.getE());
        dto.setGivenName(cert.getGivenName());
        dto.setEndDate(cert.getEndDate());
        if(cert.getIssuer() == null)
            dto.setIssuerAlias(cert.getSubjectAlias());
        else
            dto.setIssuerAlias(cert.getIssuer().getSubjectAlias());
        dto.setJksPass(cert.getJksPass());
        dto.setO(cert.getO());
        dto.setOn(cert.getO());
        dto.setOu(cert.getOu());
        dto.setStartDate(cert.getStartDate());
        dto.setSubjectAlias(cert.getSubjectAlias());
        dto.setSurname(cert.getSurname());

        return dto;
    }

    public static CertificateDataDTO covertDtoToDataDto(CertificateDTO certDTO) {
        CertificateDataDTO dto = new CertificateDataDTO();

        dto.setC(certDTO.getCountry());
        dto.setCn(certDTO.getCommonName());
        dto.setE(certDTO.getEmail());
        dto.setGivenName(certDTO.getCommonName());
        dto.setEndDate(certDTO.getEndDate());
        dto.setIssuerAlias(certDTO.getIssuer());
        dto.setJksPass("");
        dto.setO(certDTO.getOrganization());
        dto.setOn(certDTO.getOrganization());
        dto.setOu(certDTO.getOrganizationUnit());
        dto.setStartDate(certDTO.getStartDate());
        dto.setSubjectAlias("");
        dto.setSurname(certDTO.getSurname());
        dto.setKeyUsages(convertKeyUsage(certDTO.getKeyUsage()));

        return dto;
    }
    public static List<Integer> convertKeyUsage(KeyUsageDTO keyUsageDTO) {

        List<Integer> keyUsage = new ArrayList<>();
        if(keyUsageDTO.isCertificateSigning())
            keyUsage.add(4);
        if(keyUsageDTO.isCrlSign())
            keyUsage.add(2);
        if(keyUsageDTO.isDataEncipherment())
            keyUsage.add(16);
        if(keyUsageDTO.isDecipherOnly())
            keyUsage.add(32768);
        if(keyUsageDTO.isDigitalSignature())
            keyUsage.add(128);
        if(keyUsageDTO.isEncipherOnly())
            keyUsage.add(1);
        if(keyUsageDTO.isKeyAgreement())
            keyUsage.add(8);
        if(keyUsageDTO.isKeyEncipherment())
            keyUsage.add(32);
        if(keyUsageDTO.isNonRepudiation())
            keyUsage.add(64);

        return keyUsage;
    }

    public static KeyUsageDTO convertKeyUsageDTO(List<Integer> keyUsages) {

        KeyUsageDTO keyUsageDTO = null;
        for (Integer keyUsage : keyUsages) {
            if(keyUsage == 4)
                keyUsageDTO.setCertificateSigning(true);
            if(keyUsage == 2)
                keyUsageDTO.setCrlSign(true);
            if(keyUsage == 16)
                keyUsageDTO.setDataEncipherment(true);
            if(keyUsage == 32768)
                keyUsageDTO.setDecipherOnly(true);
            if(keyUsage == 128)
                keyUsageDTO.setDigitalSignature(true);
            if(keyUsage == 1 )
                keyUsageDTO.setEncipherOnly(true);
            if(keyUsage == 8)
                keyUsageDTO.setKeyAgreement(true);
            if(keyUsage == 32)
                keyUsageDTO.setKeyEncipherment(true);
            if(keyUsage == 64)
                keyUsageDTO.setNonRepudiation(true);
        }


        return keyUsageDTO;
    }

}
