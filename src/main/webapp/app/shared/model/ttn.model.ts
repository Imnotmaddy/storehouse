import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/product.model';

export const enum Status {
  REGISTERED = 'REGISTERED',
  CHECKED = 'CHECKED',
  EDITING_BY_DISPATCHER = 'EDITING_BY_DISPATCHER',
  EDITING_BY_MANAGER = 'EDITING_BY_MANAGER',
  ACCEPTED_TO_STORAGE = 'ACCEPTED_TO_STORAGE',
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

export const defaultValue: Readonly<ITTN> = {};
