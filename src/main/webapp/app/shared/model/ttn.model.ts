import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/product.model';

export interface ITTN {
  id?: number;
  serialNumber?: string;
  dateOfCreation?: Moment;
  description?: string;
  productsAmount?: number;
  numberOfProductEntries?: number;
  dateTimeOfRegistration?: Moment;
  isAccepted?: boolean;
  storehouseDispatcherName?: string;
  storehouseDispatcherId?: number;
  managerName?: string;
  managerId?: number;
  senderName?: string;
  senderId?: number;
  transportFacility?: string;
  transportId?: number;
  transporterCompanyName?: string;
  transporterId?: number;
  driverName?: string;
  driverId?: number;
  recipientCompanyName?: string;
  recipientId?: number;
  products?: IProduct[];
}

export const defaultValue: Readonly<ITTN> = {
  isAccepted: false
};
