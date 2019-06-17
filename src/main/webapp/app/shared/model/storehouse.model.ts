import { IStorageRoom } from 'app/shared/model/storage-room.model';
import { IUser } from 'app/shared/model/user.model';

export interface IStorehouse {
  id?: number;
  name?: string;
  companyName?: string;
  employees?: IUser[];
  rooms?: IStorageRoom[];
}

export const defaultValue: Readonly<IStorehouse> = {};
