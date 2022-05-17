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
    password: ''
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
        localStorage.setItem('email', tokeninfo.id)
        localStorage.setItem('role', tokeninfo.role)
        console.log('Dobioooooooooooo: ', this.userDto)
        this.router.navigateByUrl('/all-certificates').then(() => {
          window.location.reload();
        });
      },
      error => {
        console.log('Error!', error)
        this._snackBar.open('Invalid username or password', 'Close', {duration: 3000});
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

}
