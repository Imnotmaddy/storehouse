export const enum ProductState {
  REGISTRATED = 'REGISTRATED',
  APPROVED = 'APPROVED',
  STORED = 'STORED',
  LOST_BY_TRANSPORTER = 'LOST_BY_TRANSPORTER',
  GONE_FROM_STORAGE = 'GONE_FROM_STORAGE',
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
  state?: ProductState;
  daysInStorage?: number;
  cost?: number;
  requiredFacility?: Facility;
  weight?: number;
  name?: string;
  tTNId?: number;
  quantity?: number;
}

export const defaultValue: Readonly<IProduct> = {};
