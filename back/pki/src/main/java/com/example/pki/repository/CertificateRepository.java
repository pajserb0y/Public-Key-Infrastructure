package com.example.pki.repository;

import com.example.pki.model.CertificateInDatabase;
import com.example.pki.model.dto.CertificateDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CertificateRepository extends JpaRepository<CertificateInDatabase, Long> {

    CertificateInDatabase findBySubjectAlias(String issuerAlias);

    @Query("SELECT c.subjectAlias FROM CertificateInDatabase c WHERE c.serialNumber = :sn")
    String getAliasFromSerialNumber(@Param("sn") String serialNumber);

    @Modifying
    @Query("UPDATE CertificateInDatabase s SET s.isRevoked = true WHERE s.subjectAlias = :alias")
    void revokeCertificate(@Param("alias") String alias);


    @Query("SELECT c.subjectAlias FROM CertificateInDatabase c WHERE c.serialNumber = :serialNumber")
    String getAliasForCertificate(@Param("serialNumber") String serialNumber);
    
    @Query("SELECT c FROM CertificateInDatabase c WHERE c.type = 1 OR c.type = 2 AND cast(NOW() as timestamp) BETWEEN c.startDate and c.endDate AND s.isRevoked = false")
    List<CertificateInDatabase> findAllCAs();

    @Query("SELECT c FROM CertificateInDatabase c WHERE c.e = :e")
    List<CertificateInDatabase> getByE(@Param("e") String email);

    @Query("SELECT c FROM CertificateInDatabase c WHERE c.isRevoked = false")
    List<CertificateInDatabase> findAllNotRevoked();
}
