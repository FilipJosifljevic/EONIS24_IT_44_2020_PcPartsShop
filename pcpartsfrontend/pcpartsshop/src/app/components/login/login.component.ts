import { Component } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Router } from '@angular/router';
import { UserService } from '../../services/UserService';
import {AuthService} from "../../services/AuthService";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) { }

  login() {
    this.authService.login(this.email, this.password).subscribe();
    this.router.navigate(['/products']);
  }
}
