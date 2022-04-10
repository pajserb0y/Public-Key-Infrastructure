package com.example.pki.mapper;

import com.example.pki.model.CertificateInDatabase;
import com.example.pki.model.data.CertificateDataDTO;
import com.example.pki.model.dto.CertificateDTO;
import com.example.pki.model.dto.KeyUsageDTO;

import java.util.ArrayList;
import java.util.List;

public class CertificateAdapter {
    public static List<CertificateDataDTO> convertToDtoList(List<CertificateInDatabase> certs) {
        List<CertificateDataDTO> dtos = new ArrayList<>();
        for (CertificateInDatabase cert : certs)
            dtos.add(covertToDto(cert));
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
        dto.setJksPass(cert.getJksPass());
        dto.setO(cert.getO());
        dto.setOn(cert.getOn());
        dto.setOu(cert.getOu());
        dto.setStartDate(cert.getStartDate().toString());
        dto.setSubjectAlias(cert.getSubjectAlias());
        dto.setSurname(cert.getSurname());

        return dto;
    }

    public static CertificateDataDTO covertDtoToDataDto(CertificateDTO certDTO) {
        CertificateDataDTO dto = new CertificateDataDTO();

        dto.setC(certDTO.getCountry());
        dto.setCn(certDTO.getCommonName());
        dto.setE(certDTO.getEmail());
        dto.setGivenName("");
        dto.setEndDate(certDTO.getEndDate().toString());
        dto.setIssuerAlias(certDTO.getIssuer());
        dto.setJksPass("");
        dto.setO(certDTO.getOrganization());
        dto.setOn("");
        dto.setOu(certDTO.getOrganizationUnit());
        dto.setStartDate(certDTO.getStartDate().toString());
        dto.setSubjectAlias("");
        dto.setSurname(certDTO.getSurname());

        return dto;
    }
}
