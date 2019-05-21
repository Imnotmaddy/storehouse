import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './app-user.reducer';
import { IAppUser } from 'app/shared/model/app-user.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAppUserDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AppUserDetail extends React.Component<IAppUserDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { appUserEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="storeHouseApp.appUser.detail.title">AppUser</Translate> [<b>{appUserEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="birthdate">
                <Translate contentKey="storeHouseApp.appUser.birthdate">Birthdate</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={appUserEntity.birthdate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="isSuspended">
                <Translate contentKey="storeHouseApp.appUser.isSuspended">Is Suspended</Translate>
              </span>
            </dt>
            <dd>{appUserEntity.isSuspended ? 'true' : 'false'}</dd>
            <dt>
              <span id="money">
                <Translate contentKey="storeHouseApp.appUser.money">Money</Translate>
              </span>
            </dt>
            <dd>{appUserEntity.money}</dd>
            <dt>
              <Translate contentKey="storeHouseApp.appUser.user">User</Translate>
            </dt>
            <dd>{appUserEntity.userId ? appUserEntity.userId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/app-user" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/app-user/${appUserEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ appUser }: IRootState) => ({
  appUserEntity: appUser.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AppUserDetail);
