import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  client: User = {
    id: 0,
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    jwtToken: '',
    role: ''   
};
repassword: string = "";
errorMessage : string  = '';
role: string|null = localStorage.getItem('roles');
oldPassword:string = ""
wantToChangePassword : boolean = false;

  constructor(public _userService: UserService, private router: Router, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.getUserByUsername();
  }

  changeWantToChangePassword() {
    this.wantToChangePassword = !this.wantToChangePassword
  }

  edit() {
      if(!this.wantToChangePassword)
        this.client.password = this.oldPassword
      this._userService.editUser(this.client)
          .subscribe(data => {
            console.log('Dobio: ', data)
            if(data == null)
              this._snackBar.open('Incorrect filling of form or someone edited before you! Check and send again edit request', 'Close', {duration: 5000});
            else
              this.client = data
              this.router.navigateByUrl('/')
            },
          error => this.errorMessage = <any>error); 

      console.log(this.client);
      this._snackBar.open('Successfully edited', 'Close', {duration: 5000});     
  }


  getUserByUsername()
  {
      this._userService.getUserByEmail(localStorage.getItem('email') || '')
      .subscribe(data => {
                  this.client = data
                  this.oldPassword = data.password
                  this.client.password = ""
                  this.repassword = ''
                  
                  console.log('Dobio: ', data)},
                error => this.errorMessage = <any>error);     
  }

}
