import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import {MatSort, Sort} from '@angular/material/sort';

import { Certificate } from '../model/certificate';
import { CertificateService } from '../service/certificate.service';


@Component({
  selector: 'app-all-certificates',
  templateUrl: './all-certificates.component.html',
  styleUrls: ['./all-certificates.component.css']
})
export class AllCertificatesComponent implements OnInit {


  displayedColumns: string[] = ['serialNumber', 'subjectName', 'issuerName', 'validFrom', 'validTo', 'viewDetails','revoke','download'];
  errorMessage : string  = '';
  certificates: Certificate[] = []
 
  role : string|null = localStorage.getItem('role');
  

  constructor(private _certificateService: CertificateService, private router: Router, private _snackBar: MatSnackBar) { }
  email: string|null = localStorage.getItem('email');


  ngOnInit(): void {
    this.getAllCertificatesForUser(this.email)   

  }

  revoke(certificate :Certificate)
  {

  }

  viewDetails(certificate :Certificate)
  {

  }

  download(certificate :Certificate)
  {

  }

  getAllCertificatesForUser(email :String|null)
  {
    this._certificateService.getAllCertificatesForUser(email)
        .subscribe(data =>  this.certificates = data,
                   error => this.errorMessage = <any>error); 
  }

}
