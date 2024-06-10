import {Product} from "./Product";

export class ProductOrderCreation {
  constructor(
    public productOrderId: string,
    public quantity: number,
    public price: number,
    public orderId: string,
    public productId: string
  ) {}
}
