export interface User {
  userId: string;
  email: string;
  password: string;
  role: 'ADMIN' | 'CUSTOMER' | 'PROVIDER';
  firstName?: string;
  lastName?: string;
  address?: string;
  zipCode?: string;
  providerName?: string;
  cityName?: string;
  contactNumber?: number;
}
