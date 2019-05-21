import { Moment } from 'moment';

export interface IAppUser {
  id?: number;
  birthdate?: Moment;
  isSuspended?: boolean;
  money?: number;
  userId?: number;
  addressId?: number;
}

export const defaultValue: Readonly<IAppUser> = {
  isSuspended: false
};
