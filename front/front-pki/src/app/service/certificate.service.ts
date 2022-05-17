import { Injectable } from '@angular/core';
import { map, catchError, tap } from 'rxjs/operators';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import { Certificate } from '../model/certificate';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {



  private _certificates = 'http://localhost:8080/certificates';
  private _allCertificatesForUser = this._certificates + '/allCertificatesForUser/';
  private _createCertificate = this._certificates + '/createCertificates';
  private _getAllCACertificates = this._certificates + '/getAllCACertificates';
  private _allCertificates = this._certificates + '/getAllCertificates'
  private _submitCertificate = this._certificates + '/newCertificate';
  private _revokeCertificate = this._certificates + '/revoke/';
  private _downloadCertificate = this._certificates + '/downloadCertificate';
  constructor(private _http: HttpClient) { }



  createCertificate(certificate: Certificate) : Observable<any> {
    const body=JSON.stringify(certificate);
    console.log(body)
    return this._http.post(this._createCertificate, body)
  }


  getAllCertificatesForUser(email : String): Observable<Certificate[]> {
    return this._http.get<Certificate[]>(this._allCertificatesForUser + email)
                      .pipe(tap(data =>  console.log('Iz service-a: ', data)),
                      catchError(this.handleError));
  }

  getAllCACertificates() : Observable<Certificate[]> {
    return this._http.get<Certificate[]>(this._getAllCACertificates)
    .do(data =>  console.log('All: ' + JSON.stringify(data)))
    .catch(this.handleError);
  }

  submitCertificate(certificate : Certificate) : Observable<any> {
    const body=JSON.stringify(certificate);
    console.log(body)
    return this._http.post(this._submitCertificate, body)
  }

  revokeCertificate(certificate: Certificate) : Observable<any>{
    const body=JSON.stringify(certificate);
    console.log(body);
    return this._http.post(this._revokeCertificate + certificate.serialNumber, body)
  }

  downloadCertificate(certificate: Certificate) {
    const body=JSON.stringify(certificate);
    console.log(body);
    return this._http.post(this._downloadCertificate, body)
  }

  getAllCertificates()
  {
    return this._http.get<Certificate[]>(this._allCertificates)
                      .pipe(tap(data =>  console.log('Iz service-a: ', data)),
                      catchError(this.handleError));
  }

  private handleError(err : HttpErrorResponse) {
    console.log(err.message);
    return Observable.throw(err.message);
    throw new Error('Method not implemented.');
  }
}
