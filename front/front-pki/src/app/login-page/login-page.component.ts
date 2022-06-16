import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Credentials } from '../model/credentials';
//import { CustomerService } from '../service/customer.service';
import jwt_decode from 'jwt-decode';
import { UserService } from '../service/user.service';
import { UserDto } from '../model/userDto';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  credentials: Credentials = {
    email: '',
    password: '',
    pin: ''
  }
  userDto: UserDto = {
    id: 0,
    firstName: '',
    lastName: '',
    role: '',
    jwtToken: '',
    email: ''
  }


  token: string = '';

  constructor(public _userService: UserService, private router: Router, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }


  submit(): void {
    this._userService.logIn(this.credentials).subscribe(      
      data => {
        localStorage.setItem('jwtToken', data)
        let tokeninfo = this.getDecodedAccessToken(data)
        if(tokeninfo == '') 
          this._snackBar.open(data, 'Close', {duration: 6000});  
        else {
          localStorage.setItem('email', tokeninfo.sub)
          localStorage.setItem('role', tokeninfo.role)
          console.log('Dobioooooooooooo: ', this.userDto)
          this.router.navigateByUrl('/all-certificates').then(() => {
            window.location.reload();
          });
        }
      },
      error => {
        console.log('Error!', error)
        this._snackBar.open(error.error.message, 'Close', {duration: 5000});
      }
    )
  }

  getDecodedAccessToken(token: string): any {
    try{
        return jwt_decode(token);
    }
    catch(Error){
        return '';
    }
  }

  sendPassword() {
    this._userService.forgotPassword(this.credentials.email).subscribe(
      data => {
      },
      error => {
        console.log('Error!', error)
        this._snackBar.open('You have to valid insert email first.', 'Close', {duration: 3000});
      }
      )
      this._snackBar.open('Your new password has been sent to your email. You have to change it when you login for the first time!', 'Close', {duration: 7000});
  }

  sendPasswordless() {
    this._userService.sendPasswordless(this.credentials.email).subscribe(
      data => {
      },
      error => {
        console.log('Error!', error)
        this._snackBar.open('You have to valid insert email first.', 'Close', {duration: 3000});
      }
      )
      this._snackBar.open('Your new password has been sent to your email. You have to change it when you login for the first time!', 'Close', {duration: 7000});
  }

  send2factor() {
    this._userService.send2factor(this.credentials).subscribe(
      data => {
        this._snackBar.open('Your new pin has been sent to your email.', 'Close', {duration: 7000});
      },
      error => {
        console.log('Error!', error)
        this._snackBar.open('You have to valid insert email and password first.', 'Close', {duration: 3000});
      }
      )
  }

}
