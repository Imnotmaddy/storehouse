export interface IDriver {
  id?: number;
  name?: string;
  passportId?: string;
  citizenship?: string;
}

export const defaultValue: Readonly<IDriver> = {};
