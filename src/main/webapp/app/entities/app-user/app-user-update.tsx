import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { getEntity, updateEntity, createEntity, reset } from './app-user.reducer';
import { IAppUser } from 'app/shared/model/app-user.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAppUserUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAppUserUpdateState {
  isNew: boolean;
  userId: string;
  addressId: string;
}

export class AppUserUpdate extends React.Component<IAppUserUpdateProps, IAppUserUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      userId: '0',
      addressId: '0',
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

    this.props.getUsers();
    this.props.getAddresses();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { appUserEntity } = this.props;
      const entity = {
        ...appUserEntity,
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
    this.props.history.push('/entity/app-user');
  };

  render() {
    const { appUserEntity, users, addresses, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="storeHouseApp.appUser.home.createOrEditLabel">
              <Translate contentKey="storeHouseApp.appUser.home.createOrEditLabel">Create or edit a AppUser</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : appUserEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="app-user-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="birthdateLabel" for="birthdate">
                    <Translate contentKey="storeHouseApp.appUser.birthdate">Birthdate</Translate>
                  </Label>
                  <AvField id="app-user-birthdate" type="date" className="form-control" name="birthdate" />
                </AvGroup>
                <AvGroup>
                  <Label id="isSuspendedLabel" check>
                    <AvInput id="app-user-isSuspended" type="checkbox" className="form-control" name="isSuspended" />
                    <Translate contentKey="storeHouseApp.appUser.isSuspended">Is Suspended</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="moneyLabel" for="money">
                    <Translate contentKey="storeHouseApp.appUser.money">Money</Translate>
                  </Label>
                  <AvField id="app-user-money" type="string" className="form-control" name="money" />
                </AvGroup>
                <AvGroup>
                  <Label for="user.id">
                    <Translate contentKey="storeHouseApp.appUser.user">User</Translate>
                  </Label>
                  <AvInput id="app-user-user" type="select" className="form-control" name="userId">
                    <option value="" key="0" />
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/app-user" replace color="info">
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
  users: storeState.userManagement.users,
  addresses: storeState.address.entities,
  appUserEntity: storeState.appUser.entity,
  loading: storeState.appUser.loading,
  updating: storeState.appUser.updating,
  updateSuccess: storeState.appUser.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getAddresses,
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
)(AppUserUpdate);
