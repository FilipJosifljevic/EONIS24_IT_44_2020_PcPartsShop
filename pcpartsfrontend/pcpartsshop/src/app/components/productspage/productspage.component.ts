import {Component, Inject, OnInit, PLATFORM_ID, ViewChild} from '@angular/core';
import {MenuItem, PrimeIcons} from "primeng/api";
import { Router } from "@angular/router";
import {Product} from "../../models/Product";
import {ProductService} from "../../services/ProductService";
import {User} from "../../models/User";
import {UserService} from "../../services/UserService";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {isPlatformBrowser} from "@angular/common";
import {jwtDecode} from "jwt-decode";
import {AuthService} from "../../services/AuthService";
import {AddProductDialogComponent} from "../addproductdialog/addproductdialog.component";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-productspage',
  templateUrl: './productspage.component.html',
  styleUrl: './productspage.component.css'
})
export class ProductspageComponent implements OnInit {

  @ViewChild('addProductDialog')
  private addProductDialog!: AddProductDialogComponent;
  products: Product[] = [];
  rangeValues: number[] = [1000, 100000];
  menu_items: MenuItem[] | undefined;
  shouldReloadUi: boolean = false;
  currentUser: User | null = null;
  selectedProduct: Product | null = null;
  length = 50;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [5, 10, 25];

  hidePageSize = false;
  showPageSizeOptions = true;
  showFirstLastButtons = true;
  disabled = false;

  pageEvent: PageEvent | undefined = undefined;


  constructor(private productService: ProductService,
              private router: Router,
              private userService: UserService,
              private authService: AuthService,
              @Inject(PLATFORM_ID) private platformId: Object,
              private http: HttpClient) {
  }

  ngOnInit() {
    if(this.shouldReloadUi){
      this.shouldReloadUi = false;
      window.location.reload();
    }
    this.menu_items = [
      {
        label: 'Sort by price ascending',
        icon: 'pi pi-sort-numeric-up-alt',
        command: () => this.sortProducts('productPrice', 'asc')
      },
      {
        label: 'Sort by price descending',
        icon: 'pi pi-sort-numeric-down-alt',
        command: () => this.sortProducts('productPrice', 'desc')
      },
      {
        label: 'Sort by name',
        icon: 'pi pi-sort-alpha-down',
        command: () => this.sortProducts('productName', 'asc')
      },
    ];

    this.productService.getProducts().subscribe(products => {
      this.products = products;
    })

    this.productService.getProductAddedEvent().subscribe(() => {
      this.productService.getProducts().subscribe(products => {
        this.products = products;
      });
    });
    this.loadUser();
  }
  loadUser() {
    this.currentUser = this.authService.getCurrentUser();
  }

  viewProductDetails(productId: string): void {
    this.router.navigate(['products', productId]);
  }

  searchProducts(){}

  searchProductsByName(productName: string) {
    this.productService.searchProductsByName(productName).subscribe(products => {
      this.products = products;
    });
  }

  searchProductsByCategory(category: string) {
    this.productService.searchProductsByCategory(category).subscribe(products => {
      this.products = products;
    });
  }

  searchProductsByProvider(providerName: string) {
    this.productService.searchProductsByProvider(providerName).subscribe(products => {
      this.products = products;
    });
  }

  searchProductsByPriceRange(minPrice: number, maxPrice: number) {
    this.productService.searchProductsByPriceRange(minPrice, maxPrice).subscribe(products => {
      this.products = products;
    });
  }

  sortProducts(property: 'productPrice' | 'productName', order: 'asc' | 'desc') {
    this.products.sort((a, b) => {
      let comparison = 0;
      if (property === 'productPrice') {
        comparison = a.productPrice - b.productPrice;
      } else if (property === 'productName') {
        comparison = a.productName.localeCompare(b.productName);
      }
      return order === 'asc' ? comparison : -comparison;
    });
  }


  deleteProduct(productId: string) {
    this.productService.deleteProduct(productId).subscribe(result => {
      this.productService.getProducts().subscribe(products => {
        this.products = products;
      })
    })
  }

  editProduct(product: Product) {
    this.selectedProduct = product;
    this.addProductDialog.showDialog(product);
    this.productService.getProducts().subscribe(products => {
      this.products = products;
    })
  }

  isEditableByCurrentUser(product: Product): boolean {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser) {
      console.log('No current user');
      return false;
    }

    return currentUser.role === 'ADMIN' || product.providerId === currentUser.userId;
  }


  handlePageEvent(e: PageEvent) {
    this.pageIndex = e.pageIndex;
    this.pageSize = e.pageSize;
  }

  setPageSizeOptions(setPageSizeOptionsInput: string) {
    if (setPageSizeOptionsInput) {
      this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
    }
  }
}
