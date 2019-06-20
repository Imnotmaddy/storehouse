export const enum ProductState {
  REGISTRATED = 'REGISTRATED',
  APPROVED = 'APPROVED',
  STORED = 'STORED',
  LOST_BY_TRANSPORTER = 'LOST_BY_TRANSPORTER',
  LOST_FROM_STORAGE = 'LOST_FROM_STORAGE',
  STOLEN_FROM_STORAGE = 'STOLEN_FROM_STORAGE',
  TRANSPORTER_SHORTAGE = 'TRANSPORTER_SHORTAGE',
  CONFISCATED = 'CONFISCATED',
  RECYCLED = 'RECYCLED',
  UNSTORED = 'UNSTORED',
  READY_TO_LEAVE = 'READY_TO_LEAVE',
  REMOVED_FROM_STORAGE = 'REMOVED_FROM_STORAGE'
}

export const enum Facility {
  REFRIGERATOR = 'REFRIGERATOR',
  OPEN_SPACE = 'OPEN_SPACE',
  HEATED_SPACE = 'HEATED_SPACE',
  ORDINARY_ROOM = 'ORDINARY_ROOM'
}

export interface IProduct {
  id?: number;
  name?: string;
  quantity?: number;
  state?: ProductState;
  daysInStorage?: number;
  cost?: number;
  requiredFacility?: Facility;
  weight?: number;
  actId?: number;
  storageRoomId?: number;
  storageRoomRoomNumber: number;
  tTNId?: number;
  tTNSerialNumber: string;
}

export const defaultValue: Readonly<IProduct> = {};
