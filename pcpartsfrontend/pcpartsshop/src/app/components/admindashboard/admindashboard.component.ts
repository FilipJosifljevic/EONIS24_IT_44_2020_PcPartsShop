import {Component, Inject, PLATFORM_ID} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Order} from "@stripe/stripe-js";
import {OrderService} from "../../services/OrderService";
import {User} from "../../models/User";
import {UserService} from "../../services/UserService";
import {isPlatformBrowser} from "@angular/common";
import {jwtDecode} from "jwt-decode";

@Component({
  selector: 'app-admindashboard',
  templateUrl: './admindashboard.component.html',
  styleUrl: './admindashboard.component.css'
})
export class AdmindashboardComponent {

  public users : User[] = [];
  public orders : Order[] = [];
  public currentUser: User | null = null;
  private jwtoken: string | null = null;
  constructor(private orderService: OrderService, private userService: UserService,  @Inject(PLATFORM_ID) private platformId: Object, private readonly http: HttpClient,) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.jwtoken = localStorage.getItem('token');
      if (this.jwtoken) {
        try {
          const decodedToken: any = jwtDecode(this.jwtoken);
          const email = decodedToken.sub;

          this.http.get<User>(`http://localhost:8081/users/email/${email}`, {
          }).subscribe({
            next: (user) => {
              this.currentUser = user;
            },
            error: (error) => {
              console.error('Failed to fetch user by email', error);
              this.currentUser = null;
            }
          });
        } catch (error) {
          console.error('Failed to decode token', error);
          this.currentUser = null;
        }
      } else {
        this.currentUser = null;
      }

      this.userService.getUsers().subscribe(users => {
        this.users = users;
      });

      const headers = new HttpHeaders().set('Authorization', `Bearer ${this.jwtoken}`);
      this.orderService.getOrders(headers).subscribe(orders => {
        this.orders = orders;
      })
    }

  }

  deleteUser(userId: string): any {
    this.userService.deleteUser(userId).subscribe({
      next: (response) => {
        console.log('User deleted:', response);
        this.userService.getUsers().subscribe(users => {
          this.users = users;
        });
      },
      error: (error) => {
        console.error('Failed to delete user', error);
      }
    });
  }
}
