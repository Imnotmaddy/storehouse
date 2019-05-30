import { ITransport } from 'app/shared/model/transport.model';

export interface ITransporter {
  id?: number;
  companyName?: string;
  vehicles?: ITransport[];
}

export const defaultValue: Readonly<ITransporter> = {};
