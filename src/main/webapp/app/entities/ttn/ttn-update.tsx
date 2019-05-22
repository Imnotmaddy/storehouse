import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAppUser } from 'app/shared/model/app-user.model';
import { getEntities as getAppUsers } from 'app/entities/app-user/app-user.reducer';
import { ITransport } from 'app/shared/model/transport.model';
import { getEntities as getTransports } from 'app/entities/transport/transport.reducer';
import { ITransporter } from 'app/shared/model/transporter.model';
import { getEntities as getTransporters } from 'app/entities/transporter/transporter.reducer';
import { IDriver } from 'app/shared/model/driver.model';
import { getEntities as getDrivers } from 'app/entities/driver/driver.reducer';
import { IRecipient } from 'app/shared/model/recipient.model';
import { getEntities as getRecipients } from 'app/entities/recipient/recipient.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ttn.reducer';
import { ITTN } from 'app/shared/model/ttn.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITTNUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITTNUpdateState {
  isNew: boolean;
  storehouseDispatcherId: string;
  managerId: string;
  senderId: string;
  transportId: string;
  transporterId: string;
  driverId: string;
  recipientId: string;
}

export class TTNUpdate extends React.Component<ITTNUpdateProps, ITTNUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      storehouseDispatcherId: '0',
      managerId: '0',
      senderId: '0',
      transportId: '0',
      transporterId: '0',
      driverId: '0',
      recipientId: '0',
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

    this.props.getAppUsers();
    this.props.getTransports();
    this.props.getTransporters();
    this.props.getDrivers();
    this.props.getRecipients();
  }

  saveEntity = (event, errors, values) => {
    values.dateTimeOfRegistration = convertDateTimeToServer(values.dateTimeOfRegistration);

    if (errors.length === 0) {
      const { tTNEntity } = this.props;
      const entity = {
        ...tTNEntity,
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
    this.props.history.push('/entity/ttn');
  };

  render() {
    const { tTNEntity, appUsers, transports, transporters, drivers, recipients, loading, updating } = this.props;
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
              <AvForm model={isNew ? {} : tTNEntity} onSubmit={this.saveEntity}>
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
                  <AvField id="ttn-serialNumber" type="text" name="serialNumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="dateOfCreationLabel" for="dateOfCreation">
                    <Translate contentKey="storeHouseApp.tTN.dateOfCreation">Date Of Creation</Translate>
                  </Label>
                  <AvField id="ttn-dateOfCreation" type="date" className="form-control" name="dateOfCreation" />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="storeHouseApp.tTN.description">Description</Translate>
                  </Label>
                  <AvField id="ttn-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="productsAmountLabel" for="productsAmount">
                    <Translate contentKey="storeHouseApp.tTN.productsAmount">Products Amount</Translate>
                  </Label>
                  <AvField id="ttn-productsAmount" type="string" className="form-control" name="productsAmount" />
                </AvGroup>
                <AvGroup>
                  <Label id="numberOfProductEntriesLabel" for="numberOfProductEntries">
                    <Translate contentKey="storeHouseApp.tTN.numberOfProductEntries">Number Of Product Entries</Translate>
                  </Label>
                  <AvField id="ttn-numberOfProductEntries" type="string" className="form-control" name="numberOfProductEntries" />
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
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="isAcceptedLabel" check>
                    <AvInput id="ttn-isAccepted" type="checkbox" className="form-control" name="isAccepted" />
                    <Translate contentKey="storeHouseApp.tTN.isAccepted">Is Accepted</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="storehouseDispatcher.name">
                    <Translate contentKey="storeHouseApp.tTN.storehouseDispatcher">Storehouse Dispatcher</Translate>
                  </Label>
                  <AvInput id="ttn-storehouseDispatcher" type="select" className="form-control" name="storehouseDispatcherId">
                    <option value="" key="0" />
                    {appUsers
                      ? appUsers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="manager.name">
                    <Translate contentKey="storeHouseApp.tTN.manager">Manager</Translate>
                  </Label>
                  <AvInput id="ttn-manager" type="select" className="form-control" name="managerId">
                    <option value="" key="0" />
                    {appUsers
                      ? appUsers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="sender.name">
                    <Translate contentKey="storeHouseApp.tTN.sender">Sender</Translate>
                  </Label>
                  <AvInput id="ttn-sender" type="select" className="form-control" name="senderId">
                    <option value="" key="0" />
                    {appUsers
                      ? appUsers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="transport.facility">
                    <Translate contentKey="storeHouseApp.tTN.transport">Transport</Translate>
                  </Label>
                  <AvInput id="ttn-transport" type="select" className="form-control" name="transportId">
                    <option value="" key="0" />
                    {transports
                      ? transports.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.facility}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="transporter.companyName">
                    <Translate contentKey="storeHouseApp.tTN.transporter">Transporter</Translate>
                  </Label>
                  <AvInput id="ttn-transporter" type="select" className="form-control" name="transporterId">
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
                <AvGroup>
                  <Label for="driver.name">
                    <Translate contentKey="storeHouseApp.tTN.driver">Driver</Translate>
                  </Label>
                  <AvInput id="ttn-driver" type="select" className="form-control" name="driverId">
                    <option value="" key="0" />
                    {drivers
                      ? drivers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="recipient.companyName">
                    <Translate contentKey="storeHouseApp.tTN.recipient">Recipient</Translate>
                  </Label>
                  <AvInput id="ttn-recipient" type="select" className="form-control" name="recipientId">
                    <option value="" key="0" />
                    {recipients
                      ? recipients.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.companyName}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/ttn" replace color="info">
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
  appUsers: storeState.appUser.entities,
  transports: storeState.transport.entities,
  transporters: storeState.transporter.entities,
  drivers: storeState.driver.entities,
  recipients: storeState.recipient.entities,
  tTNEntity: storeState.tTN.entity,
  loading: storeState.tTN.loading,
  updating: storeState.tTN.updating,
  updateSuccess: storeState.tTN.updateSuccess
});

const mapDispatchToProps = {
  getAppUsers,
  getTransports,
  getTransporters,
  getDrivers,
  getRecipients,
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
