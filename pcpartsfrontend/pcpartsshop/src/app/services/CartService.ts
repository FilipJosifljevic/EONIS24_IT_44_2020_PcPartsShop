import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { ProductOrder } from '../models/ProductOrder';
import { Order } from '../models/Order';
import { Product } from '../models/Product';
import { ProductOrderCreation } from '../models/ProductOrderCreation';
import {map, switchMap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = 'http://localhost:8081';
  private cartSubject = new BehaviorSubject<ProductOrder[]>([]);
  cart$ = this.cartSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadCart();
  }

  private loadCart() {
    if (typeof window !== 'undefined') {
      const storedCart = localStorage.getItem('cart');
      if (storedCart) {
        this.cartSubject.next(JSON.parse(storedCart));
      }
    }
  }

  private saveCart() {
    if (typeof window !== 'undefined') {
      localStorage.setItem('cart', JSON.stringify(this.cartSubject.value));
    }
  }


  addToCart(product: Product, quantity: number) {
    const currentCart = this.cartSubject.value;
    const existingProductOrder = currentCart.find(item => item.product.productId === product.productId);

    if (existingProductOrder) {
      existingProductOrder.quantity += quantity;
    } else {
      const productOrder = new ProductOrder('', quantity, product.productPrice, '', product);
      currentCart.push(productOrder);
    }

    this.cartSubject.next(currentCart);
    this.saveCart();
  }

  removeFromCart(product: Product) {
    const currentCart = this.cartSubject.value;
    const updatedCart = currentCart.filter(item => item.product.productId !== product.productId);
    this.cartSubject.next(updatedCart);
    this.saveCart();
  }

  updateCart(product: Product, quantity: number) {
    const currentCart = this.cartSubject.value;
    const productOrder = currentCart.find(item => item.product.productId === product.productId);

    if (productOrder) {
      productOrder.quantity = quantity;
    }

    this.cartSubject.next(currentCart);
    this.saveCart();
  }

  getSubtotal(): number {
    const currentCart = this.cartSubject.value;
    return currentCart.reduce((total, item) => total + (item.product.productPrice * item.quantity), 0);
  }

  getItems(): ProductOrder[] {
    return this.cartSubject.value;
  }
}
