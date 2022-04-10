package com.example.pki.mapper;

import com.example.pki.model.CertificateInDatabase;
import com.example.pki.model.data.CertificateDataDTO;
import com.example.pki.model.dto.CertificateDTO;

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
        dto.setOrganization(cert.getOn());
        dto.setSurname(cert.getSurname());
        dto.setCommonName(cert.getCn());
        dto.setEmail(cert.getE());
        dto.setStartDate(cert.getStartDate());
        dto.setEndDate(cert.getEndDate());
        dto.setKeyUsage(null);
        dto.setCertificateType(cert.getType());

        return dto;
    }

    private static CertificateDataDTO covertToDto(CertificateInDatabase cert) {
        CertificateDataDTO dto = new CertificateDataDTO();

        dto.setC(cert.getC());
        dto.setCn(cert.getCn());
        dto.setE(cert.getE());
        dto.setGivenName(cert.getGivenName());
        dto.setEndDate(cert.getEndDate().toString());
        dto.setIssuerAlias(cert.getIssuer().getSubjectAlias());
        dto.setKeyPass(cert.getKeyPass());
        dto.setO(cert.getO());
        dto.setOn(cert.getOn());
        dto.setOu(cert.getOu());
        dto.setStartDate(cert.getStartDate().toString());
        dto.setSubjectAlias(cert.getSubjectAlias());
        dto.setSurname(cert.getSurname());

        return dto;
    }
}
