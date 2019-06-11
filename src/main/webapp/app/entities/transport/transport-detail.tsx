import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './transport.reducer';
import { ITransport } from 'app/shared/model/transport.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITransportDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TransportDetail extends React.Component<ITransportDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { transportEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="storeHouseApp.transport.detail.title">Transport</Translate> [<b>{transportEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="vehicleNumber">
                <Translate contentKey="storeHouseApp.transport.vehicleNumber">Vehicle Number</Translate>
              </span>
            </dt>
            <dd>{transportEntity.vehicleNumber}</dd>
            <dt>
              <span id="wagonsNumber">
                <Translate contentKey="storeHouseApp.transport.wagonsNumber">Wagons Number</Translate>
              </span>
            </dt>
            <dd>{transportEntity.wagonsNumber}</dd>
            <dt>
              <span id="deliveryType">
                <Translate contentKey="storeHouseApp.transport.deliveryType">Delivery Type</Translate>
              </span>
            </dt>
            <dd>{transportEntity.deliveryType}</dd>
            <dt>
              <span id="facility">
                <Translate contentKey="storeHouseApp.transport.facility">Facility</Translate>
              </span>
            </dt>
            <dd>{transportEntity.facility}</dd>
          </dl>
          <Button tag={Link} to="/entity/transport" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/transport/${transportEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ transport }: IRootState) => ({
  transportEntity: transport.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TransportDetail);
