package com.example.pki.controller;

import com.example.pki.model.data.CertificateDataDTO;
import com.example.pki.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
@RequestMapping(value = "certificates")
public class SertificateController {

    @Autowired
    private CertificateService certificateService;


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/issueRoot")
    public ResponseEntity<Void> issueRootCertificate(@RequestBody CertificateDataDTO certificateDataDTO) {
        //certificateService.setDataGenerator(rootIntermediateDataGenerator);
        certificateService.issueRootCertificate(certificateDataDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
