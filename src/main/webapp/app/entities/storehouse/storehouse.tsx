import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './storehouse.reducer';
import { IStorehouse } from 'app/shared/model/storehouse.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStorehouseProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IStorehouseState {
  search: string;
}

export class Storehouse extends React.Component<IStorehouseProps, IStorehouseState> {
  state: IStorehouseState = {
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
    const { storehouseList, match } = this.props;
    return (
      <div>
        <h2 id="storehouse-heading">
          <Translate contentKey="storeHouseApp.storehouse.home.title">Storehouses</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="storeHouseApp.storehouse.home.createLabel">Create new Storehouse</Translate>
          </Link>
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
                    placeholder={translate('storeHouseApp.storehouse.home.search')}
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
                  <Translate contentKey="storeHouseApp.storehouse.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="storeHouseApp.storehouse.owner">Owner</Translate>
                </th>
                <th>
                  <Translate contentKey="storeHouseApp.storehouse.administrator">Administrator</Translate>
                </th>
                <th>
                  <Translate contentKey="storeHouseApp.storehouse.dispatcher">Dispatcher</Translate>
                </th>
                <th>
                  <Translate contentKey="storeHouseApp.storehouse.manager">Manager</Translate>
                </th>
                <th>
                  <Translate contentKey="storeHouseApp.storehouse.supervisor">Supervisor</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {storehouseList.map((storehouse, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${storehouse.id}`} color="link" size="sm">
                      {storehouse.id}
                    </Button>
                  </td>
                  <td>{storehouse.name}</td>
                  <td>{storehouse.ownerLastName ? storehouse.ownerLastName : ''}</td>
                  <td>{storehouse.administratorLastName ? storehouse.administratorLastName : ''}</td>
                  <td>{storehouse.dispatcherLastName ? storehouse.dispatcherLastName : ''}</td>
                  <td>{storehouse.managerLastName ? storehouse.managerLastName : ''}</td>
                  <td>{storehouse.supervisorLastName ? storehouse.supervisorLastName : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${storehouse.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${storehouse.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${storehouse.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ storehouse }: IRootState) => ({
  storehouseList: storehouse.entities
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
)(Storehouse);
