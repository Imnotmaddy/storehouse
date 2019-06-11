import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Storehouse from './storehouse';
import StorehouseDetail from './storehouse-detail';
import StorehouseUpdate from './storehouse-update';
import StorehouseDeleteDialog from './storehouse-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StorehouseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StorehouseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StorehouseDetail} />
      <ErrorBoundaryRoute path={match.url} component={Storehouse} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={StorehouseDeleteDialog} />
  </>
);

export default Routes;
