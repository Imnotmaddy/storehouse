import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Driver from './driver';
import Transporter from './transporter';
import Recipient from './recipient';
import Sender from './sender';
import Storehouse from './storehouse';
import StorageRoom from './storage-room';
import Address from './address';
import AppUser from './app-user';
import Product from './product';
import Transport from './transport';
import TTN from './ttn';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/driver`} component={Driver} />
      <ErrorBoundaryRoute path={`${match.url}/transporter`} component={Transporter} />
      <ErrorBoundaryRoute path={`${match.url}/recipient`} component={Recipient} />
      <ErrorBoundaryRoute path={`${match.url}/sender`} component={Sender} />
      <ErrorBoundaryRoute path={`${match.url}/storehouse`} component={Storehouse} />
      <ErrorBoundaryRoute path={`${match.url}/storage-room`} component={StorageRoom} />
      <ErrorBoundaryRoute path={`${match.url}/address`} component={Address} />
      <ErrorBoundaryRoute path={`${match.url}/app-user`} component={AppUser} />
      <ErrorBoundaryRoute path={`${match.url}/product`} component={Product} />
      <ErrorBoundaryRoute path={`${match.url}/transport`} component={Transport} />
      <ErrorBoundaryRoute path={`${match.url}/ttn`} component={TTN} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
