package com.example.pki.repository;

import com.example.pki.model.CertificateInDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CertificateRepository extends JpaRepository<CertificateInDatabase, Long> {

    CertificateInDatabase findBySubjectAlias(String issuerAlias);

    @Modifying
    @Query("UPDATE CertificateInDatabase s SET s.isRevoked = true WHERE s.subjectAlias = :alias")
    void revokeCertificate(@Param("alias") String alias);

    @Query("SELECT c FROM CertificateInDatabase c WHERE c.type = 1 or c.type = 2")
    List<CertificateInDatabase> findAllCAs();

    List<CertificateInDatabase> findByE(String email);
}
