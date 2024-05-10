import { Provider } from './Provider';

export class Product {
  productId: string;
  productName: string;
  productCategory: string;
  productPrice: number;
  quantityInStock: number;
  imageUrl: string;
  providerId: string;

  constructor(
    productId: string,
    productName: string,
    productCategory: string,
    productPrice: number,
    quantityInStock: number,
    imageUrl: string,
    providerId: string
  ) {
    this.productId = productId;
    this.productName = productName;
    this.productCategory = productCategory;
    this.productPrice = productPrice;
    this.quantityInStock = quantityInStock;
    this.imageUrl = imageUrl;
    this.providerId = providerId;
  }
}
