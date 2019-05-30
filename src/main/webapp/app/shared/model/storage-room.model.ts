import { IProduct } from 'app/shared/model/product.model';

export const enum Facility {
  REFRIGERATOR = 'REFRIGERATOR',
  OPEN_SPACE = 'OPEN_SPACE',
  HEATED_SPACE = 'HEATED_SPACE',
  ORDINARY_ROOM = 'ORDINARY_ROOM'
}

export interface IStorageRoom {
  id?: number;
  roomNumber?: string;
  type?: Facility;
  products?: IProduct[];
  storehouseId?: number;
}

export const defaultValue: Readonly<IStorageRoom> = {};
