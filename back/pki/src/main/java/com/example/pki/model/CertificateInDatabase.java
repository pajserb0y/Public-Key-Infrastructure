package com.example.pki.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "certificates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateInDatabase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String serialNumber;
    private String cn;
    @Column(name = "org_name")
    private String on;
    private String ou;
    private String surname;
    private String givenName;
    private String o;
    private String c;
    private String e;
    private String s;
//    @JsonIgnore
    @Column(unique = true)
    private String subjectAlias;
    private Date startDate;
    private Date endDate;
    private String keyPass;
    private boolean isRevoked;
    private Integer type;

    @ManyToOne
    private CertificateInDatabase issuer;

}
