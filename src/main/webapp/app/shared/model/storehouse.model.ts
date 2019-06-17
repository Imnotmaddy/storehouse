import { IStorageRoom } from 'app/shared/model/storage-room.model';
import { IUser } from 'app/shared/model/user.model';

export interface IStorehouse {
  id?: number;
  name?: string;
  companyName?: string;
  owners?: IUser[];
  dispatchers?: IUser[];
  managers?: IUser[];
  supervisors?: IUser[];
  rooms?: IStorageRoom[];
}

export const defaultValue: Readonly<IStorehouse> = {};
