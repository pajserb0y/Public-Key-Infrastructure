import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import {MatSort, Sort} from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';

import { Certificate } from '../model/certificate';
import { CertificateService } from '../service/certificate.service';
import { DetailsDialogComponent } from '../details-dialog/details-dialog.component';
import { HttpErrorResponse } from '@angular/common/http';
import { saveAs } from 'file-saver';


@Component({
  selector: 'app-all-certificates',
  templateUrl: './all-certificates.component.html',
  styleUrls: ['./all-certificates.component.css']
})
export class AllCertificatesComponent implements OnInit {


  displayedColumns: string[] = ['serialNumber', 'subjectName', 'issuerName', 'validFrom', 'validTo', 'viewDetails','revoke','download'];
  errorMessage : string  = '';
  certificates: Certificate[] = []
 
  role : string = localStorage.getItem('role') || '';
  

  constructor(private _certificateService: CertificateService, private toastr: ToastrService, private router: Router, private _snackBar: MatSnackBar, public dialog: MatDialog) { }
  email: string = localStorage.getItem('email') || '' ;


  ngOnInit(): void {
    this.role == 'ROLE_ADMIN' ? this.getAllCertificates() : this.getAllCertificatesForUser(this.email)   

  }

  revoke(certificate :Certificate)
  {
    this._certificateService.revokeCertificate(certificate)
        .subscribe(data =>  this.certificates = data,
                   error => this.errorMessage = <any>error);
  }

  viewDetails(certificate :Certificate)
  {
    const dialogRef = this.dialog.open(DetailsDialogComponent, {
      width: '1000px',
      data: {certificate: certificate},
    });
  }

  /* download(certificate :Certificate)
  {
    this._certificateService.downloadCertificate(certificate)
    .subscribe(
      () => {
        this.toastr.success('Success!', 'Download certificate');
      },
      (e: HttpErrorResponse) => {
        this.toastr.error(e.error.message, 'Failed to download selected certificate');
      }
    );
  } */
  download(certificate : Certificate)
  {
    this._certificateService.downloadCertificate(certificate)
    .subscribe(
      blob => saveAs(blob, 'certificate.cer'),
      error => this.errorMessage = <any>error);
  } 

  getAllCertificatesForUser(email :String)
  {
    let email1 = localStorage.getItem('email') || '' ;
    this._certificateService.getAllCertificatesForUser(email1)
        .subscribe(data =>  this.certificates = data,
                   error => this.errorMessage = <any>error); 
  }

  getAllCertificates()
  {
    this._certificateService.getAllCertificates()
        .subscribe(data =>  this.certificates = data,
                   error => this.errorMessage = <any>error); 
  }
  

}
