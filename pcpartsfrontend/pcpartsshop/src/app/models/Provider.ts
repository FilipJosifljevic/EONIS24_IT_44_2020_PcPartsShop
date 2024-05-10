export class Provider {
  userId: string;
  providerName: string;
  cityName: string;
  contactNumber: number;

  constructor(
    userId: string,
    providerName: string,
    cityName: string,
    contactNumber: number
  ) {
    this.userId = userId;
    this.providerName = providerName;
    this.cityName = cityName;
    this.contactNumber = contactNumber;
  }
}
