export const enum DeliveryType {
  Auto = 'Auto',
  Train = 'Train'
}

export interface ITransport {
  id?: number;
  vehicleNumber?: string;
  deliveryType?: DeliveryType;
  companyId?: number;
}

export const defaultValue: Readonly<ITransport> = {};
