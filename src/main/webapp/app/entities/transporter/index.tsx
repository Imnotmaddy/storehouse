import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Transporter from './transporter';
import TransporterDetail from './transporter-detail';
import TransporterUpdate from './transporter-update';
import TransporterDeleteDialog from './transporter-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TransporterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TransporterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TransporterDetail} />
      <ErrorBoundaryRoute path={match.url} component={Transporter} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TransporterDeleteDialog} />
  </>
);

export default Routes;
