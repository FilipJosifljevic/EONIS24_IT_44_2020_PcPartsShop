import { Component, PLATFORM_ID, Inject } from '@angular/core';
import { Product } from '../../models/Product';
import { AuthService } from '../../services/AuthService';
import { ProductService } from "../../services/ProductService";

@Component({
  selector: 'app-add-product-dialog',
  templateUrl: './addproductdialog.component.html',
  styleUrls: ['./addproductdialog.component.css']
})
export class AddProductDialogComponent {
  display: boolean = false;
  isEditing: boolean = false;
  product: Product = new Product('', '', '', 0, 0, '', '');

  constructor(
    private productService: ProductService,
    private authService: AuthService
  ) { }
  showDialog(product?: Product) {
    if (product) {
      this.product = { ...product };
      this.isEditing = true;
    } else {
      this.product = new Product('', '', '', 0, 0, '', '');
      this.isEditing = false;
    }
    this.display = true;
  }

  saveProduct() {
    const token = this.authService.getToken();
    const user = this.authService.getCurrentUser();

    if (!token || !user) {
      console.error('Token or user data not found');
      return;
    }

    // Set the providerId only if it's a new product (not editing)
    if (!this.isEditing) {
      this.product.providerId = user.userId;
    }

    if (this.isEditing) {
      this.productService.updateProduct(this.product).subscribe(
        response => {
          console.log('Product updated successfully', response);
          this.display = false;
        },
        error => {
          console.error('Error updating product', error);
        }
      );
    } else {
      // Adding new product
      this.productService.addProduct(this.product).subscribe(
        response => {
          console.log('Product added successfully', response);
          this.display = false;
        },
        error => {
          console.error('Error adding product', error);
        }
      );
    }
  }
}
