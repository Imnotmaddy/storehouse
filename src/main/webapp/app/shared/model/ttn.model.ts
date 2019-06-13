import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/product.model';

export interface ITTN {
  id?: number;
  serialNumber?: string;
  dateOfCreation?: Moment;
  description?: string;
  driverName?: string;
  productsAmount?: number;
  numberOfProductEntries?: number;
  dateTimeOfRegistration?: Moment;
  isAccepted?: boolean;
  dispatcherLastName?: string;
  dispatcherId?: number;
  managerLastName?: string;
  managerId?: number;
  senderLastName?: string;
  sender?: string;
  recipient?: string;
  transportId?: number;
  transportVehicleNumber?: number;
  transporterCompanyName?: string;
  transporterId?: number;
  products?: IProduct[];
}

export const defaultValue: Readonly<ITTN> = {
  isAccepted: false
};
