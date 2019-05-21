import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Sender from './sender';
import SenderDetail from './sender-detail';
import SenderUpdate from './sender-update';
import SenderDeleteDialog from './sender-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SenderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SenderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SenderDetail} />
      <ErrorBoundaryRoute path={match.url} component={Sender} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={SenderDeleteDialog} />
  </>
);

export default Routes;
