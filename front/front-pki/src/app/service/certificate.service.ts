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


  private _certificates = '/api/certificates';
  private _allCertificatesForUser = this._certificates + '/allCertificatesForUser/';
  private _createCertificate = this._certificates + '/createCertificates';
  private _getAllCACertificates = this._certificates + '/getAllCACertificates';
  private _submitCertificate = this._certificates + '/newCertificate';

  constructor(private _http: HttpClient) { }



  createCertificate(certificate: Certificate) : Observable<any> {
    const body=JSON.stringify(certificate);
    console.log(body)
    return this._http.post(this._createCertificate, body)
  }


  getAllCertificatesForUser(username : String|null): Observable<Certificate[]> {
    return this._http.get<Certificate[]>(this._allCertificatesForUser + username)
                      .pipe(tap(data =>  console.log('Iz service-a: ', data)),
                      catchError(this.handleError));
  }

  getAllCACertificates() : Observable<Certificate[]> {
    return this._http.get<Certificate[]>(this._getAllCACertificates)
    .do(data =>  console.log('All: ' + JSON.stringify(data)))
    .catch(this.handleError);
  }

  submitCertificate(certificate : Certificate) {
    const body=JSON.stringify(certificate);
    console.log(body)
     this._http.post(this._submitCertificate, body)
  }

  private handleError(err : HttpErrorResponse) {
    console.log(err.message);
    return Observable.throw(err.message);
    throw new Error('Method not implemented.');
  }
}
