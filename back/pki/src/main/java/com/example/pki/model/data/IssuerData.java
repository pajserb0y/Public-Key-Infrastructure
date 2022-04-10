package com.example.pki.model.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bouncycastle.asn1.x500.X500Name;
//import sun.security.x509.X500Name;

import java.security.PrivateKey;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssuerData {
    private PrivateKey privateKey;
    private X500Name x500name;
}