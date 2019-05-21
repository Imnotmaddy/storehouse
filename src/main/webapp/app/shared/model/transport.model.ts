export const enum DeliveryType {
  Auto = 'Auto',
  Train = 'Train'
}

export const enum Facility {
  REFRIGERATOR = 'REFRIGERATOR',
  OPEN_SPACE = 'OPEN_SPACE',
  HEATED_SPACE = 'HEATED_SPACE',
  ORDINARY_ROOM = 'ORDINARY_ROOM'
}

export interface ITransport {
  id?: number;
  vehicleNumber?: string;
  wagonsNumber?: string;
  deliveryType?: DeliveryType;
  facility?: Facility;
}

export const defaultValue: Readonly<ITransport> = {};
