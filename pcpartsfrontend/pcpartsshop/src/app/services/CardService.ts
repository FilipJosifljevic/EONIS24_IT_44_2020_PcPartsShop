import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from "rxjs";
import {CartService} from "./CartService";

@Injectable({
  providedIn: 'root'
})
export class CardService {


  constructor(private httpClient: HttpClient, private cartService: CartService) {}

  public createCharge(cardToken: any, price: number, description: string):Observable<any> {
    const chargeRequest = {
      token: cardToken.id,
      price,
      description
    };

    return this.httpClient.post('http://localhost:8081/charges', chargeRequest)
  }

}
