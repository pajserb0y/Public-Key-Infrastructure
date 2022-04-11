package com.example.pki.controller;

import com.example.pki.mapper.CertificateAdapter;
import com.example.pki.model.data.CertificateDataDTO;
import com.example.pki.model.dto.CertificateDTO;
import com.example.pki.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.KeyStoreException;
import java.security.cert.CertificateEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/newCertificate")
    public ResponseEntity<?> newCertificate(@RequestBody CertificateDTO certificateDTO) {
        CertificateDataDTO certificateDataDTO = CertificateAdapter.covertDtoToDataDto(certificateDTO);
        certificateService.issueCertificate(certificateDataDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //Certificate Signing, Off-line CRL Signing, CRL Signing (06)
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
        return new ResponseEntity<>(certificateService.getAllValidCACertificates(), HttpStatus.OK);
    }


    @PostMapping("/allCertificatesForUser/{email}")
    public ResponseEntity<List<CertificateDTO>> allCertificatesForUser(@PathVariable String email) {
        return new ResponseEntity<>(certificateService.allCertificatesForUser(email), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_admin', 'ROLE_user')")
    @PostMapping("/download")
    public ResponseEntity<Resource> downloadCertificate(@RequestBody CertificateDTO certToDownload) {

        Resource file = null;

        try {
            file = certificateService.getCertificateToDownload(certToDownload);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"certificate.cer\"")
                    .body(file);
        } catch(Exception e){
            return ResponseEntity.badRequest()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"null\"")
                    .body(file);
        }
    }
}
