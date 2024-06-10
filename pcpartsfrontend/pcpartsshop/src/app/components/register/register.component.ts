import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {
  email: string = '';
  password: string = '';
  selectedRole: { name: string; value: string } | undefined;
  roles : any[] | undefined;

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit() {
    this.roles = [
      { name: 'Customer', value: 'CUSTOMER'},
      {name: 'Provider', value: 'PROVIDER'},
    ];
  }

  register() {
    // Check if a role is selected
    if (this.selectedRole) {
      const user = {
        email: this.email,
        password: this.password,
        role: this.selectedRole.value
      };
      console.log(user);
      const headers = new HttpHeaders().set('Content-Type', 'application/json');
      this.http.post('http://localhost:8081/register', user, { headers: headers }).subscribe({
        next: () => {
          console.log('Registration successful');
          this.router.navigate(['login']);
        },
        error: (error) => {
          console.error('Registration failed:', error);
        }
      });
    } else {
      console.log('Please select a role');
    }
  }
}
