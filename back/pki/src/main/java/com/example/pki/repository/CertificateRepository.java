package com.example.pki.repository;

import com.example.pki.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Certificate findBySubjectAlias(String issuerAlias);
}
