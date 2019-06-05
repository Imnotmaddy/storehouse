import React from 'react';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import { Switch } from 'react-router';
import Companies from './companies';
import CompaniesDetail from './companies-detail';
import CompaniesUpdate from './companies-update';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CompaniesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:login`} component={CompaniesDetail} />
      <ErrorBoundaryRoute exact path={`${match.url}/:login/edit`} component={CompaniesUpdate} />
      <ErrorBoundaryRoute path={match.url} component={Companies} />
    </Switch>
  </>
);

export default Routes;
