package com.example.pki.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {
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
    //    private String s;
//    @JsonIgnore
    @Column(unique = true)
    private String subjectAlias;
    private String startDate;
    private String endDate;
    private String jksPass;

    @ManyToOne
    private Certificate issuer;
}
