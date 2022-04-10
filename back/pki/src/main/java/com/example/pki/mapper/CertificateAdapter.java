package com.example.pki.mapper;

import com.example.pki.model.CertificateInDatabase;
import com.example.pki.model.data.CertificateDataDTO;

import java.util.ArrayList;
import java.util.List;

public class CertificateAdapter {
    public static List<CertificateDataDTO> convertToDtoList(List<CertificateInDatabase> certs) {
        List<CertificateDataDTO> dtos = new ArrayList<>();
        for (CertificateInDatabase cert : certs)
            dtos.add(covertToDto(cert));
        return dtos;
    }

    private static CertificateDataDTO covertToDto(CertificateInDatabase cert) {
        CertificateDataDTO dto = new CertificateDataDTO();

        dto.setC(cert.getC());
        dto.setCn(cert.getCn());
        dto.setE(cert.getE());
        dto.setGivenName(cert.getGivenName());
        dto.setEndDate(cert.getEndDate());
        dto.setIssuerAlias(cert.getIssuer().getSubjectAlias());
        dto.setJksPass(cert.getJksPass());
        dto.setO(cert.getO());
        dto.setOn(cert.getOn());
        dto.setOu(cert.getOu());
        dto.setStartDate(cert.getStartDate());
        dto.setSubjectAlias(cert.getSubjectAlias());
        dto.setSurname(cert.getSurname());

        return dto;
    }
}
