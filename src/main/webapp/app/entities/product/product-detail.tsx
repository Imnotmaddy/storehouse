import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './product.reducer';
import { IProduct } from 'app/shared/model/product.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProductDetail extends React.Component<IProductDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { productEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="storeHouseApp.product.detail.title">Product</Translate> [<b>{productEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="storeHouseApp.product.name">Name</Translate>
              </span>
            </dt>
            <dd>{productEntity.name}</dd>
            <dt>
              <span id="quantity">
                <Translate contentKey="storeHouseApp.product.quantity">Quantity</Translate>
              </span>
            </dt>
            <dd>{productEntity.quantity}</dd>
            <dt>
              <span id="state">
                <Translate contentKey="storeHouseApp.product.state">State</Translate>
              </span>
            </dt>
            <dd>{productEntity.state}</dd>
            <dt>
              <span id="daysInStorage">
                <Translate contentKey="storeHouseApp.product.daysInStorage">Days In Storage</Translate>
              </span>
            </dt>
            <dd>{productEntity.daysInStorage}</dd>
            <dt>
              <span id="cost">
                <Translate contentKey="storeHouseApp.product.cost">Cost</Translate>
              </span>
            </dt>
            <dd>{productEntity.cost}</dd>
            <dt>
              <span id="requiredFacility">
                <Translate contentKey="storeHouseApp.product.requiredFacility">Required Facility</Translate>
              </span>
            </dt>
            <dd>{productEntity.requiredFacility}</dd>
            <dt>
              <span id="weight">
                <Translate contentKey="storeHouseApp.product.weight">Weight</Translate>
              </span>
            </dt>
            <dd>{productEntity.weight}</dd>
            <dt>
              <Translate contentKey="storeHouseApp.product.act">Act</Translate>
            </dt>
            <dd>{productEntity.actId ? productEntity.actId : ''}</dd>
            <dt>
              <Translate contentKey="storeHouseApp.product.storageRoom">Storage Room</Translate>
            </dt>
            <dd>{productEntity.storageRoomId ? productEntity.storageRoomId : ''}</dd>
            <dt>
              <Translate contentKey="storeHouseApp.product.tTN">T TN</Translate>
            </dt>
            <dd>{productEntity.tTNId ? productEntity.tTNId : ''}</dd>
          </dl>
          <Button tag={Link} to="/product" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ product }: IRootState) => ({
  productEntity: product.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProductDetail);
