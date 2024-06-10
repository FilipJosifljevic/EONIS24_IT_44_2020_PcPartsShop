import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../../models/Product';
import { ProductService } from '../../services/ProductService';
import { CartService } from '../../services/CartService';

@Component({
  selector: 'app-productdetails',
  templateUrl: './productdetails.component.html',
  styleUrls: ['./productdetails.component.css']
})
export class ProductdetailsComponent implements OnInit {

  product: Product | undefined;
  quantity: number = 1; // Default quantity

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private cartService: CartService // Inject CartService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const productId = params.get('id');
      if (productId) {
        this.productService.getProductById(productId).subscribe(product => {
          this.product = product;
        });
      }
    });
  }

  addToCart() {
    if (this.product) {
      this.cartService.addToCart(this.product, this.quantity);
    }
  }
}
