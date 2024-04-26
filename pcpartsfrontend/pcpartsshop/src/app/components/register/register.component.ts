import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {
  username: string = '';
  password: string = '';
  selectedRole: string | undefined;
  roles : any[] | undefined;

  ngOnInit() {
    this.roles = [
      { name: 'Customer', value: 'CUSTOMER'},
      {name: 'Provider', value: 'PROVIDER'},
    ];
  }

  register() {
    // Check if a role is selected
    if (this.selectedRole) {
      console.log('Username:', this.username);
      console.log('Password:', this.password);
      console.log('SelectedRole:', this.selectedRole);

      // Implement your registration logic here
    } else {
      console.log('Please select a role');
    }
  }
}
