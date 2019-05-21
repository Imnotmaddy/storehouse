export interface IAddress {
  id?: number;
  city?: string;
  street?: string;
  houseNumber?: string;
  apartmentNumber?: number;
  userName?: string;
  userId?: number;
}

export const defaultValue: Readonly<IAddress> = {};
