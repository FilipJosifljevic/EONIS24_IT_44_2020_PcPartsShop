import {Component, OnInit} from '@angular/core';
import {MenuItem, PrimeIcons} from "primeng/api";
import {Product} from "../../models/Product";
import {ProductService} from "../../services/ProductService";

@Component({
  selector: 'app-productspage',
  templateUrl: './productspage.component.html',
  styleUrl: './productspage.component.css'
})
export class ProductspageComponent implements OnInit {

  items: MenuItem[] | undefined;
  products: Product[] = [];

  constructor(private productService: ProductService) {
  }

  ngOnInit() {
    this.items = [
      {
        label: 'Home',
        icon: 'https://cdn-icons-png.flaticon.com/512/263/263115.png'
      },
      {
        label: 'Profile',
        icon: 'https://cdn-icons-png.flaticon.com/512/1077/1077063.png'
      },
      {
        label: 'Cart',
        icon: 'https://cdn-icons-png.flaticon.com/512/5337/5337564.png'
      },
      {
        label: 'About',
        icon: 'https://cdn-icons-png.flaticon.com/512/3503/3503827.png'
      }
    ];

    this.productService.getProducts().subscribe(products => {
      this.products = products;
    })
  }
}
