import {ProductOrder} from "./ProductOrder";
import {Customer} from "./Customer";

export class Order {
  constructor(
    public orderId: string,
    public orderDate: Date,
    public orderPrice: number,
    public orderStatus: string,
    public discount: number,
    public promoCode: string,
    public customer: Customer,
    public productOrders: ProductOrder[]
  ) {}
}

export interface OrderCreateRequest {
  orderDate: Date;
  orderPrice: number;
  orderStatus: string;
  discount: number;
  promoCode?: string;
  customerId: string;
}
