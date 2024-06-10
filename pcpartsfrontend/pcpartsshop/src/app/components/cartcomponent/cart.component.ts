import { Component, OnInit } from '@angular/core';
import { CartService } from '../../services/CartService';
import { Observable } from 'rxjs';
import { ProductOrder } from '../../models/ProductOrder';
import { Router } from '@angular/router';
import { OrderService } from '../../services/OrderService';
import { AuthService } from '../../services/AuthService';
import { User } from '../../models/User';
import { OrderCreateRequest } from '../../models/Order';
import {Product} from "../../models/Product";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cart$: Observable<ProductOrder[]>;
  cart: ProductOrder[] = [];
  currentUser: User | null = null;

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private authService: AuthService,
    private router: Router
  ) {
    this.cart$ = this.cartService.cart$;
  }

  ngOnInit(): void {
    this.cart$.subscribe(cart => {
      this.cart = cart || [];
    });

    this.currentUser = this.authService.getCurrentUser();
  }

  removeFromCart(product: Product) {
    this.cartService.removeFromCart(product);
    this.cartService.getSubtotal();
  }

  increaseQuantity(item: ProductOrder) {
    item.quantity++;
    this.cartService.updateCart(item.product, item.quantity);
    this.cartService.getSubtotal();
  }

  decreaseQuantity(item: ProductOrder) {
    if (item.quantity > 1) {
      item.quantity--;
      this.cartService.updateCart(item.product, item.quantity);
      this.cartService.getSubtotal();
    }
  }

  createOrderAndCheckout() {
    if (this.currentUser) {
      const orderCreateRequest: OrderCreateRequest = {
        orderDate: new Date(),
        orderPrice: this.cartService.getSubtotal(),
        orderStatus: 'Created',
        discount: 0,
        promoCode: '',
        customerId: this.currentUser.userId
      };

      const token = this.authService.getToken();
      if (token) {
        this.orderService.createOrder(orderCreateRequest).subscribe({
          next: (response: any) => {
            console.log('Order created successfully', response);
            const orderId = response.orderId;
              this.router.navigate(['/checkout'], { queryParams: { orderId: orderId } });
          },
          error: (error: any) => {
            console.error('Failed to create order', error);
          }
        });
      } else {
        console.error('No token found, cannot create order');
      }
    } else {
      console.error('No current user, cannot create order');
    }
  }

  checkout() {
    console.log("Checkout button clicked!")
    this.createOrderAndCheckout();
  }

  getSubtotal(): number {
    return this.cartService.getSubtotal();
  }
}
