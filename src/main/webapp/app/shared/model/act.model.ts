import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/product.model';

export const enum ActType {
  THEFT = 'THEFT',
  INCONSISTENCY = 'INCONSISTENCY',
  LOSS = 'LOSS',
  WRITE_OFF = 'WRITE_OFF'
}

export interface IAct {
  id?: number;
  date?: Moment;
  cost?: number;
  type?: ActType;
  userId?: number;
  products?: IProduct[];
}

export const defaultValue: Readonly<IAct> = {};
