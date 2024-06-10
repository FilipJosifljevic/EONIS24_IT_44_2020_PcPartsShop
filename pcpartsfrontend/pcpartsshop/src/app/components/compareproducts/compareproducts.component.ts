import {Component, OnInit, ViewChild} from '@angular/core';
import { ProductService } from '../../services/ProductService';
import { Product } from '../../models/Product';
import {AutoComplete, AutoCompleteSelectEvent} from "primeng/autocomplete";

@Component({
  selector: 'app-compareproducts',
  templateUrl: './compareproducts.component.html',
  styleUrls: ['./compareproducts.component.css']
})
export class CompareproductsComponent implements OnInit {
  categories: any[] | undefined;
  selectedCategory: { name: string, value: string } | undefined;
  suggestions1: Product[] = [];
  suggestions2: Product[] = [];
  selectedProduct1: Product | undefined;
  selectedProduct2: Product | undefined;
  selectedItem1: Product | undefined;
  selectedItem2: Product | undefined;
  @ViewChild('autoComplete1') autoComplete1!: AutoComplete;
  @ViewChild('autoComplete2') autoComplete2!: AutoComplete;

  constructor(private productService: ProductService) { }

  ngOnInit() {
    this.categories = [
      { name: 'GPU', value: 'GPU' },
      { name: 'CPU', value: 'CPU' },
      { name: 'SSD', value: 'SSD' },
      { name: 'PSU', value: 'PSU' }
    ];
  }

  searchProducts1(event: any) {
    const searchTerm = event.query;
    if (this.selectedCategory && searchTerm) {
      this.productService.searchProductsByCategory(this.selectedCategory.value)
        .subscribe(products => {
          this.suggestions1 = products.filter(product =>
            product.productName.toLowerCase().includes(searchTerm.toLowerCase()));
          console.log('Suggestions1:', this.suggestions1); // Debugging log
        });
    }
  }

  searchProducts2(event: any) {
    const searchTerm = event.query;
    if (this.selectedCategory && searchTerm) {
      this.productService.searchProductsByCategory(this.selectedCategory.value)
        .subscribe(products => {
          this.suggestions2 = products.filter(product =>
            product.productName.toLowerCase().includes(searchTerm.toLowerCase()));
          console.log('Suggestions2:', this.suggestions2); // Debugging log
        });
    }
  }

  selectProduct1(event: AutoCompleteSelectEvent) {
    if (event.value) {
      this.selectedProduct1 = event.value as Product;
      console.log('Selected Product 1:', this.selectedProduct1); // Debugging log
    }
  }

  selectProduct2(event: AutoCompleteSelectEvent) {
    if (event.value) {
      this.selectedProduct2 = event.value as Product;
      console.log('Selected Product 2:', this.selectedProduct2); // Debugging log
    }
  }

  resetProducts() {
    this.selectedProduct1 = undefined;
    this.selectedProduct2 = undefined;
    this.autoComplete1.clear();
    this.autoComplete2.clear();
  }
}
