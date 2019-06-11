import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table, Row } from 'reactstrap';
import {
  Translate,
  ICrudGetAllAction,
  ICrudPutAction,
  TextFormat,
  JhiPagination,
  getPaginationItemsNumber,
  getSortState,
  IPaginationBaseState
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { getUsers, updateUser, toggleEmployees } from './companies.reducer';
import { IRootState } from 'app/shared/reducers';

export interface ICompaniesProps extends StateProps, DispatchProps, RouteComponentProps<{}> {}

export class Companies extends React.Component<ICompaniesProps, IPaginationBaseState> {
  state: IPaginationBaseState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getUsers();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortUsers()
    );
  };

  sortUsers() {
    this.getUsers();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortUsers());

  getUsers = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getUsers(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  toggleActive = user => () => {
    this.props.updateUser({
      ...user,
      activated: !user.activated
    });
    this.props.toggleEmployees(user.company, !user.activated);
  };

  render() {
    const { users, account, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="companies-page-heading">
          <Translate contentKey="companies.home.title">Companies</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity">
            <FontAwesomeIcon icon="plus" /> <Translate contentKey="companies.home.addLabel">Add company</Translate>
          </Link>
        </h2>
        <Table responsive striped>
          <thead>
            <tr>
              <th className="hand" onClick={this.sort('company')}>
                <Translate contentKey="companies.companyName">Company name</Translate>
                <FontAwesomeIcon icon="sort" />
              </th>
              <th />
              <th className="hand" onClick={this.sort('createdDate')}>
                <Translate contentKey="companies.createdDate">Created Date</Translate>
                <FontAwesomeIcon icon="sort" />
              </th>
              <th id="modified-date-sort" className="hand" onClick={this.sort('lastModifiedDate')}>
                <Translate contentKey="companies.lastModifiedDate">Last Modified Date</Translate>
                <FontAwesomeIcon icon="sort" />
              </th>
              <th />
            </tr>
          </thead>
          <tbody>
            {users.map((user, i) => (
              <tr id={user.login} key={`user-${i}`}>
                <td>
                  <Button tag={Link} to={`${match.url}/${user.login}`} color="link" size="sm">
                    {user.company}
                  </Button>
                </td>
                <td>
                  {user.activated ? (
                    <Button color="success" onClick={this.toggleActive(user)}>
                      Activated
                    </Button>
                  ) : (
                    <Button color="danger" onClick={this.toggleActive(user)}>
                      Deactivated
                    </Button>
                  )}
                </td>
                <td>
                  <TextFormat value={user.createdDate} type="date" format={APP_DATE_FORMAT} blankOnInvalid />
                </td>
                <td>
                  <TextFormat value={user.lastModifiedDate} type="date" format={APP_DATE_FORMAT} blankOnInvalid />
                </td>
                <td className="text-right">
                  <div className="btn-group flex-btn-group-container">
                    <Button tag={Link} to={`${match.url}/${user.login}`} color="info" size="sm">
                      <FontAwesomeIcon icon="eye" />{' '}
                      <span className="d-none d-md-inline">
                        <Translate contentKey="entity.action.view">View</Translate>
                      </span>
                    </Button>
                    <Button tag={Link} to={`${match.url}/${user.login}/edit`} color="primary" size="sm">
                      <FontAwesomeIcon icon="pencil-alt" />{' '}
                      <span className="d-none d-md-inline">
                        <Translate contentKey="entity.action.edit">Edit</Translate>
                      </span>
                    </Button>
                    <Button
                      tag={Link}
                      to={`${match.url}/${user.login}/delete`}
                      color="danger"
                      size="sm"
                      disabled={account.login === user.login}
                    >
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
        <Row className="justify-content-center">
          <JhiPagination
            items={getPaginationItemsNumber(totalItems, this.state.itemsPerPage)}
            activePage={this.state.activePage}
            onSelect={this.handlePagination}
            maxButtons={5}
          />
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.companies.users,
  totalItems: storeState.companies.totalItems,
  account: storeState.authentication.account
});

const mapDispatchToProps = { getUsers, updateUser, toggleEmployees };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Companies);
