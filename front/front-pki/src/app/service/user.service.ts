import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Credentials } from '../model/credentials';
import { Observable } from 'rxjs';
import { User } from '../model/user';

const headers = { 'content-type': 'application/json'} 

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private _baseUrl = 'https://localhost:8080/';
  private _registration = this._baseUrl + 'api/register';
  private _login = this._baseUrl + 'auth/login';  


  constructor(private _http: HttpClient) { }

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
    throw new Error(err.message);
  } 
}
