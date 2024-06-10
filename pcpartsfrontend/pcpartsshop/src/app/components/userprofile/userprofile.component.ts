import {Component, OnInit, Provider} from '@angular/core';
import {User} from "../../models/User";
import {UserService} from "../../services/UserService";
import {Observable} from "rxjs";
import {AuthService} from "../../services/AuthService";
import {HttpClient} from "@angular/common/http";
import {Customer} from "../../models/Customer";

@Component({
  selector: 'app-userprofile',
  templateUrl: 'userprofile.component.html',
  styleUrl: './userprofile.component.css'
})
export class UserprofileComponent implements OnInit{
  currentUser: User | null = null;
  updatedUser: User | null = null;
  isCustomer = false;
  isProvider = false;

  constructor(private userService : UserService, private authService: AuthService, private http: HttpClient) { }

  ngOnInit(): void {
    this.authService.currentUser$.subscribe((user:any) => {
      this.currentUser = user;
      this.updatedUser = { ...user };
      if (user) {
        this.isCustomer = user.role === 'CUSTOMER';
        this.isProvider = user.role === 'PROVIDER';
      }
    });
  }

  updateCustomer(userId: string,customerData: Customer): void {
    // Update the customer data in the backend
    this.userService.updateCustomer(userId,customerData).subscribe(updatedCustomer => {
      // Update the current user with the updated customer data
      this.currentUser = { ...updatedCustomer };
      this.updatedUser = { ...updatedCustomer };
    });
  }

  updateProvider(userId: string,providerData: Provider): void {
    // Update the provider data in the backend
    this.userService.updateProvider(userId,providerData).subscribe(updatedProvider => {
      // Update the current user with the updated provider data
      this.currentUser = { ...updatedProvider };
      this.updatedUser = { ...updatedProvider };
    });
  }
}
