import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Certificate } from '../model/certificate';
import { CertificateService } from '../service/certificate.service'
import { DatePipe } from '@angular/common'
import { KeyUsage } from '../model/keyUsage';

@Component({
  selector: 'app-new-certificate',
  templateUrl: './new-certificate.component.html',
  styleUrls: ['./new-certificate.component.css']
})
export class NewCertificateComponent implements OnInit {

  CACertificates : Certificate[] = [];
  @Input() newCertRegime : boolean = true;
 @Input() newCertificate : Certificate = {
    issuer: '',
    serialNumber: '',
    country: '',
    organizationUnit: '',
    organization: '',
    surname: '',
    commonName: '',
    email: '',
    startDate: new Date(),
    endDate:  new Date(),
    keyUsage: {
      certificateSigning: false,
      crlSign: false,
      dataEncipherment: false,
      decipherOnly: false,
      digitalSignature: false,
      encipherOnly: false,
      keyAgreement: false,
      keyEncipherment: false,
      nonRepudiation: false,
    },
    certificateType: 1
  };
  
  role : string = localStorage.getItem('role') || '';
  errorMessage : string = '';

  minDate : Date = new Date();

  public createRootCertificateExtendedKeyUsageExtensionsForm = new FormGroup({});
  public createRootCertificateKeyUsageExtensionsForm = new FormGroup({});

  rangeTerm = new FormGroup({
    startDate: new FormControl(),
    endDate: new FormControl(),
  });

  constructor(  private formBuilder: FormBuilder, private _certificateService: CertificateService,public datepipe: DatePipe) {
  
   }

  ngOnInit(): void {
    this.initKeyUsageForm();
    this.initExtendedKeyUsageForm();
    this.getAllCACertificates();
  }

  checkAvailability()
  {
    
  }

  public initKeyUsageForm() {
    this.createRootCertificateKeyUsageExtensionsForm = this.formBuilder.group({
      keyUsage: this.formBuilder.group({
        certificateSigning: this.newCertRegime && this.newCertificate.certificateType == 1 ||  this.newCertificate.certificateType == 2 ? new FormControl(true) : new FormControl(false),
        crlSign: this.newCertRegime && this.newCertificate.certificateType == 1 ||  this.newCertificate.certificateType == 2 ? new FormControl(true) : new FormControl(false),
        dataEncipherment: new FormControl(false),
        decipherOnly: new FormControl(false),
        digitalSignature: new FormControl(false),
        encipherOnly: new FormControl(false),
        keyAgreement: new FormControl(false),
        keyEncipherment: new FormControl(false),
        nonRepudiation: new FormControl(false),
      }),
    });
    
  }

  private initExtendedKeyUsageForm() {
    this.createRootCertificateExtendedKeyUsageExtensionsForm = this.formBuilder.group({
      extendedKeyUsage: this.formBuilder.group({
        serverAuth: new FormControl(false),
        clientAuth: new FormControl(false),
        codeSigning: new FormControl(false),
        emailProtection: new FormControl(false),
        timeStamping: new FormControl(false),
        ocspSigning: new FormControl(false),
        dvcs: new FormControl(false),
      }),
    });
  }

  getAllCACertificates()  {
    this._certificateService.getAllCACertificates()
        .subscribe(CAcerts => this.CACertificates = CAcerts,
                    error => this.errorMessage = <any>error); 
  }

  submitCertificate(certificate : Certificate)
  {
    certificate.startDate = this.rangeTerm.value.startDate;
    certificate.endDate = this.rangeTerm.value.endDate;
    this._certificateService.submitCertificate(certificate)
      .subscribe(() => {},
        error => this.errorMessage = <any>error);
  }

  convertDate()
  { 
    this.newCertificate.startDate = this.datepipe.transform(this.newCertificate.startDate, 'yyyy-MM-dd') || '';
    this.newCertificate.endDate =this.datepipe.transform(this.newCertificate.endDate, 'yyyy-MM-dd') || '';
    this.newCertificate.endDate = this.newCertificate.endDate.split('T')[0];
    this.newCertificate.startDate = this.newCertificate.startDate.split('T')[0];
  }
}


