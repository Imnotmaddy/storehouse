import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Label, Row, Table } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { createEntity, getEntity, reset, updateEntity } from './storehouse.reducer';
import { getSession } from 'app/shared/reducers/authentication';
import { IStorageRoom } from 'app/shared/model/storage-room.model';
import { AddModal } from 'app/entities/storehouse/addModal';

// tslint:disable-next-line:no-unused-variable

export interface IStorehouseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IStorehouseUpdateState {
  isNew: boolean;
  ownerId: string;
  administratorId: string;
  dispatcherId: string;
  managerId: string;
  supervisorId: string;
  roomNumberValue: string;
  typeValue: string;
  showAddModal: boolean;
  storageRooms: IStorageRoom[];
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
      roomNumberValue: '',
      typeValue: '',
      showAddModal: false,
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
      const entity = this.props.getEntity(this.props.match.params.id);
      const promise = new Promise(resolve => {
        resolve(entity);
      });
      promise.then(value => {
        this.setState({ storageRooms: value.value.data.rooms });
      });
    }

    this.props.getUsers();
  }

  genRows = () =>
    this.state.storageRooms.map((row, i) => (
      <tr key={i}>
        <td>{row.roomNumber}</td>
        <td>{row.type}</td>
        <td>
          <Button color="danger" size="sm" value={i} onClick={this.deleteRow}>
            <FontAwesomeIcon icon="trash" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.delete">Delete</Translate>
            </span>
          </Button>
        </td>
      </tr>
    ));

  deleteRow = event => {
    const elementId = event.currentTarget.value;
    const newRows = [...this.state.storageRooms];
    newRows.splice(elementId, 1); // filter, const value from event
    this.setState({ storageRooms: newRows });
  };

  handleModalValues = (value: IStorageRoom) => {
    value.storehouseId = this.props.storehouseEntity.id;
    const storageRooms = this.state.storageRooms.concat(value);
    console.log('new rooms', storageRooms);
    this.setState({
      storageRooms,
      roomNumberValue: '',
      typeValue: '',
      showAddModal: false
    });
  };

  toggleAddModal = () => {
    const state = {
      ...this.state
    };
    state.showAddModal = !state.showAddModal;
    this.setState(state);
  };

  checkRoomNumber = value => !this.state.storageRooms.includes({ roomNumber: value });

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { storehouseEntity } = this.props;
      const entity = {
        ...storehouseEntity,
        ...values,
        rooms: this.state.storageRooms,
        companyName: this.props.companyName
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
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
              <AvForm model={isNew ? {} : storehouseEntity} id="storehouseForm" onSubmit={this.saveEntity}>
                {!isNew ? <AvInput id="storehouse-id" type="hidden" className="form-control" name="id" required readOnly /> : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="storeHouseApp.storehouse.name">Name</Translate>
                  </Label>
                  <AvField
                    id="storehouse-name"
                    type="text"
                    name="name"
                    validate={{
                      required: {
                        value: true,
                        errorMessage: translate('entity.validation.required')
                      }
                    }}
                  />
                </AvGroup>
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
              </AvForm>
            )}
            <div className="d-flex">
              <Label className="mr-auto" for="storageRoomsTable">
                <Translate contentKey="storeHouseApp.storehouse.storageRooms">Storage rooms</Translate>
              </Label>
              <Button size="sm" color="primary" className="mb-1" onClick={this.toggleAddModal}>
                <Translate contentKey="storeHouseApp.storehouse.addRoom">Add room</Translate>
              </Button>
            </div>
            <Table name="storageRoomsTable" responsive size="sm">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="storeHouseApp.storehouse.roomNumber">Room number</Translate>
                  </th>
                  <th>
                    <Translate contentKey="storeHouseApp.storehouse.type">Type</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>{this.genRows()}</tbody>
            </Table>
            <AddModal
              show={this.state.showAddModal}
              toggle={this.toggleAddModal}
              getValues={this.handleModalValues}
              checkRoomNumber={this.checkRoomNumber}
            />
            <Button tag={Link} id="cancel-save" to="/storehouse" replace color="info">
              <FontAwesomeIcon icon="arrow-left" />
              &nbsp;
              <span className="d-none d-md-inline">
                <Translate contentKey="entity.action.back">Back</Translate>
              </span>
            </Button>
            &nbsp;
            <Button color="primary" id="save-entity" type="submit" form="storehouseForm" disabled={updating}>
              <FontAwesomeIcon icon="save" />
              &nbsp;
              <Translate contentKey="entity.action.save">Save</Translate>
            </Button>
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  companyName: storeState.authentication.account.company,
  storehouseEntity: storeState.storehouse.entity,
  loading: storeState.storehouse.loading,
  updating: storeState.storehouse.updating,
  updateSuccess: storeState.storehouse.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getSession,
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
