export const enum Facility {
  REFRIGERATOR = 'REFRIGERATOR',
  OPEN_SPACE = 'OPEN_SPACE',
  HEATED_SPACE = 'HEATED_SPACE',
  ORDINARY_ROOM = 'ORDINARY_ROOM'
}

export interface IStorageRoom {
  id?: number;
  amountOfDistinctProducts?: number;
  type?: Facility;
  storehouseId?: number;
}

export const defaultValue: Readonly<IStorageRoom> = {};
