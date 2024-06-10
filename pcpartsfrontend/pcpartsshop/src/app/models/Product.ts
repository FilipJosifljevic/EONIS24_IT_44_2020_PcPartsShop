import { Provider } from './Provider';

export class Product {
  constructor(
    public productId: string,
    public productName: string,
    public productCategory: string,
    public productPrice: number,
    public quantityInStock: number,
    public imageUrl: string,
    public providerId: string, // Use Provider object instead of providerId
  ) {}
}
