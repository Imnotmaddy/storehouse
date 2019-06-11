export interface IUser {
  id?: any;
  login?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: any[];
  company?: string;
  birthdate?: Date;
  country?: string;
  city?: string;
  address?: string;
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  password?: string;
}

export const defaultValue: Readonly<IUser> = {
  id: '',
  login: '',
  firstName: '',
  lastName: '',
  email: '',
  activated: false,
  langKey: '',
  authorities: [],
  company: '',
  birthdate: null,
  country: '',
  city: '',
  address: '',
  createdBy: '',
  createdDate: null,
  lastModifiedBy: '',
  lastModifiedDate: null,
  password: ''
};
