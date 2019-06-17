import { IStorageRoom } from 'app/shared/model/storage-room.model';

export interface IStorehouse {
  id?: number;
  name?: string;
  companyName?: string;
  ownerLastName?: string;
  ownerId?: number;
  dispatcherLastName?: string;
  dispatcherId?: number;
  managerLastName?: string;
  managerId?: number;
  supervisorLastName?: string;
  supervisorId?: number;
  rooms?: IStorageRoom[];
}

export const defaultValue: Readonly<IStorehouse> = {};
