import React from 'react';
import { Translate } from 'react-jhipster';

import { DropdownMenu, DropdownToggle, NavbarBrand, NavItem, NavLink, UncontrolledDropdown } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import appConfig from 'app/config/constants';

export const NavDropdown = props => (
  <UncontrolledDropdown nav inNavbar id={props.id}>
    <DropdownToggle nav caret className="d-flex align-items-center">
      <FontAwesomeIcon icon={props.icon} />
      <span>{props.name}</span>
    </DropdownToggle>
    <DropdownMenu right style={props.style}>
      {props.children}
    </DropdownMenu>
  </UncontrolledDropdown>
);

export const BrandIcon = props => (
  <div {...props} className="brand-icon">
    <img src="content/images/logo-jhipster.png" alt="Logo" />
  </div>
);

export const Brand = props => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    <BrandIcon />
    <span className="brand-title" id="companyName">
      {props.companyName ? props.companyName : 'StoreHouse'}
    </span>
    <span className="navbar-version">{appConfig.VERSION}</span>
  </NavbarBrand>
);

export const Home = props => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" />
      <span>
        <Translate contentKey="global.menu.home">Home</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const Ttn = props => (
  <NavItem>
    <NavLink tag={Link} to="/ttn" className="d-flex align-items-center">
      <span>TTN</span>
    </NavLink>
  </NavItem>
);

export const Companies = props => (
  <NavItem>
    <NavLink tag={Link} to="/companies" className="d-flex align-items-center">
      <span>
        <Translate contentKey="global.menu.companies">Companies</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const Transporter = props => (
  <NavItem>
    <NavLink tag={Link} to="/transporter" className="d-flex align-items-center">
      <span>Transporter</span>
    </NavLink>
  </NavItem>
);

export const Transport = props => (
  <NavItem>
    <NavLink tag={Link} to="/transport" className="d-flex align-items-center">
      <span>Transport</span>
    </NavLink>
  </NavItem>
);

export const Users = props => (
  <NavItem>
    <NavLink tag={Link} to="/employees" className="d-flex align-items-center">
      <Translate contentKey="global.menu.employees">Employees</Translate>
    </NavLink>
  </NavItem>
);

export const Storehouse = props => (
  <NavItem>
    <NavLink tag={Link} to="/storehouse" className="d-flex align-items-center">
      <Translate contentKey="global.menu.storehouse">Add storehouse</Translate>
      </NavLink>
  </NavItem>
 );

export const Act = props => (
  <NavItem>
    <NavLink tag={Link} to="/act" className="d-flex align-items-center">
      <Translate contentKey="global.menu.entities.act">Act</Translate>
    </NavLink>
  </NavItem>
);

export const Product = props => (
  <NavItem>
    <NavLink tag={Link} to="/product" className="d-flex align-items-center">
      <Translate contentKey="global.menu.entities.product">Product</Translate>
    </NavLink>
  </NavItem>
);
