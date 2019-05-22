import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StorageRoom from './storage-room';
import StorageRoomDetail from './storage-room-detail';
import StorageRoomUpdate from './storage-room-update';
import StorageRoomDeleteDialog from './storage-room-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StorageRoomUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StorageRoomUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StorageRoomDetail} />
      <ErrorBoundaryRoute path={match.url} component={StorageRoom} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={StorageRoomDeleteDialog} />
  </>
);

export default Routes;
