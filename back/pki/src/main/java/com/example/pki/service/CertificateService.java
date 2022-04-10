package com.example.pki.service;

import com.example.pki.model.data.CertificateDataDTO;

import java.security.KeyStoreException;
import java.security.cert.CertificateEncodingException;
import java.util.List;

public interface CertificateService {
    void issueCertificate(CertificateDataDTO certificateDataDTO);

    List<CertificateDataDTO> getAll();

    void revoke(String alias) throws KeyStoreException, CertificateEncodingException;
}
