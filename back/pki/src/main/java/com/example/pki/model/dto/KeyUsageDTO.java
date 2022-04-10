package com.example.pki.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyUsageDTO {

    private boolean certificateSigning;
    private boolean crlSign;
    private boolean dataEncipherment;
    private boolean decipherOnly;
    private boolean digitalSignature;
    private boolean encipherOnly;
    private boolean keyAgreement;
    private boolean keyEncipherment;
    private boolean nonRepudiation;

}
