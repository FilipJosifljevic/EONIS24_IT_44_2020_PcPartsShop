import {User} from "./User";

export interface Provider extends User{
  userId: string;
  providerName?: string;
  cityName?: string;
  contactNumber?: number;
}
