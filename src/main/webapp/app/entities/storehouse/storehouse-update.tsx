import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAppUser } from 'app/shared/model/app-user.model';
import { getEntities as getAppUsers } from 'app/entities/app-user/app-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './storehouse.reducer';
import { IStorehouse } from 'app/shared/model/storehouse.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStorehouseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IStorehouseUpdateState {
  isNew: boolean;
  ownerId: string;
  administratorId: string;
  dispatcherId: string;
  managerId: string;
  supervisorId: string;
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

  handleClose = () => {
    this.props.history.push('/entity/storehouse');
  };

  render() {
    const { storehouseEntity, appUsers, loading, updating } = this.props;
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
                  <Label for="owner.name">
                    <Translate contentKey="storeHouseApp.storehouse.owner">Owner</Translate>
                  </Label>
                  <AvInput id="storehouse-owner" type="select" className="form-control" name="ownerId">
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
                  <Label for="administrator.name">
                    <Translate contentKey="storeHouseApp.storehouse.administrator">Administrator</Translate>
                  </Label>
                  <AvInput id="storehouse-administrator" type="select" className="form-control" name="administratorId">
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
                  <Label for="dispatcher.name">
                    <Translate contentKey="storeHouseApp.storehouse.dispatcher">Dispatcher</Translate>
                  </Label>
                  <AvInput id="storehouse-dispatcher" type="select" className="form-control" name="dispatcherId">
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
                    <Translate contentKey="storeHouseApp.storehouse.manager">Manager</Translate>
                  </Label>
                  <AvInput id="storehouse-manager" type="select" className="form-control" name="managerId">
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
                  <Label for="supervisor.name">
                    <Translate contentKey="storeHouseApp.storehouse.supervisor">Supervisor</Translate>
                  </Label>
                  <AvInput id="storehouse-supervisor" type="select" className="form-control" name="supervisorId">
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
  appUsers: storeState.appUser.entities,
  storehouseEntity: storeState.storehouse.entity,
  loading: storeState.storehouse.loading,
  updating: storeState.storehouse.updating,
  updateSuccess: storeState.storehouse.updateSuccess
});

const mapDispatchToProps = {
  getAppUsers,
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
