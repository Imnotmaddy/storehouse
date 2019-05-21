import React from 'react';
import { Translate } from 'react-jhipster';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Button, Col, Label, Row } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';

export const TTNAdd = props => (
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
        <AvForm>
          <AvGroup>
            <Label for="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </Label>
            <AvInput id="ttn-id" type="text" className="form-control" name="id" required readOnly />
          </AvGroup>
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
            <Label id="productAmountLabel" for="productAmount">
              <Translate contentKey="storeHouseApp.tTN.productAmount">Product Amount</Translate>
            </Label>
            <AvField id="ttn-productAmount" type="string" className="form-control" name="productAmount" />
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
            <AvInput id="ttn-storehouseDispatcher" type="select" className="form-control" name="storehouseDispatcher.id">
              <option value="" key="0" />
            </AvInput>
          </AvGroup>
          <AvGroup>
            <Label for="transport.facility">
              <Translate contentKey="storeHouseApp.tTN.transport">Transport</Translate>
            </Label>
            <AvInput id="ttn-transport" type="select" className="form-control" name="transport.id">
              <option value="" key="0" />
            </AvInput>
          </AvGroup>
          <AvGroup>
            <Label for="sender.name">
              <Translate contentKey="storeHouseApp.tTN.sender">Sender</Translate>
            </Label>
            <AvInput id="ttn-sender" type="select" className="form-control" name="sender.id">
              <option value="" key="0" />
            </AvInput>
          </AvGroup>
          <AvGroup>
            <Label for="transporter.companyName">
              <Translate contentKey="storeHouseApp.tTN.transporter">Transporter</Translate>
            </Label>
            <AvInput id="ttn-transporter" type="select" className="form-control" name="transporter.id">
              <option value="" key="0" />
            </AvInput>
          </AvGroup>
          <AvGroup>
            <Label for="driver.name">
              <Translate contentKey="storeHouseApp.tTN.driver">Driver</Translate>
            </Label>
            <AvInput id="ttn-driver" type="select" className="form-control" name="driver.id">
              <option value="" key="0" />
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
          <Button color="primary" id="save-entity" type="submit">
            <FontAwesomeIcon icon="save" />
            &nbsp;
            <Translate contentKey="entity.action.save">Save</Translate>
          </Button>
        </AvForm>
      </Col>
    </Row>
  </div>
);
