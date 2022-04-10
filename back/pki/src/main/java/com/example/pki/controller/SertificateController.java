package com.example.pki.controller;

import com.example.pki.model.data.CertificateDataDTO;
import com.example.pki.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.KeyStoreException;
import java.security.cert.CertificateEncodingException;

@RestController
@RequestMapping(value = "certificates")
public class SertificateController {

    @Autowired
    private CertificateService certificateService;


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/issueRoot")
    public ResponseEntity<?> issueRootCertificate(@RequestBody CertificateDataDTO certificateDataDTO) {
        //certificateService.setDataGenerator(rootIntermediateDataGenerator);
        certificateService.issueCertificate(certificateDataDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(certificateService.getAll(), HttpStatus.CREATED);
    }

    @PutMapping("/revoke/{alias}")
    public ResponseEntity<?> revoke(@PathVariable String alias) throws CertificateEncodingException, KeyStoreException {
        certificateService.revoke(alias);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
