package com.example.pki.service;

import com.example.pki.model.data.CertificateDataDTO;

public interface CertificateService {
    void issueRootCertificate(CertificateDataDTO certificateDataDTO);
}
