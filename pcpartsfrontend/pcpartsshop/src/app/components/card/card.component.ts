import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { StripeService } from '../../services/StripeService';
import { CardService } from '../../services/CardService';
import { CartService } from '../../services/CartService';
import { OrderService } from '../../services/OrderService';
import { ProductOrder } from '../../models/ProductOrder';
import { User } from '../../models/User';
import { UserService } from '../../services/UserService';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { OrderCreateRequest } from '../../models/Order';
import { isPlatformBrowser } from "@angular/common";
import { jwtDecode } from "jwt-decode";
import { ActivatedRoute } from '@angular/router';
import {Product} from "../../models/Product";
import {ProductService} from "../../services/ProductService";

declare var Stripe: any;

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {
  private stripe: any = null;
  private card: any = null;
  private elements: any = null;
  public cardError: string = "";
  public chargeError: any = null;
  public charge: any = null;
  public cartItems: ProductOrder[] = [];
  public paymentIntentClientSecret: string = "";
  public currentUser: User | null = null;
  private jwtoken: string | null = null;
  private orderId: string = "";
  public productsMap: { [key: string]: Product } = {};

  constructor(
    private readonly stripeService: StripeService,
    private readonly cardService: CardService,
    private readonly cartService: CartService,
    private readonly userService: UserService,
    private readonly orderService: OrderService,
    private readonly productService: ProductService,
    private readonly http: HttpClient,
    private readonly route: ActivatedRoute,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.jwtoken = localStorage.getItem('token');
      if (this.jwtoken) {
        try {
          const decodedToken: any = jwtDecode(this.jwtoken);
          const email = decodedToken.sub;

          this.http.get<User>(`http://localhost:8081/users/email/${email}`
          ).subscribe({
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
    }

    this.stripeService.initializeStripe().subscribe(() => {
      this.stripe = Stripe('pk_test_51PKmDhBY4FcDsN1R2mQT6i8J4kuuEE8QQaZwIBP1LEEvetc4EPaqoGMZ8kAnR4ZBmXpX8M1abqALfsmpw1s8x94X00jWrGwATI');
      this.elements = this.stripe.elements();
      this.card = this.elements.create('card');
      this.card.mount('#card-element');
      this.card.addEventListener('change', (event: any) => event.error ? this.cardError = event.error.message : null);
    });

    this.loadCartItems();

    this.route.queryParams.subscribe(params => {
      this.orderId = params['orderId'];
    });
  }

  private loadCartItems() {
    this.cartItems = this.cartService.getItems();
    this.cartItems.forEach(item => {
      this.productService.getProductById(item.product.productId).subscribe(product => {
        this.productsMap[item.product.productId] = product;
      });
    });
  }

  public createCharge(token: any) {
    this.charge = null;
    this.chargeError = null;

    this.cardService.createCharge(token, this.cartService.getSubtotal(), this.orderId)
      .subscribe(
        response => this.charge = response,
        error => this.chargeError = error
      );
  }

  public getToken() {
    this.stripe.createToken(this.card).then((result: any) => {
      if (result.error) {
        this.cardError = result.error.message;
      } else {
        this.createCharge(result.token);
      }
    });
  }
}
