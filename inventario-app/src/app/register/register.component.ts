import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_URLS } from '../../config/api-urls';
import { Router } from '@angular/router';
import { GenericResponse } from '../models/generic-response.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
name: string = '';
email: string = '';
phone: string = '';
password: string = '';

constructor(private http: HttpClient,private router: Router) {}
onSubmit() {
  let registerData = {
    name: this.name,
    email: this.email,
    phone: this.phone,
    password: this.password
  };

  this.http.post<GenericResponse>(API_URLS.REGISTER, registerData).subscribe(response => {
    if (response.status === 200) {
      Swal.fire({
        icon: 'success',
        title: 'Welcome to your inventory app',
        text: response.message +' , thanks for register!',
      }).then(() => {
        this.router.navigate(['/login']);
      });
    } else{
      Swal.fire({
        icon: 'error',
        title: 'Register Failed',
        text: response.message,
      });
    }
  }, error => {
    Swal.fire({
      icon: 'error',
      title: 'Register Failed',
      text: 'Error : ' + error.message,
    });
  });
}
}
