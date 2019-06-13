import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ttn.reducer';
import { ITTN } from 'app/shared/model/ttn.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITTNDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TTNDetail extends React.Component<ITTNDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { tTNEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="storeHouseApp.tTN.detail.title">TTN</Translate> [<b>{tTNEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="serialNumber">
                <Translate contentKey="storeHouseApp.tTN.serialNumber">Serial Number</Translate>
              </span>
            </dt>
            <dd>{tTNEntity.serialNumber}</dd>
            <dt>
              <span id="dateOfCreation">
                <Translate contentKey="storeHouseApp.tTN.dateOfCreation">Date Of Creation</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={tTNEntity.dateOfCreation} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="description">
                <Translate contentKey="storeHouseApp.tTN.description">Description</Translate>
              </span>
            </dt>
            <dd>{tTNEntity.description}</dd>
            <dt>
              <span id="driverName">
                <Translate contentKey="storeHouseApp.tTN.driverName">Driver Name</Translate>
              </span>
            </dt>
            <dd>{tTNEntity.driverName}</dd>
            <dt>
              <span id="productsAmount">
                <Translate contentKey="storeHouseApp.tTN.productsAmount">Products Amount</Translate>
              </span>
            </dt>
            <dd>{tTNEntity.productsAmount}</dd>
            <dt>
              <span id="numberOfProductEntries">
                <Translate contentKey="storeHouseApp.tTN.numberOfProductEntries">Number Of Product Entries</Translate>
              </span>
            </dt>
            <dd>{tTNEntity.numberOfProductEntries}</dd>
            <dt>
              <span id="dateTimeOfRegistration">
                <Translate contentKey="storeHouseApp.tTN.dateTimeOfRegistration">Date Time Of Registration</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={tTNEntity.dateTimeOfRegistration} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="isAccepted">
                <Translate contentKey="storeHouseApp.tTN.isAccepted">Is Accepted</Translate>
              </span>
            </dt>
            <dd>{tTNEntity.isAccepted ? 'true' : 'false'}</dd>
            <dt>
              <Translate contentKey="storeHouseApp.tTN.dispatcher">Dispatcher</Translate>
            </dt>
            <dd>{tTNEntity.dispatcherLastName ? tTNEntity.dispatcherLastName : ''}</dd>
            <dt>
              <Translate contentKey="storeHouseApp.tTN.manager">Manager</Translate>
            </dt>
            <dd>{tTNEntity.managerLastName ? tTNEntity.managerLastName : ''}</dd>
            <dt>
              <span id="sender">
                <Translate contentKey="storeHouseApp.tTN.sender">Sender</Translate>
              </span>
            </dt>
            <dd>{tTNEntity.sender}</dd>
            <dt>
              <span id="recipient">Recipient</span>
            </dt>
            <dd>{tTNEntity.recipient}</dd>
            <dt>
              <Translate contentKey="storeHouseApp.tTN.transport">Transport</Translate>
            </dt>
            <dd>{tTNEntity.transportVehicleNumber ? tTNEntity.transportVehicleNumber : ''}</dd>
            <dt>
              <Translate contentKey="storeHouseApp.tTN.transporter">Transporter</Translate>
            </dt>
            <dd>{tTNEntity.transporterCompanyName ? tTNEntity.transporterCompanyName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/ttn" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/ttn/${tTNEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ tTN }: IRootState) => ({
  tTNEntity: tTN.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TTNDetail);
