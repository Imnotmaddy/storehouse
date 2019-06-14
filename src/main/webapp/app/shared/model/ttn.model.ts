import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/product.model';

export const enum Status {
  REGISTERED = 'REGISTERED',
  CHECKED = 'CHECKED',
  DECORATED = 'DECORATED',
  RELEASE_ALLOWED = 'RELEASE_ALLOWED',
  REMOVED_FROM_STORAGE = 'REMOVED_FROM_STORAGE'
}

export interface ITTN {
  id?: number;
  serialNumber?: string;
  dateOfCreation?: Moment;
  description?: string;
  driverName?: string;
  productsAmount?: number;
  numberOfProductEntries?: number;
  dateTimeOfRegistration?: Moment;
  status?: Status;
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
