import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './storage-room.reducer';
import { IStorageRoom } from 'app/shared/model/storage-room.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStorageRoomDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class StorageRoomDetail extends React.Component<IStorageRoomDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { storageRoomEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="storeHouseApp.storageRoom.detail.title">StorageRoom</Translate> [<b>{storageRoomEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="amountOfDistinctProducts">
                <Translate contentKey="storeHouseApp.storageRoom.amountOfDistinctProducts">Amount Of Distinct Products</Translate>
              </span>
            </dt>
            <dd>{storageRoomEntity.amountOfDistinctProducts}</dd>
            <dt>
              <span id="type">
                <Translate contentKey="storeHouseApp.storageRoom.type">Type</Translate>
              </span>
            </dt>
            <dd>{storageRoomEntity.type}</dd>
            <dt>
              <Translate contentKey="storeHouseApp.storageRoom.storehouse">Storehouse</Translate>
            </dt>
            <dd>{storageRoomEntity.storehouseId ? storageRoomEntity.storehouseId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/storage-room" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/storage-room/${storageRoomEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ storageRoom }: IRootState) => ({
  storageRoomEntity: storageRoom.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StorageRoomDetail);
