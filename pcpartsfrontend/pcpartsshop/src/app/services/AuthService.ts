import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { BehaviorSubject } from 'rxjs';
import { decodeToken } from './jwtHelper';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8081';
  private tokenKey = 'authToken';
  private userKey = 'userData';
  private currentUserSubject: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {
    if (this.isBrowser()) {
      const userData = localStorage.getItem(this.userKey);
      const user = userData ? JSON.parse(userData) as User : null;
      this.currentUserSubject.next(user);
    }
  }

  login(email: string, password: string) {
    const url = `${this.apiUrl}/login`;
    return this.http.post<any>(url, { email, password }).pipe(
      tap(response => {
        if (!response || !response.token) {
          throw new Error('Invalid response from server');
        }
        const token = response.token;
        const decodedToken = decodeToken(token);
        const user = {
          userId: decodedToken.id,
          email: decodedToken.email,
          role: decodedToken.role
        } as User;
        if (this.isBrowser()) {
          localStorage.setItem(this.tokenKey, token);
          localStorage.setItem(this.userKey, JSON.stringify(user));
        }
        this.currentUserSubject.next(user);
      })
    );
  }

  logout(): void {
    if (this.isBrowser()) {
      localStorage.removeItem(this.tokenKey);
      localStorage.removeItem(this.userKey);
      this.currentUserSubject.next(null);
      this.router.navigate(['/login']);
    }
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getToken(): string | null {
    if (this.isBrowser()) {
      return localStorage.getItem(this.tokenKey);
    }
    return null;
  }

  private isBrowser(): boolean {
    return typeof window !== 'undefined';
  }
}
