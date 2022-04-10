package com.example.pki.repository;

import com.example.pki.model.CertificateInDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CertificateRepository extends JpaRepository<CertificateInDatabase, Long> {

    CertificateInDatabase findBySubjectAlias(String issuerAlias);

    @Modifying
    @Query("UPDATE CertificateInDatabase s SET s.isRevoked = true WHERE s.subjectAlias = :alias")
    void revokeCertificate(@Param("alias") String alias);

    @Query("SELECT c.subjectAlias FROM CertificateInDatabase c WHERE c.serialNumber = :serialNumber")
    String getAliasForCertificate(@Param("serialNumber") String serialNumber);
}
