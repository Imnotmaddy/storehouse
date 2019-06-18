import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './act.reducer';
import { IAct } from 'app/shared/model/act.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IActDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ActDetail extends React.Component<IActDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { actEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="storeHouseApp.act.detail.title">Act</Translate> [<b>{actEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="date">
                <Translate contentKey="storeHouseApp.act.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={actEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="cost">
                <Translate contentKey="storeHouseApp.act.cost">Cost</Translate>
              </span>
            </dt>
            <dd>{actEntity.cost}</dd>
            <dt>
              <span id="type">
                <Translate contentKey="storeHouseApp.act.type">Type</Translate>
              </span>
            </dt>
            <dd>{actEntity.type}</dd>
            <dt>
              <Translate contentKey="storeHouseApp.act.user">User</Translate>
            </dt>
            <dd>{actEntity.userId ? actEntity.userId : ''}</dd>
          </dl>
          <Button tag={Link} to="/act" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/act/${actEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ act }: IRootState) => ({
  actEntity: act.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ActDetail);
