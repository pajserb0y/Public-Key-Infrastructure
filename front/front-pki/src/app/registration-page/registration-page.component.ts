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


  constructor(public _userService: UserService, private _snackBar: MatSnackBar, private router: Router) { }

  ngOnInit(): void {
  }

  submit(): void {
      this._userService.registerUser(this.user)
      .subscribe(
        data => {
          if(data == null)
            this._snackBar.open('User exists with same email.', 'Close', {duration: 5000});
          else {
            this.router.navigateByUrl('/').then(() => {
              this._snackBar.open('Registration request successfully submited!', 'Close', {duration: 3000});
              }); 
          }
        },
        error => console.log('Error!', error)
      )
  }

}
