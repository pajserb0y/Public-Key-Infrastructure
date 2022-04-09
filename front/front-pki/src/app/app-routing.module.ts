import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllCertificatesComponent } from './all-certificates/all-certificates.component';
//import { UserProfileComponent } from './user-profile/user-profile.component';

import { LandingPageComponent } from './landing-page/landing-page.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { NewCertificateComponent } from './new-certificate/new-certificate.component';
import { RegistrationPageComponent } from './registration-page/registration-page.component'; 
import { AuthGuard } from './service/auth.guard';


const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'register', component: RegistrationPageComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'all-certificates', component: AllCertificatesComponent },
  { path: 'new-certificate', component: NewCertificateComponent },
  //{ path: 'user-profile', component: UserProfileComponent, canActivate: [AuthGuard], data: { role: ['ROLE_CUSTOMER', 'ROLE_INSTRUCTOR','ROLE_WEEKEND_HOUSE_OWNER','ROLE_BOAT_OWNER','ROLE_ADMIN']} },
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
