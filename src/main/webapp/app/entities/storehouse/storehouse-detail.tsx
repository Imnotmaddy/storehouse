import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './storehouse.reducer';

// tslint:disable-next-line:no-unused-variable

export interface IStorehouseDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class StorehouseDetail extends React.Component<IStorehouseDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { storehouseEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="storeHouseApp.storehouse.detail.title">Storehouse</Translate> <b>{storehouseEntity.name}</b>
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <Translate contentKey="storeHouseApp.storehouse.owner">Owner</Translate>
            </dt>
            <dd>{storehouseEntity.ownerLastName ? storehouseEntity.ownerLastName : ''}</dd>
            <dt>
              <Translate contentKey="storeHouseApp.storehouse.dispatcher">Dispatcher</Translate>
            </dt>
            <dd>{storehouseEntity.dispatcherLastName ? storehouseEntity.dispatcherLastName : ''}</dd>
            <dt>
              <Translate contentKey="storeHouseApp.storehouse.manager">Manager</Translate>
            </dt>
            <dd>{storehouseEntity.managerLastName ? storehouseEntity.managerLastName : ''}</dd>
            <dt>
              <Translate contentKey="storeHouseApp.storehouse.supervisor">Supervisor</Translate>
            </dt>
            <dd>{storehouseEntity.supervisorLastName ? storehouseEntity.supervisorLastName : ''}</dd>
          </dl>
          <Button tag={Link} to="/storehouse" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/storehouse/${storehouseEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ storehouse }: IRootState) => ({
  storehouseEntity: storehouse.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StorehouseDetail);
