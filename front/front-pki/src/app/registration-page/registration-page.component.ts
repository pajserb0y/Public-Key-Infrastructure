import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import { Router } from '@angular/router';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

const specialChars = /[`!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent implements OnInit {

  emailControl: FormControl = new FormControl('', [Validators.required, Validators.email]);
  matcher = new MyErrorStateMatcher();
  user: User = {
    id: 0,
    firstName: '',
    lastName: '',
    role: '',
    jwtToken: '',
    email : '',
    password : ''
  }
  repassword :string = '' ;
  errorMessage : string  = '';
  blackListPassMessage: string = '';
  isInBlackList: boolean = false;


  constructor(public _userService: UserService, private _snackBar: MatSnackBar, private router: Router) { }

  ngOnInit(): void {
  }

  submit(): void {
      this._userService.registerUser(this.user)
      .subscribe(
        data => {
            this.router.navigateByUrl('/').then(() => {
              this._snackBar.open('Registration request successfully submited!', 'Close', {duration: 3000});
              });       
        },
        error => {
          this._snackBar.open('User exists with same email.', 'Close', {duration: 5000});
          console.log('Error!', error)
        }
      )
  }

  checkPass() {
    this._userService.checkBlackListPass(this.user.password)
        .subscribe(data => {
          if (data == null)
            this.isInBlackList = false
          else {
            this.isInBlackList = true
            this.blackListPassMessage = data
          }
          console.log(this.blackListPassMessage);},
                    error => this.errorMessage = <any>error);
  }

  containAllCharacters(pass: string) {
    var res = specialChars.test(pass);
    return res
  }

  containSpace(username: string) {
    if(username.split('').includes(' '))
      return true
    return false
  }

}
