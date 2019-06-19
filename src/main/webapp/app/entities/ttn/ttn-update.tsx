import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Label, Row, Table, Input } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntities as getTransports } from 'app/entities/transport/transport.reducer';
import { getEntities as getTransporters } from 'app/entities/transporter/transporter.reducer';
import { createEntity, getEntity, reset, updateEntity } from './ttn.reducer';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';

import { AddProductModal } from 'app/entities/ttn/add-product-modal';
import { IProduct } from 'app/shared/model/product.model';

import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';
import { IStorageRoom } from 'app/shared/model/storage-room.model';
import axios from 'axios';

export interface ITTNUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
  isAuthenticated: boolean;
  isAdmin: boolean;
  isDispatcher: boolean;
  isManager: boolean;
  isStorehouseAdmin: boolean;
  isSupervisor: boolean;
}

export interface ITTNUpdateState {
  isNew: boolean;
  dispatcherId: string;
  managerId: string;
  senderId: string;
  transportId: string;
  transporterId: string;
  products: IProduct[];
  managerProducts: IProduct[];
  rooms: IStorageRoom[];
  storageRoomId: string;
  nameValue: string;
  quantityValue: string;
  costValue: string;
  weightValue: string;
  requiredFacilityValue: string;
  showAddModal: boolean;
  rows: boolean[];
}

