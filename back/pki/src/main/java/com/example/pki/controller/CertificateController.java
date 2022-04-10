package com.example.pki.controller;

import com.example.pki.mapper.CertificateAdapter;
import com.example.pki.model.data.CertificateDataDTO;
import com.example.pki.model.dto.CertificateDTO;
import com.example.pki.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.KeyStoreException;
import java.security.cert.CertificateEncodingException;
import java.util.List;

@RestController
@RequestMapping(value = "certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/newCertificate")
    public ResponseEntity<?> newCertificates(@RequestBody CertificateDTO certificateDTO) {
        //certificateService.setDataGenerator(rootIntermediateDataGenerator);
        CertificateDataDTO certificateDataDTO = CertificateAdapter.covertDtoToDataDto(certificateDTO);
        certificateService.issueCertificate(certificateDataDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAllCertificates")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(certificateService.getAll(), HttpStatus.OK);
    }

    @PutMapping("/revoke/{serialNumber}")
    public ResponseEntity<?> revoke(@PathVariable String serialNumber) throws CertificateEncodingException, KeyStoreException {
        certificateService.revoke(serialNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INTER_USER')")
    @GetMapping("/getAllCACertificates")
    public ResponseEntity<List<CertificateDTO>> getAllCACertificates() {
        return new ResponseEntity<List<CertificateDTO>>(certificateService.getAllCACertificates(), HttpStatus.OK);
    }


    @PostMapping("/allCertificatesForUser/{email}")
    public ResponseEntity<List<CertificateDTO>> allCertificatesForUser(@PathVariable String email) {
        return new ResponseEntity<List<CertificateDTO>>(certificateService.allCertificatesForUser(email), HttpStatus.OK);
    }
}
