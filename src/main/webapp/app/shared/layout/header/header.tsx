import './header.css';

import React from 'react';
import { Storage, Translate } from 'react-jhipster';
import { Brand, Companies, Home, Transporter, Ttn, Transport, Users } from './header-components';
import { Collapse, Nav, Navbar, NavbarToggler, NavItem } from 'reactstrap';
import LoadingBar from 'react-redux-loading-bar';
import { AccountMenu, AdminMenu, EntitiesMenu, LocaleMenu } from './menus';

export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  isDispatcher: boolean;
  isManager: boolean;
  isStorehouseAdmin: boolean;
  isSupervisor: boolean;
  companyName: string;
  ribbonEnv: string;
  isInProduction: boolean;
  isSwaggerEnabled: boolean;
  currentLocale: string;
  onLocaleChange: Function;
}

export interface IHeaderState {
  menuOpen: boolean;
}

export default class Header extends React.Component<IHeaderProps, IHeaderState> {
  state: IHeaderState = {
    menuOpen: false
  };

  handleLocaleChange = event => {
    const langKey = event.target.value;
    Storage.session.set('locale', langKey);
    this.props.onLocaleChange(langKey);
  };

  renderDevRibbon = () =>
    this.props.isInProduction === false ? (
      <div className="ribbon dev">
        <a href="">
          <Translate contentKey={`global.ribbon.${this.props.ribbonEnv}`} />
        </a>
      </div>
    ) : null;

  toggleMenu = () => {
    this.setState({ menuOpen: !this.state.menuOpen });
  };

  render() {
    const {
      currentLocale,
      isAuthenticated,
      isAdmin,
      isDispatcher,
      isManager,
      isStorehouseAdmin,
      isSupervisor,
      companyName,
      isSwaggerEnabled,
      isInProduction
    } = this.props;

    /* jhipster-needle-add-element-to-menu - JHipster will add new menu items here */

    return (
      <div id="app-header">
        {this.renderDevRibbon()}
        <LoadingBar className="loading-bar" />
        <Navbar dark expand="sm" fixed="top" className="jh-navbar">
          <NavbarToggler aria-label="Menu" onClick={this.toggleMenu} />
          <Brand companyName={companyName} />
          <Collapse isOpen={this.state.menuOpen} navbar>
            <Nav id="header-tabs" className="ml-auto" navbar>
              <Home />
              {isAuthenticated && (isManager || isDispatcher || isSupervisor) && <Ttn />}
              {isAuthenticated && isDispatcher && <Transporter />}
              {isAuthenticated && isDispatcher && <Transport />}
              {isAuthenticated && isAdmin && <Companies />}
              {isAuthenticated && isStorehouseAdmin && <Users />}
              <LocaleMenu currentLocale={currentLocale} onClick={this.handleLocaleChange} />
              <AccountMenu isAuthenticated={isAuthenticated} />
            </Nav>
          </Collapse>
        </Navbar>
      </div>
    );
  }
}
