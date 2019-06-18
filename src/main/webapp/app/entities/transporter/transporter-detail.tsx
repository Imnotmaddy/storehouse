import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './transporter.reducer';
import { ITransporter } from 'app/shared/model/transporter.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, AUTHORITIES } from 'app/config/constants';
import { hasAnyAuthority } from 'app/shared/auth/private-route';

export interface ITransporterDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
  isAuthenticated: boolean;
  isAdmin: boolean;
  isDispatcher: boolean;
  isManager: boolean;
  isStorehouseAdmin: boolean;
  isOwner: boolean;
}

export class TransporterDetail extends React.Component<ITransporterDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { transporterEntity, isAuthenticated, isAdmin, isDispatcher, isManager, isStorehouseAdmin, isOwner } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="storeHouseApp.transporter.detail.title">Transporter</Translate> [<b>{transporterEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="companyName">
                <Translate contentKey="storeHouseApp.transporter.companyName">Company Name</Translate>
              </span>
            </dt>
            <dd>{transporterEntity.companyName}</dd>
          </dl>
          <Button tag={Link} to="/transporter" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          {!isOwner && (
            <Button tag={Link} to={`/transporter/${transporterEntity.id}/edit`} replace color="primary">
              <FontAwesomeIcon icon="pencil-alt" />{' '}
              <span className="d-none d-md-inline">
                <Translate contentKey="entity.action.edit">Edit</Translate>
              </span>
            </Button>
          )}
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ authentication, transporter }: IRootState) => ({
  transporterEntity: transporter.entity,
  isAuthenticated: authentication.isAuthenticated,
  isAdmin: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.ADMIN]),
  isDispatcher: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.DISPATCHER]),
  isManager: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.MANAGER]),
  isStorehouseAdmin: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.STOREHOUSE_ADMIN]),
  isOwner: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.OWNER])
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TransporterDetail);
