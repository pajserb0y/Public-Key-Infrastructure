import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Credentials } from '../model/credentials';
import { Observable, throwError } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';
import { User } from '../model/user';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import { Certificate } from '../model/certificate';


const headers = { 'content-type': 'application/json'} 

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private _baseUrl = 'https://localhost:8080/';
  private _registration = this._baseUrl + 'auth/register';
  private _login = this._baseUrl + 'auth/login';  
  private _blackList = this._baseUrl + 'auth/password/blackList/';
  private _forgotPassword  = this._baseUrl + 'auth/newPassword';
  private _editClient  = this._baseUrl + 'auth/update';
  private _clientByUsername  = this._baseUrl + 'auth/email/';
  private _sendPasswordless = this._baseUrl + 'auth/sso/';
  private _send2factor = this._baseUrl + 'auth/2factorAuth/pin/send';
  private _loginPaswordless = this._baseUrl + 'auth/login/passwordless?token=';
  
  
  constructor(private _http: HttpClient) { }

 
  loginPasswordless(token: any): Observable<any> {
    return this._http.post(this._loginPaswordless + token, {},{responseType: 'text'})
  } 
  
  sendPasswordless(email: string): Observable<any> {
    return this._http.get<any>(this._sendPasswordless + email)
                           .pipe(tap(data =>  console.log('Iz service-a: ', data)),                         
                                catchError(this.handleError)); 
  }

  send2factor(credentials: Credentials): Observable<any> {
    const body=JSON.stringify(credentials);
    console.log(body)
    return this._http.post(this._send2factor, body)
  }

  getUserByEmail(username: string): Observable<User> {
    return this._http.get<User>(this._clientByUsername + username)
                           .pipe(tap(data =>  console.log('Iz service-a: ', data)),                         
                                catchError(this.handleError)); 
  }

  editUser(client: User) : Observable<any> {
    const body=JSON.stringify(client);
    console.log(body)
    return this._http.post(this._editClient, body)
  }

  forgotPassword(username: string) : Observable<any> {
    return this._http.put<any>(this._forgotPassword, {'email': username})
                  .pipe(tap(data =>  console.log('All: ' + JSON.stringify(data))),
                  catchError(this.handleError)); 
  }

  checkBlackListPass(pass: string): Observable<any> { 
    return this._http.get<any>(this._blackList + pass, {responseType: 'text' as 'json'})
                          .pipe(tap(data =>  console.log('All: ' + JSON.stringify(data))),
                            catchError(this.handleError)); 
  }

  registerUser(user: User) : Observable<any> {
    const body=JSON.stringify(user);
    console.log(body)
    return this._http.post(this._registration, body)
  }  

  logIn(credentials: Credentials): Observable<any> {
    const body=JSON.stringify(credentials);
    console.log(body)
    return this._http.post(this._login, body, {responseType: 'text'})
  }

  private handleError(err : HttpErrorResponse) {
    console.log(err.message);
    return Observable.throw(err.message);
    throw new Error('Method not implemented.');
  }
}
