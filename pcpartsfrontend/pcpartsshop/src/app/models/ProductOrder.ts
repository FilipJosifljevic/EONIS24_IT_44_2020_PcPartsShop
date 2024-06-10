import { Product } from './Product';

export class ProductOrder {
  constructor(
    public productOrderId: string,
    public quantity: number,
    public price: number,
    public orderId: string,
    public product: Product
  ) {}
}
