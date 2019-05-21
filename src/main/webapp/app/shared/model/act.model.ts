import { Moment } from 'moment';

export const enum ActType {
  THEFT = 'THEFT',
  INCONSISTENCE = 'INCONSISTENCE',
  LOSS = 'LOSS',
  SHORTAGE = 'SHORTAGE',
  WRITE_OFF = 'WRITE_OFF'
}

export interface IAct {
  id?: number;
  date?: Moment;
  cost?: number;
  type?: ActType;
  userId?: number;
}

export const defaultValue: Readonly<IAct> = {};
