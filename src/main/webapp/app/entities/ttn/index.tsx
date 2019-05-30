import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TTN from './ttn';
import TTNDetail from './ttn-detail';
import TTNUpdate from './ttn-update';
import TTNDeleteDialog from './ttn-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TTNUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TTNUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TTNDetail} />
      <ErrorBoundaryRoute path={match.url} component={TTN} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TTNDeleteDialog} />
  </>
);

export default Routes;