export class TTNUpdate extends React.Component<ITTNUpdateProps, ITTNUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      dispatcherId: '0',
      managerId: '0',
      senderId: '0',
      transportId: '0',
      transporterId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id,
      managerProducts: [],
      products: [],
      rooms: [],
      rows: [],
      storageRoomId: '0',
      nameValue: '',
      quantityValue: '',
      costValue: '',
      weightValue: '',
      requiredFacilityValue: '',
      showAddModal: false
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
      this.props
        .getEntity(this.props.match.params.id)
        .then(response => {
          this.setState({ products: response.value.data.products });
        })
        .catch(() => {
          this.setState({ products: [] });
        });
    }

    const requestUrl = `/api/products/getByStorehouseId/${this.props.storehouseId}`;
    axios
      .get<IProduct[]>(requestUrl)
      .then(response => {
        const rows = new Array(response.data.length);
        for (let i = 0; i < rows.length; i++) {
          rows[i] = false;
        }
        const newState = { ...this.state, managerProducts: response.data, rows: rows };
        this.setState(newState);
      })
      .catch(error => {
        console.log('ERROR', error);
        this.setState({ managerProducts: [] });
      });

    this.props.getUsers();
    this.props.getTransports();
    this.props.getTransporters();
  }

  getRooms = () => {
    return this.props.storehouseId;
  };

  genRows = () =>
    this.state.products.map((row, i) => (
      <tr key={i}>
        <td>{row.name}</td>
        <td>{row.quantity}</td>
        <td>{row.cost}</td>
        <td>{row.weight}</td>
        <td>{row.requiredFacility}</td>
        <td>{row.state}</td>
        <td>{row.storageRoomId}</td>
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

  genRowsForManager = () =>
    this.state.managerProducts.map((row, i) => (
      <tr key={i}>
        <td style={{ paddingTop: '20px' }}>
          <Input type="checkbox" value={row} name={'checkbox' + i} onChange={this.handleCheckbox} />
        </td>
        <td>{row.name}</td>
        <td>{row.quantity}</td>
        <td>{row.cost}</td>
        <td>{row.weight}</td>
        <td>{row.requiredFacility}</td>
        <td>{row.state}</td>
        <td>{row.storageRoomId}</td>
      </tr>
    ));

  parseId = (componentName: string) => parseInt(componentName.charAt(componentName.length - 1), 10);

  handleCheckbox = event => {
    const target = event.currentTarget;
    const index = this.parseId(target.name);
    const arr = [...this.state.rows];
    arr[index] = target.checked;
    this.setState({ rows: arr });
  };

  deleteRow = event => {
    const elementId = event.currentTarget.value;
    const newRows = [...this.state.products];
    newRows.splice(elementId, 1); // filter, const value from event
    this.setState({ products: newRows });
  };

  handleModalValues = (value: IProduct) => {
    const products = this.state.products.concat(value);
    this.setState({
      products,
      nameValue: '',
      quantityValue: '',
      costValue: '',
      weightValue: '',
      requiredFacilityValue: '',
      rooms: [],
      storageRoomId: '',
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

  saveEntity = (event, errors, values) => {
    values.dateTimeOfRegistration = convertDateTimeToServer(values.dateTimeOfRegistration);
    const products = this.state.products;
    this.state.rows.forEach((row, i) => {
      if (row === true) products.push(this.state.managerProducts[i]);
    });
    console.log('PRODUCTS', this.state.products);
    if (errors.length === 0) {
      const { tTNEntity } = this.props;
      const entity = {
        ...tTNEntity,
        ...values,
        products: this.state.products
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/ttn');
  };

  render() {
    const { tTNEntity, transports, transporters, loading, updating, isAuthenticated, isDispatcher, isManager, isSupervisor } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="storeHouseApp.tTN.home.createOrEditLabel">
              <Translate contentKey="storeHouseApp.tTN.home.createOrEditLabel">Create or edit a TTN</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : tTNEntity} id="ttnForm" onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="ttn-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="serialNumberLabel" for="serialNumber">
                    <Translate contentKey="storeHouseApp.tTN.serialNumber">Serial Number</Translate>
                  </Label>
                  <AvField
                    id="ttn-serialNumber"
                    type="text"
                    name="serialNumber"
                    validate={{
                      required: {
                        value: true,
                        errorMessage: translate('entity.validation.required')
                      }
                    }}
                    readOnly={isSupervisor}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="dateOfCreationLabel" for="dateOfCreation">
                    <Translate contentKey="storeHouseApp.tTN.dateOfCreation">Date Of Creation</Translate>
                  </Label>
                  <AvField
                    id="ttn-dateOfCreation"
                    type="date"
                    className="form-control"
                    name="dateOfCreation"
                    validate={{
                      required: {
                        value: true,
                        errorMessage: translate('entity.validation.required')
                      }
                    }}
                    readOnly={isSupervisor}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="storeHouseApp.tTN.description">Description</Translate>
                  </Label>
                  <AvField id="ttn-description" type="text" name="description" readOnly={isSupervisor} />
                </AvGroup>
                <AvGroup>
                  <Label id="driverNameLabel" for="driverName">
                    <Translate contentKey="storeHouseApp.tTN.driverName">Driver Name</Translate>
                  </Label>
                  <AvField
                    id="ttn-driverName"
                    type="text"
                    name="driverName"
                    readOnly={isSupervisor}
                    validate={{
                      required: {
                        value: true,
                        errorMessage: translate('entity.validation.required')
                      }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="dateTimeOfRegistrationLabel" for="dateTimeOfRegistration">
                    <Translate contentKey="storeHouseApp.tTN.dateTimeOfRegistration">Date Time Of Registration</Translate>
                  </Label>
                  <AvInput
                    id="ttn-dateTimeOfRegistration"
                    type="datetime-local"
                    className="form-control"
                    name="dateTimeOfRegistration"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.tTNEntity.dateTimeOfRegistration)}
                    validate={{
                      required: {
                        value: true,
                        errorMessage: translate('entity.validation.required')
                      }
                    }}
                    readOnly={isSupervisor}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">Status</Label>
                  <AvInput
                    id="ttn-status"
                    type="select"
                    className="form-control"
                    name="status"
                    validate={{
                      required: {
                        value: true,
                        errorMessage: translate('entity.validation.required')
                      }
                    }}
                  >
                    <option value="" key="0" defaultChecked />
                    {isAuthenticated && isDispatcher && <option value="EDITING_BY_DISPATCHER">Editing</option>}
                    {isAuthenticated && isManager && <option value="EDITING_BY_MANAGER">Editing</option>}
                    {isAuthenticated && (isDispatcher || isManager) && <option value="REGISTERED">Registered</option>}
                    {isAuthenticated && isSupervisor && <option value="CHECKED">Checked</option>}
                    {isAuthenticated && isDispatcher && <option value="ACCEPTED_TO_STORAGE">Accepted to storage</option>}
                    {isAuthenticated && isSupervisor && <option value="RELEASE_ALLOWED">Release allowed</option>}
                    {isAuthenticated && isDispatcher && <option value="REMOVED_FROM_STORAGE">Removed from storage</option>}
                  </AvInput>
                </AvGroup>
                {isAuthenticated &&
                  (isDispatcher || isSupervisor) && (
                    <AvGroup>
                      <Label id="senderLabel" for="sender">
                        <Translate contentKey="storeHouseApp.tTN.sender">Sender</Translate>
                      </Label>
                      <AvField
                        id="ttn-sender"
                        type="text"
                        name="sender"
                        readOnly={isSupervisor}
                        validate={{
                          required: {
                            value: true,
                            errorMessage: translate('entity.validation.required')
                          }
                        }}
                      />
                    </AvGroup>
                  )}
                {isAuthenticated &&
                  (isManager || isSupervisor) && (
                    <AvGroup>
                      <Label id="recipientLabel" for="recipient">
                        Recipient
                      </Label>
                      <AvField
                        id="ttn-recipient"
                        type="text"
                        name="recipient"
                        validate={{
                          required: {
                            value: true,
                            errorMessage: translate('entity.validation.required')
                          }
                        }}
                        readOnly={isSupervisor}
                      />
                    </AvGroup>
                  )}
                <AvGroup>
                  <Label for="transport.id">
                    <Translate contentKey="storeHouseApp.tTN.transport">Transport</Translate>
                  </Label>
                  <AvInput
                    id="ttn-transport"
                    type="select"
                    className="form-control"
                    name="transportId"
                    validate={{
                      required: {
                        value: true,
                        errorMessage: translate('entity.validation.required')
                      }
                    }}
                    disabled={isSupervisor}
                  >
                    <option value="" key="0" defaultChecked />
                    {transports
                      ? transports.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.deliveryType + ' ' + otherEntity.vehicleNumber}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="transporter.companyName">
                    <Translate contentKey="storeHouseApp.tTN.transporter">Transporter</Translate>
                  </Label>
                  <AvInput
                    id="ttn-transporter"
                    type="select"
                    className="form-control"
                    name="transporterId"
                    validate={{
                      required: {
                        value: true,
                        errorMessage: translate('entity.validation.required')
                      }
                    }}
                    disabled={isSupervisor}
                  >
                    <option value="" key="0" defaultChecked />
                    {transporters
                      ? transporters.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.companyName}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
              </AvForm>
            )}
            {isAuthenticated &&
              (isDispatcher || isSupervisor || isManager) && (
                <div className="position-relative">
                  <div className="d-flex">
                    <Label className="mr-auto" for="productsTable">
                      <Translate contentKey="storeHouseApp.tTN.products">Products</Translate>
                    </Label>
                    <Button size="sm" color="primary" className="mb-1" onClick={this.toggleAddModal} hidden={isSupervisor || isManager}>
                      <Translate contentKey="storeHouseApp.tTN.addProduct">Add product</Translate>
                    </Button>
                  </div>
                  <Table name="productsTable" responsive size="sm">
                    <thead>
                      <tr>
                        {isManager && <th />}
                        <th>
                          <Translate contentKey="storeHouseApp.tTN.name">Product Name</Translate>
                        </th>
                        <th>
                          <Translate contentKey="storeHouseApp.tTN.quantity">Quantity</Translate>
                        </th>
                        <th>
                          <Translate contentKey="storeHouseApp.tTN.cost">Cost</Translate>
                        </th>
                        <th>
                          <Translate contentKey="storeHouseApp.tTN.weight">Weight</Translate>
                        </th>
                        <th>
                          <Translate contentKey="storeHouseApp.tTN.requiredFacility">Required Facility</Translate>
                        </th>
                        <th>
                          <Translate contentKey="storeHouseApp.tTN.currentState">Current State</Translate>
                        </th>
                        <th>
                          <span>Current Storage Room</span>
                        </th>
                        {!isManager && <th />}
                      </tr>
                    </thead>
                    {(isDispatcher || isSupervisor) && <tbody>{this.genRows()} </tbody>}
                    {isManager && <tbody>{this.genRowsForManager()}</tbody>}
                  </Table>
                  {(isDispatcher || isSupervisor) && (
                    <AddProductModal
                      show={this.state.showAddModal}
                      storehouseId={this.getRooms}
                      toggle={this.toggleAddModal}
                      getValues={this.handleModalValues}
                    />
                  )}
                </div>
              )}
            <Button tag={Link} id="createAct" to={`/act/new?ttnId=${tTNEntity.id}`} replace color="info" hidden={!isSupervisor}>
              <span className="d-none d-md-inline">Create Act</span>
            </Button>
            <Button tag={Link} id="cancel-save" to="/ttn" replace color="info" hidden={isSupervisor}>
              <FontAwesomeIcon icon="arrow-left" />
              &nbsp;
              <span className="d-none d-md-inline">
                <Translate contentKey="entity.action.back">Back</Translate>
              </span>
            </Button>
            &nbsp;
            <Button color="primary" id="save-entity" type="submit" form="ttnForm" disabled={updating}>
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
  isAuthenticated: storeState.authentication.isAuthenticated,
  isAdmin: hasAnyAuthority(storeState.authentication.account.authorities, [AUTHORITIES.ADMIN]),
  isDispatcher: hasAnyAuthority(storeState.authentication.account.authorities, [AUTHORITIES.DISPATCHER]),
  isManager: hasAnyAuthority(storeState.authentication.account.authorities, [AUTHORITIES.MANAGER]),
  isStorehouseAdmin: hasAnyAuthority(storeState.authentication.account.authorities, [AUTHORITIES.STOREHOUSE_ADMIN]),
  isSupervisor: hasAnyAuthority(storeState.authentication.account.authorities, [AUTHORITIES.SUPERVISOR]),
  users: storeState.userManagement.users,
  transports: storeState.transport.entities,
  transporters: storeState.transporter.entities,
  tTNEntity: storeState.tTN.entity,
  loading: storeState.tTN.loading,
  updating: storeState.tTN.updating,
  updateSuccess: storeState.tTN.updateSuccess,
  storehouseId: storeState.authentication.account.storehouseId
});

const mapDispatchToProps = {
  getUsers,
  getTransports,
  getTransporters,
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
)(TTNUpdate);
