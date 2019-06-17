import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './transport.reducer';
import { ITransport } from 'app/shared/model/transport.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, AUTHORITIES } from 'app/config/constants';
import { hasAnyAuthority } from 'app/shared/auth/private-route';

export interface ITransportProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {
  isAuthenticated: boolean;
  isAdmin: boolean;
  isDispatcher: boolean;
  isManager: boolean;
  isStorehouseAdmin: boolean;
  isOwner: boolean;
}

export interface ITransportState {
  search: string;
}

export class Transport extends React.Component<ITransportProps, ITransportState> {
  state: ITransportState = {
    search: ''
  };

  componentDidMount() {
    this.props.getEntities();
  }

  search = () => {
    if (this.state.search) {
      this.props.getSearchEntities(this.state.search);
    }
  };

  clear = () => {
    this.setState({ search: '' }, () => {
      this.props.getEntities();
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  render() {
    const { transportList, match, isAuthenticated, isAdmin, isDispatcher, isManager, isStorehouseAdmin, isOwner } = this.props;
    return (
      <div>
        <h2 id="transport-heading">
          <Translate contentKey="storeHouseApp.transport.home.title">Transports</Translate>
          {!isOwner && (
            <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
              <FontAwesomeIcon icon="plus" />
              &nbsp;
              <Translate contentKey="storeHouseApp.transport.home.createLabel">Create new Transport</Translate>
            </Link>
          )}
        </h2>
        <Row>
          <Col sm="12">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput
                    type="text"
                    name="search"
                    value={this.state.search}
                    onChange={this.handleSearch}
                    placeholder={translate('storeHouseApp.transport.home.search')}
                  />
                  <Button className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={this.clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </InputGroup>
              </AvGroup>
            </AvForm>
          </Col>
        </Row>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="storeHouseApp.transport.vehicleNumber">Vehicle Number</Translate>
                </th>
                <th>
                  <Translate contentKey="storeHouseApp.transport.deliveryType">Delivery Type</Translate>
                </th>
                <th>
                  <Translate contentKey="storeHouseApp.transport.company">Company</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {transportList.map((transport, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${transport.id}`} color="link" size="sm">
                      {transport.id}
                    </Button>
                  </td>
                  <td>{transport.vehicleNumber}</td>
                  <td>
                    <Translate contentKey={`storeHouseApp.DeliveryType.${transport.deliveryType}`} />
                  </td>
                  <td>
                    {transport.transporterCompanyName ? (
                      <Link to={`transporter/${transport.companyId}`}>{transport.transporterCompanyName}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  {!isOwner && (
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${transport.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${transport.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${transport.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  )}
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ authentication, transport }: IRootState) => ({
  transportList: transport.entities,
  isAuthenticated: authentication.isAuthenticated,
  isAdmin: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.ADMIN]),
  isDispatcher: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.DISPATCHER]),
  isManager: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.MANAGER]),
  isStorehouseAdmin: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.STOREHOUSE_ADMIN]),
  isOwner: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.OWNER])
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Transport);
