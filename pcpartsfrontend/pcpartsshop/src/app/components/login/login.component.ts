import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  login() {
    const user = { email: this.email, password: this.password };
    this.http.post<JSON>('http://localhost:8081/login', user).subscribe({
      next: () => {
        console.log('Login successful');
        this.router.navigate(['products']);
      },
      error: (error) => {
        console.error('Login failed:', error);
      }
    });
  }
}
