import {map, switchMap} from 'rxjs/operators';
import {Product} from "../models/Product";
import {forkJoin, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {ProviderService} from "./ProviderService";
import {Injectable, Provider} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8081/products';

  constructor(private http: HttpClient, private providerService: ProviderService) { }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl);
  }


  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, product);
  }

  updateProduct(product: Product): Observable<Product> {
    const url = `${this.apiUrl}/${product.productId}`;
    return this.http.put<Product>(url, product);
  }

  deleteProduct(productId: string): Observable<void> {
    const url = `${this.apiUrl}/${productId}`;
    return this.http.delete<void>(url);
  }
}
