import { IStorageRoom } from 'app/shared/model/storage-room.model';

export interface IStorehouse {
  id?: number;
  ownerName?: string;
  ownerId?: number;
  administratorName?: string;
  administratorId?: number;
  dispatcherName?: string;
  dispatcherId?: number;
  managerName?: string;
  managerId?: number;
  supervisorName?: string;
  supervisorId?: number;
  rooms?: IStorageRoom[];
}

export const defaultValue: Readonly<IStorehouse> = {};
