import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ITransport } from 'app/shared/model/transport.model';
import { getEntities as getTransports } from 'app/entities/transport/transport.reducer';
import { ITransporter } from 'app/shared/model/transporter.model';
import { getEntities as getTransporters } from 'app/entities/transporter/transporter.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ttn.reducer';
import { ITTN } from 'app/shared/model/ttn.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

import { AddProductModal } from 'app/entities/ttn/add-product-modal';
import { AddProduct } from 'app/entities/ttn/add-product';
import { IProduct } from 'app/shared/model/product.model';

import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';

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
  nameValue: string;
  quantityValue: string;
  costValue: string;
  weightValue: string;
  requiredFacilityValue: string;
  stateValue: string;
  showAddModal: boolean;
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
      products: [],
      nameValue: '',
      quantityValue: '',
      costValue: '',
      weightValue: '',
      requiredFacilityValue: '',
      stateValue: '',
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
      const entity = this.props.getEntity(this.props.match.params.id);
      const promise = new Promise(resolve => {
        resolve(entity);
      });
      promise.then(value => {
        this.setState({ products: value.value.data.products });
      });
    }

    this.props.getUsers();
    this.props.getTransports();
    this.props.getTransporters();
  }

  genRows = () =>
    this.state.products.map((row, i) => (
      <tr key={i}>
        <td>{row.name}</td>
        <td>{row.quantity}</td>
        <td>{row.cost}</td>
        <td>{row.weight}</td>
        <td>{row.requiredFacility}</td>
        <td>{row.state}</td>
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
    const newRows = [...this.state.products];
    newRows.splice(elementId, 1); // filter, const value from event
    this.setState({ products: newRows });
  };

  handleModalValues = (value: IProduct) => {
    const products = this.state.products.concat(value);
    console.log('new products', products);
    this.setState({
      products,
      nameValue: '',
      quantityValue: '',
      costValue: '',
      weightValue: '',
      requiredFacilityValue: '',
      stateValue: '',
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
    const {
      tTNEntity,
      users,
      transports,
      transporters,
      loading,
      updating,
      isAuthenticated,
      isAdmin,
      isDispatcher,
      isManager,
      isStorehouseAdmin,
      isSupervisor
    } = this.props;
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
                  <AvField id="ttn-driverName" type="text" name="driverName" readOnly={isSupervisor} />
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
                  <AvInput id="ttn-status" type="select" className="form-control" name="status">
                    {isAuthenticated && (isDispatcher || isManager) && <option value="REGISTERED">REGISTERED</option>}
                    {isAuthenticated && isSupervisor && <option value="CHECKED">CHECKED</option>}
                    {isAuthenticated && isDispatcher && <option value="DECORATED">DECORATED</option>}
                    {isAuthenticated && isSupervisor && <option value="RELEASE_ALLOWED">RELEASE_ALLOWED</option>}
                    {isAuthenticated && isSupervisor && <option value="REMOVED_FROM_STORAGE">REMOVED_FROM_STORAGE</option>}
                  </AvInput>
                </AvGroup>
                {isAuthenticated &&
                  isDispatcher && (
                    <AvGroup>
                      <Label id="senderLabel" for="sender">
                        <Translate contentKey="storeHouseApp.tTN.sender">Sender</Translate>
                      </Label>
                      <AvField id="ttn-sender" type="text" name="sender" readOnly={isSupervisor} />
                    </AvGroup>
                  )}
                {isAuthenticated &&
                  isManager && (
                    <AvGroup>
                      <Label id="recipientLabel" for="recipient">
                        Recipient
                      </Label>
                      <AvField id="ttn-recipient" type="text" name="recipient" readOnly={isSupervisor} />
                    </AvGroup>
                  )}
                <AvGroup>
                  <Label for="transport.id">
                    <Translate contentKey="storeHouseApp.tTN.transport">Transport</Translate>
                  </Label>
                  <AvInput id="ttn-transport" type="select" className="form-control" name="transportId" readOnly={isSupervisor}>
                    <option value="" key="0" />
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
                  <AvInput id="ttn-transporter" type="select" className="form-control" name="transporterId" readOnly={isSupervisor}>
                    <option value="" key="0" />
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
            <div className="position-relative">
              <div className="d-flex">
                <Label className="mr-auto" for="productsTable">
                  <Translate contentKey="storeHouseApp.tTN.products">Products</Translate>
                </Label>
                <Button size="sm" color="primary" className="mb-1" onClick={this.toggleAddModal} disabled={isSupervisor}>
                  <Translate contentKey="storeHouseApp.tTN.addProduct">Add product</Translate>
                </Button>
              </div>
              <Table name="productsTable" responsive size="sm">
                <thead>
                  <tr>
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
                    <th />
                  </tr>
                </thead>
                <tbody>{this.genRows()}</tbody>
              </Table>
              <AddProductModal show={this.state.showAddModal} toggle={this.toggleAddModal} getValues={this.handleModalValues} />
            </div>
            <Button tag={Link} id="cancel-save" to="/ttn" replace color="info">
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

const mapStateToProps = (storeState: IRootState, authentication: IRootState) => ({
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
  updateSuccess: storeState.tTN.updateSuccess
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
