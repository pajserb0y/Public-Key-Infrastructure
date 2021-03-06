package com.example.pki.model.data;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDataDTO {
    private String issuerAlias;
    private String cn;
    private String on;
    private String ou;
    private String surname;
    private String givenName;
    private String o;
    private String c;
    private String e;
    private String s;
//    @JsonIgnore
    private String subjectAlias;
    private Date startDate;
    private Date endDate;
    private String keyPass;
    private List<Boolean> keyUsages;
    private Integer type;   //1-root, 2-inter, 3-end

}
