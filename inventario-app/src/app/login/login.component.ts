import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_URLS } from '../../config/api-urls';
import { LoginResponse } from '../models/login-response.model';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private http: HttpClient,private router: Router) {}

  onSubmit() {
    let loginData = {
      email: this.email,
      password: this.password
    };

    this.http.post<LoginResponse>(API_URLS.LOGIN, loginData).subscribe(response => {
      if (response.status === 200) {
        let userData = response.data;
        localStorage.setItem('session','true')
        localStorage.setItem('userToken', userData.token);
        localStorage.setItem('userName', userData.name);
        localStorage.setItem('userEmail', userData.email);
        localStorage.setItem('userPhone', userData.phone);

        Swal.fire({
          icon: 'success',
          title: response.message,
          text: 'Welcome ' + userData.name + '!',
        }).then(() => {
          this.router.navigate(['/home']);
          });
      } else if (response.status === 401) {
        Swal.fire({
          icon: 'error',
          title: 'Login Failed',
          text: response.message,
        });
      }
    }, error => {
      Swal.fire({
        icon: 'error',
        title: 'Login Failed',
        text: 'Error en la solicitud: ' + error.message,
      });
    });
  }
}
