import React from 'react';
import { Switch } from 'react-router-dom';
import Loadable from 'react-loadable';

import Login from 'app/modules/login/login';
import Register from 'app/modules/account/register/register';
import Activate from 'app/modules/account/activate/activate';
import PasswordResetInit from 'app/modules/account/password-reset/init/password-reset-init';
import PasswordResetFinish from 'app/modules/account/password-reset/finish/password-reset-finish';
import Logout from 'app/modules/login/logout';
import Home from 'app/modules/home/home';
import Entities from 'app/entities';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import { AUTHORITIES } from 'app/config/constants';
import TTN from 'app/entities/ttn';
import Transporter from 'app/entities/transporter';
import Transport from 'app/entities/transport';
import Companies from 'app/modules/companies';
import UserManagement from 'app/modules/administration/user-management';
import Storehouse from 'app/entities/storehouse';

// tslint:disable:space-in-parens
const Account = Loadable({
  loader: () => import(/* webpackChunkName: "account" */ 'app/modules/account'),
  loading: () => <div>loading ...</div>
});

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/administration'),
  loading: () => <div>loading ...</div>
});
// tslint:enable

const Routes = ({ match }) => (
  <div className="view-routes">
    <ErrorBoundaryRoute path="/login" component={Login} />
    <Switch>
      <ErrorBoundaryRoute path="/logout" component={Logout} />
      <ErrorBoundaryRoute path="/register" component={Register} />
      <ErrorBoundaryRoute path="/activate/:key?" component={Activate} />
      <ErrorBoundaryRoute path="/reset/request" component={PasswordResetInit} />
      <ErrorBoundaryRoute path="/reset/finish/:key?" component={PasswordResetFinish} />
      <PrivateRoute path="/admin" component={Admin} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
      <PrivateRoute path="/account" component={Account} hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.USER]} />
      <PrivateRoute path="/transport" component={Transport} hasAnyAuthorities={[AUTHORITIES.DISPATCHER]} />
      <PrivateRoute path="/transporter" component={Transporter} hasAnyAuthorities={[AUTHORITIES.DISPATCHER]} />
      <PrivateRoute path="/ttn" component={TTN} hasAnyAuthorities={[AUTHORITIES.DISPATCHER, AUTHORITIES.MANAGER]} />
      <PrivateRoute path="/entity" component={Entities} hasAnyAuthorities={[AUTHORITIES.MANAGER]} />
      <PrivateRoute path="/companies" component={Companies} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
      <PrivateRoute path="/storehouse" component={Storehouse} hasAnyAuthorities={[AUTHORITIES.STOREHOUSE_ADMIN]} />
      <PrivateRoute path="/employees" component={UserManagement} hasAnyAuthorities={[AUTHORITIES.STOREHOUSE_ADMIN]} />
      <ErrorBoundaryRoute path="/" component={Home} />
    </Switch>
  </div>
);

export default Routes;
