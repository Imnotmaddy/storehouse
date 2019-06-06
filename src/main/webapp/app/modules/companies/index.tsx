import React from 'react';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import { Switch } from 'react-router';
import Companies from './companies';
import CompaniesDetail from './companies-detail';
import CompaniesUpdate from './companies-update';
import CompaniesDeleteDialog from './companies-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CompaniesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:login`} component={CompaniesDetail} />
      <ErrorBoundaryRoute exact path={`${match.url}/:login/edit`} component={CompaniesUpdate} />
      <ErrorBoundaryRoute path={match.url} component={Companies} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:login/delete`} component={CompaniesDeleteDialog} />
  </>
);

export default Routes;
