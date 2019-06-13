import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Label, Row } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { createEntity, getEntity, reset, updateEntity } from './storehouse.reducer';
import { AddStorageRoom } from 'app/entities/storehouse/add-storage-room';

// tslint:disable-next-line:no-unused-variable

export interface IStorehouseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IStorehouseUpdateState {
  isNew: boolean;
  ownerId: string;
  administratorId: string;
  dispatcherId: string;
  managerId: string;
  supervisorId: string;
  storageRooms: Array<{
    roomNumber: string;
    type: string;
  }>;
}

export class StorehouseUpdate extends React.Component<IStorehouseUpdateProps, IStorehouseUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      ownerId: '0',
      administratorId: '0',
      dispatcherId: '0',
      managerId: '0',
      supervisorId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id,
      storageRooms: []
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

    this.props.getUsers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { storehouseEntity } = this.props;
      const entity = {
        ...storehouseEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleStorageRoomsUpdate = (storageRooms: []) => {
    this.setState({ storageRooms });
  };

  handleClose = () => {
    this.props.history.push('/storehouse');
  };

  render() {
    const { storehouseEntity, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="storeHouseApp.storehouse.home.createOrEditLabel">
              <Translate contentKey="storeHouseApp.storehouse.home.createOrEditLabel">Create or edit a Storehouse</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : storehouseEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="storehouse-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="storeHouseApp.storehouse.name">Name</Translate>
                  </Label>
                  <AvField
                    id="storehouse-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AddStorageRoom getRows={this.handleStorageRoomsUpdate} />
                <AvGroup>
                  <Label for="owner.lastName">
                    <Translate contentKey="storeHouseApp.storehouse.owner">Owner</Translate>
                  </Label>
                  <AvInput id="storehouse-owner" type="select" className="form-control" name="ownerId">
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.lastName}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="administrator.lastName">
                    <Translate contentKey="storeHouseApp.storehouse.administrator">Administrator</Translate>
                  </Label>
                  <AvInput id="storehouse-administrator" type="select" className="form-control" name="administratorId">
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.lastName}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="dispatcher.lastName">
                    <Translate contentKey="storeHouseApp.storehouse.dispatcher">Dispatcher</Translate>
                  </Label>
                  <AvInput id="storehouse-dispatcher" type="select" className="form-control" name="dispatcherId">
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.lastName}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="manager.lastName">
                    <Translate contentKey="storeHouseApp.storehouse.manager">Manager</Translate>
                  </Label>
                  <AvInput id="storehouse-manager" type="select" className="form-control" name="managerId">
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.lastName}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="supervisor.lastName">
                    <Translate contentKey="storeHouseApp.storehouse.supervisor">Supervisor</Translate>
                  </Label>
                  <AvInput id="storehouse-supervisor" type="select" className="form-control" name="supervisorId">
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.lastName}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/storehouse" replace color="info">
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
  users: storeState.userManagement.users,
  storehouseEntity: storeState.storehouse.entity,
  loading: storeState.storehouse.loading,
  updating: storeState.storehouse.updating,
  updateSuccess: storeState.storehouse.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
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
)(StorehouseUpdate);
