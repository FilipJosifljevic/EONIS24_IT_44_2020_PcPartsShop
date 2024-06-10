import { User } from '../models/User';

export interface Customer extends User {
  firstName: string;
  lastName: string;
  address: string;
  zipCode: string;
}
