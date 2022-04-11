package com.example.pki.service;

import com.example.pki.model.data.CertificateDataDTO;
import com.example.pki.model.dto.CertificateDTO;
import com.example.pki.model.dto.KeyUsageDTO;
import org.springframework.core.io.Resource;

import java.security.KeyStoreException;
import java.security.cert.CertificateEncodingException;
import java.util.List;

public interface CertificateService {
    void issueCertificate(CertificateDataDTO certificateDataDTO, KeyUsageDTO keyUsage);

    List<CertificateDTO> getAll();

    void revoke(String alias) throws KeyStoreException, CertificateEncodingException;

    List<CertificateDTO> getAllValidCACertificates();

    List<CertificateDTO> allCertificatesForUser(String email);

    Resource getCertificateToDownload(String certToDownload);
}
