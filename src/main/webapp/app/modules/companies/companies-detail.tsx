import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Badge, Button, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { getUser } from './companies.reducer';
import { IRootState } from 'app/shared/reducers';

export interface ICompaniesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ login: string }> {}

export class CompaniesDetail extends React.Component<ICompaniesDetailProps> {
  componentDidMount() {
    this.props.getUser(this.props.match.params.login);
  }

  render() {
    const { user } = this.props;
    return (
      <div>
        <h2>
          <Translate contentKey="companies.detail.title">Company</Translate> <b>{user.company}</b>
          &nbsp;
          {user.activated ? (
            <Badge color="success">
              <Translate contentKey="companies.activated">Activated</Translate>
            </Badge>
          ) : (
            <Badge color="danger">
              <Translate contentKey="companies.deactivated">Deactivated</Translate>
            </Badge>
          )}
        </h2>
        <Row className="md">
          <dl className="jh-entity-details">
            <dt>
              <Translate contentKey="companies.createdDate">Created Date</Translate>
            </dt>
            <dd>
              <TextFormat value={user.createdDate} type="date" format={APP_DATE_FORMAT} blankOnInvalid />
            </dd>
            <dt>
              <Translate contentKey="userManagement.lastModifiedDate">Last Modified Date</Translate>
            </dt>
            <dd>
              <TextFormat value={user.lastModifiedDate} type="date" format={APP_DATE_FORMAT} blankOnInvalid />
            </dd>
          </dl>
        </Row>
        <h3>
          <Translate contentKey="companies.detail.adminInfo">Admin info</Translate>
        </h3>
        <Row size="md">
          <dl className="jh-entity-details">
            <dt>
              <Translate contentKey="companies.login">Login</Translate>
            </dt>
            <dd>
              <span>{user.login}</span>
            </dd>
            <dt>
              <Translate contentKey="companies.firstName">First Name</Translate>
            </dt>
            <dd>{user.firstName}</dd>
            <dt>
              <Translate contentKey="companies.lastName">Last Name</Translate>
            </dt>
            <dd>{user.lastName}</dd>
            <dt>
              <Translate contentKey="companies.email">Email</Translate>
            </dt>
            <dd>{user.email}</dd>
            <dt>
              <Translate contentKey="companies.country">Country</Translate>
            </dt>
            <dd>{user.country}</dd>
            <dt>
              <Translate contentKey="companies.city">City</Translate>
            </dt>
            <dd>{user.city}</dd>
            <dt>
              <Translate contentKey="companies.address">Address line</Translate>
            </dt>
            <dd>{user.address}</dd>
          </dl>
        </Row>
        <Button tag={Link} to="/companies" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  user: storeState.companies.user
});

const mapDispatchToProps = { getUser };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CompaniesDetail);
