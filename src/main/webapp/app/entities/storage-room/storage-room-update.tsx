import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IStorehouse } from 'app/shared/model/storehouse.model';
import { getEntities as getStorehouses } from 'app/entities/storehouse/storehouse.reducer';
import { getEntity, updateEntity, createEntity, reset } from './storage-room.reducer';
import { IStorageRoom } from 'app/shared/model/storage-room.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStorageRoomUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IStorageRoomUpdateState {
  isNew: boolean;
  storehouseId: string;
}

export class StorageRoomUpdate extends React.Component<IStorageRoomUpdateProps, IStorageRoomUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      storehouseId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getStorehouses();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { storageRoomEntity } = this.props;
      const entity = {
        ...storageRoomEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/storage-room');
  };

  render() {
    const { storageRoomEntity, storehouses, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="storeHouseApp.storageRoom.home.createOrEditLabel">
              <Translate contentKey="storeHouseApp.storageRoom.home.createOrEditLabel">Create or edit a StorageRoom</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : storageRoomEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="storage-room-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="roomNumberLabel" for="roomNumber">
                    <Translate contentKey="storeHouseApp.storageRoom.roomNumber">Room Number</Translate>
                  </Label>
                  <AvField
                    id="storage-room-roomNumber"
                    type="text"
                    name="roomNumber"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel">
                    <Translate contentKey="storeHouseApp.storageRoom.type">Type</Translate>
                  </Label>
                  <AvInput
                    id="storage-room-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && storageRoomEntity.type) || 'REFRIGERATOR'}
                  >
                    <option value="REFRIGERATOR">
                      <Translate contentKey="storeHouseApp.Facility.REFRIGERATOR" />
                    </option>
                    <option value="OPEN_SPACE">
                      <Translate contentKey="storeHouseApp.Facility.OPEN_SPACE" />
                    </option>
                    <option value="HEATED_SPACE">
                      <Translate contentKey="storeHouseApp.Facility.HEATED_SPACE" />
                    </option>
                    <option value="ORDINARY_ROOM">
                      <Translate contentKey="storeHouseApp.Facility.ORDINARY_ROOM" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="storehouse.id">
                    <Translate contentKey="storeHouseApp.storageRoom.storehouse">Storehouse</Translate>
                  </Label>
                  <AvInput id="storage-room-storehouse" type="select" className="form-control" name="storehouseId">
                    <option value="" key="0" />
                    {storehouses
                      ? storehouses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/storage-room" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  storehouses: storeState.storehouse.entities,
  storageRoomEntity: storeState.storageRoom.entity,
  loading: storeState.storageRoom.loading,
  updating: storeState.storageRoom.updating,
  updateSuccess: storeState.storageRoom.updateSuccess
});

const mapDispatchToProps = {
  getStorehouses,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StorageRoomUpdate);
