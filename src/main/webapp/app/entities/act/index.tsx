import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Act from './act';
import ActDetail from './act-detail';
import ActUpdate from './act-update';
import ActDeleteDialog from './act-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ActUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ActUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ActDetail} />
      <ErrorBoundaryRoute path={match.url} component={Act} />
      <ErrorBoundaryRoute path={`${match.url}/new/:ttnId`} component={ActUpdate} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ActDeleteDialog} />
  </>
);

export default Routes;
