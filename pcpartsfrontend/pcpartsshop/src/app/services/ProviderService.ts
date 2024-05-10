import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Provider } from '../models/Provider';

@Injectable({
  providedIn: 'root'
})
export class ProviderService {
  private apiUrl = 'http://localhost:8081/providers';

  constructor(private http: HttpClient) { }

  getProviders(): Observable<Provider[]> {
    return this.http.get<Provider[]>(this.apiUrl);
  }

  getProvider(id: string): Observable<Provider> {
    return this.http.get<Provider>(`${this.apiUrl}/${id}`);
  }
  addProvider(provider: Provider): Observable<Provider> {
    return this.http.post<Provider>(this.apiUrl, provider);
  }

  updateProvider(provider: Provider): Observable<Provider> {
    const url = `${this.apiUrl}/${provider.userId}`;
    return this.http.put<Provider>(url, provider);
  }

  deleteProvider(providerId: string): Observable<void> {
    const url = `${this.apiUrl}/${providerId}`;
    return this.http.delete<void>(url);
  }
}
