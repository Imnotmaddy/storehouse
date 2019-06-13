export const enum DeliveryType {
  Auto = 'Auto',
  Train = 'Train'
}

export interface ITransport {
  id?: number;
  vehicleNumber?: string;
  deliveryType?: DeliveryType;
  companyId?: number;
  transporterCompanyName?: string;
}

export const defaultValue: Readonly<ITransport> = {};
