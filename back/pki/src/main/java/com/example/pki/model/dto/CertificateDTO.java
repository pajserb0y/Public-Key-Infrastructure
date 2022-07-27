package com.example.pki.model.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDTO {

    private String issuer;
    private String serialNumber;
    private String country;
    private String organizationUnit;
    private String organization;
    private String surname;
    private String commonName;
    private String email;
    private Date startDate;
    private Date endDate;
    private KeyUsageDTO keyUsage;
    private Integer certificateType;

}