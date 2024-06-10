import {map, switchMap, tap} from 'rxjs/operators';
import {Product} from "../models/Product";
import {forkJoin, Observable, Subject} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ProviderService} from "./ProviderService";
import {Injectable, Provider} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private productAddedSubject = new Subject<void>();
  private apiUrl = 'http://localhost:8081/products';

  constructor(private http: HttpClient, private providerService: ProviderService) { }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl);
  }

  getProductById(id: string): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/${id}`);
  }

  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, product).pipe(
      tap(() => this.productAddedSubject.next())
    );
  }

  getProductAddedEvent(): Observable<void> {
    return this.productAddedSubject.asObservable();
  }

  updateProduct(product: Product): Observable<Product> {
    const url = `${this.apiUrl}/${product.productId}`;
    return this.http.put<Product>(url, product).pipe(
      tap(() => this.productAddedSubject.next())
    );
  }

  deleteProduct(productId: string): Observable<any> {
    const url = `${this.apiUrl}/${productId}`;
    return this.http.delete(url, { responseType: 'text' });
  }

  searchProductsByName(productName: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/byName?productName=${productName}`);
  }

  searchProductsByCategory(category: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/byCategory?productCategory=${category}`);
  }

  searchProductsByProvider(providerName: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/byProvider?providerName=${providerName}`);
  }

  searchProductsByPriceRange(minPrice: number, maxPrice: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/byPrice?minPrice=${minPrice}&maxPrice=${maxPrice}`);
  }
}
