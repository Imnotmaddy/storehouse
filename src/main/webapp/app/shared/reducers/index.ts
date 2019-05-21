import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import driver, {
  DriverState
} from 'app/entities/driver/driver.reducer';
// prettier-ignore
import transporter, {
  TransporterState
} from 'app/entities/transporter/transporter.reducer';
// prettier-ignore
import sender, {
  SenderState
} from 'app/entities/sender/sender.reducer';
// prettier-ignore
import storehouse, {
  StorehouseState
} from 'app/entities/storehouse/storehouse.reducer';
// prettier-ignore
import storageRoom, {
  StorageRoomState
} from 'app/entities/storage-room/storage-room.reducer';
// prettier-ignore
import address, {
  AddressState
} from 'app/entities/address/address.reducer';
// prettier-ignore
import appUser, {
  AppUserState
} from 'app/entities/app-user/app-user.reducer';
// prettier-ignore
import act, {
  ActState
} from 'app/entities/act/act.reducer';
// prettier-ignore
import product, {
  ProductState
} from 'app/entities/product/product.reducer';
// prettier-ignore
import recipient, {
  RecipientState
} from 'app/entities/recipient/recipient.reducer';
// prettier-ignore
import transport, {
  TransportState
} from 'app/entities/transport/transport.reducer';
// prettier-ignore
import tTN, {
  TTNState
} from 'app/entities/ttn/ttn.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly driver: DriverState;
  readonly transporter: TransporterState;
  readonly sender: SenderState;
  readonly storehouse: StorehouseState;
  readonly storageRoom: StorageRoomState;
  readonly address: AddressState;
  readonly appUser: AppUserState;
  readonly act: ActState;
  readonly product: ProductState;
  readonly recipient: RecipientState;
  readonly transport: TransportState;
  readonly tTN: TTNState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  driver,
  transporter,
  sender,
  storehouse,
  storageRoom,
  address,
  appUser,
  act,
  product,
  recipient,
  transport,
  tTN,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
