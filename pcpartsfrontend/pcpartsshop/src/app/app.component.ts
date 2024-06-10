import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { User } from './models/User';
import { UserService } from './services/UserService';
import { ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from './models/Product';
import { ProductService } from './services/ProductService';
import { HttpClient } from '@angular/common/http';
import { isPlatformBrowser } from '@angular/common';
import { AddProductDialogComponent } from './components/addproductdialog/addproductdialog.component';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from './services/AuthService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'pcpartsshop';
  items: MenuItem[] | undefined;
  menuItems: MenuItem[] | undefined;
  menuItemsLoggedIn: MenuItem[] | undefined;
  menuItemsLoggedOut: MenuItem[] | undefined;
  currentUser: User | null = null;
  filteredProducts: Product[] = [];
  searchResults: Product[] = [];
  showDropdown: boolean = false;
  searchText: string = '';
  private uiRefreshed: boolean = false;

  constructor(
    private userService: UserService,
    private ref: ChangeDetectorRef,
    private router: Router,
    private productService: ProductService,
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object,
    private dialog: MatDialog,
    private authService: AuthService // Inject AuthService
  ) { }

  ngOnInit() {
    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-home',
        route: '/products'
      },
      {
        label: 'Compare',
        icon: 'pi pi-chart-bar',
        route: '/compare'
      },
      {
        label: 'Contact',
        icon: 'pi pi-envelope'
      }
    ];

    this.menuItemsLoggedIn = [
      {
        label: 'Settings',
        icon: 'pi pi-cog',
        route: '/profile'
      },
      {
        label: 'Logout',
        icon: 'pi pi-sign-out',
        command: () => this.logOut()
      }
    ];

    this.menuItemsLoggedOut = [
      {
        label: 'Log in',
        icon: 'pi pi-sign-in',
        route: '/login'
      }
    ];

    this.loadUser();
  }

  loadUser() {
    if (isPlatformBrowser(this.platformId)) {
      const user = this.authService.getCurrentUser();
      if (user) {
        this.currentUser = user;
        this.updateMenuItems();
      } else {
        this.currentUser = null;
        this.updateMenuItems();
      }
    }
  }

  updateMenuItems() {
    if (this.currentUser) {
      this.menuItems = this.menuItemsLoggedIn;
    } else {
      this.menuItems = this.menuItemsLoggedOut;
    }
  }

  logOut() {
    this.authService.logout();
    this.currentUser = null;
    this.updateMenuItems();
    this.router.navigate(['/products']);
    window.location.reload();
  }

  reloadPage() {
    window.location.reload();
  }

  navigateToDashboard() {
    this.router.navigate(['/dashboard']);
  }

  searchProducts(event: any) {
    const query = event.query;
    if (query) {
      this.productService.searchProductsByName(query).subscribe(products => {
        this.filteredProducts = products;
      },
        (error) => {
          console.log(error);
        });
    } else {
      this.filteredProducts = [];
    }
  }

  viewProductDetails(event: any) {
    if(event && event.value){
      this.router.navigate(['products', event.value.productId]);
    }

  }

  private refreshUIOnce() {
    if (!this.uiRefreshed) {
      this.ref.detectChanges();
      this.uiRefreshed = true;
    }
  }
}
