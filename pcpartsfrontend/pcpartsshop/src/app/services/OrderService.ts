import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from '../models/Order';
import { OrderCreateRequest } from '../models/Order';
import {ProductOrder} from "../models/ProductOrder";

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private baseUrl = 'http://localhost:8081/orders';

  constructor(private http: HttpClient) { }

  getOrders(headers:HttpHeaders): Observable<any> {
    return this.http.get(this.baseUrl, {headers:headers});
  }


  createOrder(orderCreateRequest: OrderCreateRequest): Observable<any> {
    return this.http.post(this.baseUrl, orderCreateRequest);
  }

  updateProductOrders(productOrders: ProductOrder[]): Observable<any> {
    const url = `${this.baseUrl}/updateProductOrders`;
    return this.http.put(url, productOrders);
  }
}
