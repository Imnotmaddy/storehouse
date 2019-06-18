import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getCompanyStorehouses, getEntities, getSearchEntities } from './storehouse.reducer';

// tslint:disable-next-line:no-unused-variable

export interface IStorehouseProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IStorehouseState {
  search: string;
}

export class Storehouse extends React.Component<IStorehouseProps, IStorehouseState> {
  state: IStorehouseState = {
    search: ''
  };

  componentDidMount() {
    this.props.getCompanyStorehouses(this.props.companyName);
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

  render() {
    const { storehouseList, match } = this.props;
    return (
      <div>
        <h2 id="storehouse-heading">
          <Translate contentKey="storeHouseApp.storehouse.home.title">Storehouses</Translate>
          {storehouseList.length < 1 ? (
            <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
              <FontAwesomeIcon icon="plus" />
              &nbsp;
              <Translate contentKey="storeHouseApp.storehouse.home.createLabel">Create new Storehouse</Translate>
            </Link>
          ) : null}
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="storeHouseApp.storehouse.name">Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {storehouseList.map((storehouse, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${storehouse.id}`} color="link" size="sm">
                      {storehouse.name}
                    </Button>
                  </td>
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

const mapStateToProps = ({ storehouse, authentication }: IRootState) => ({
  storehouseList: storehouse.entities,
  companyName: authentication.account.company
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  getCompanyStorehouses
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Storehouse);
