import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Certificate } from '../model/certificate';
import { CertificateService } from '../service/certificate.service'


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

  constructor(  private formBuilder: FormBuilder, private _certificateService: CertificateService) {
  
   }

  ngOnInit(): void {
    this.initKeyUsageForm();
    this.initExtendedKeyUsageForm();
    this.getAllCACertificates();
    this.newCertificate.certificateType = 1;
  }

  checkAvailability()
  {
    
  }

  private initKeyUsageForm() {
    this.createRootCertificateKeyUsageExtensionsForm = this.formBuilder.group({
      keyUsage: this.formBuilder.group({
        certificateSigning: new FormControl(false),
        crlSign: new FormControl(false),
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
    this.convertDate()
    this._certificateService.submitCertificate(certificate)
      .subscribe(() => {},
        error => this.errorMessage = <any>error);
  }
}


