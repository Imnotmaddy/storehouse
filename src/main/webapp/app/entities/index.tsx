import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Act from './act';
import Storehouse from './storehouse';
import StorageRoom from './storage-room';
import Product from './product';
import Transporter from './transporter';
import Transport from './transport';
import TTN from './ttn';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/act`} component={Act} />
      <ErrorBoundaryRoute path={`${match.url}/storehouse`} component={Storehouse} />
      <ErrorBoundaryRoute path={`${match.url}/storage-room`} component={StorageRoom} />
      <ErrorBoundaryRoute path={`${match.url}/product`} component={Product} />
      <ErrorBoundaryRoute path={`${match.url}/transporter`} component={Transporter} />
      <ErrorBoundaryRoute path={`${match.url}/transport`} component={Transport} />
      <ErrorBoundaryRoute path={`${match.url}/ttn`} component={TTN} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
