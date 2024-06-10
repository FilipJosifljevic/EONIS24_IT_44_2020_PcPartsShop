import {Injectable, Provider} from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { User } from "../models/User";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = "http://localhost:8081/users";
  private customerUrl = "http://localhost:8081/customers";
  private providerUrl = "http://localhost:8081/providers";

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  getUserByEmail(email: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/email/${email}`);
  }

  deleteUser(userId: string) : Observable<any> {
    return this.http.delete(`${this.apiUrl}/${userId}`, { responseType: 'text' });
  }

  updateCustomer(userId:string, user: User): Observable<any> {
    return this.http.put(`${this.customerUrl}/${userId}`, user);
  }

  updateProvider(userId: string, user: Provider): Observable<any> {
    return this.http.put(`${this.providerUrl}/${userId}`, user);
  }
}
