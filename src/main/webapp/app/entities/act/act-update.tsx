import React from 'react';
import { connect } from 'react-redux';

import { RouteComponentProps } from 'react-router-dom';
import { Alert, Button, Col, Input, Label, Row, Table } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { log, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { createEntity, getEntity, reset, updateEntity } from './act.reducer';
import { getTtnProducts } from 'app/entities/product/product.reducer';
import { IProduct, ProductState } from 'app/shared/model/product.model';
import generate from '@babel/generator';
import { ActType } from 'app/shared/model/act.model';

// tslint:disable-next-line:no-unused-variable

export interface IActUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IActUpdateState {
  isNew: boolean;
  userId: string;
  cost: string;
  products: IProduct[];
  rows: any[];
  isAlertShown: boolean;
  statusTypes: string[];
}

const TTN_ID_PARAM = 'ttnId';

export class ActUpdate extends React.Component<IActUpdateProps, IActUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      userId: '0',
      cost: '0',
      products: [],
      rows: [],
      isAlertShown: false,
      statusTypes: [],
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    const requestParams = new URLSearchParams(this.props.location.search);
    const ttnId = requestParams.get(TTN_ID_PARAM);

    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
    this.props.getUsers();
    // @ts-ignore
    this.props.getTtnProducts(ttnId).then(response => {
      const products = response.value.data;
      const rows = new Array(products.length);
      for (let i = 0; i < rows.length; i++) {
        rows[i] = {
          checked: false,
          quantity: 0,
          state: ''
        };
      }
      this.setState({ products, rows });
    });
  }

  saveEntity = (event, errors, values) => {
    if (!this.state.rows.filter(row => row.checked).length) {
      this.setState({ isAlertShown: true });
      return false;
    } else if (errors.length === 0) {
      const { actEntity } = this.props;
      const entity = {
        ...actEntity,
        ...values,
        products: this.getActProducts(),
        userId: this.props.userId
      };
      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  getActProducts = (): IProduct[] => {
    const products = [];
    this.state.rows.forEach((row, i) => {
      if (row.checked === true && row.state !== '' && this.checkQuantity(row.quantity, this.state.products[i].quantity)) {
        products.push({
          ...this.state.products[i],
          quantity: row.quantity,
          state: row.state
        });
      }
    });
    return products;
  };

  handleCost = event => {
    this.setState({ cost: event.target.value });
  };

  handleCheckbox = event => {
    const target = event.currentTarget;
    const index = this.parseId(target.name);
    const arr = [...this.state.rows];
    arr[index] = {
      ...arr[index],
      checked: target.checked
    };
    this.setState({ rows: arr });
  };

  handleQuantity = event => {
    const target = event.currentTarget;
    const value = target.value;
    const index = this.parseId(target.name);
    if (value > 0 && value <= this.state.products[index].quantity) {
      const arr = [...this.state.rows];
      arr[index] = {
        ...arr[index],
        quantity: value
      };
      this.setState({ rows: arr });
    }
  };

  handleState = event => {
    const target = event.currentTarget;
    const value = target.value;
    const index = this.parseId(target.name);
    const arr = [...this.state.rows];
    arr[index] = {
      ...arr[index],
      state: value
    };
    this.setState({ rows: arr });
  };

  checkQuantity = (actualQuantity: number, desiredQuantity: number): boolean => actualQuantity > 0 && actualQuantity <= desiredQuantity;

  parseId = (componentName: string) => parseInt(componentName.charAt(componentName.length - 1), 10);

  changeOptions = event => {
    {
      const target = event.target;

      switch (target.value) {
        case ActType.THEFT: {
          const arr = [ProductState.STOLEN_FROM_STORAGE];
          this.setState({ statusTypes: arr });
          break;
        }
        case ActType.LOSS: {
          const arr = [ProductState.LOST_BY_TRANSPORTER];
          this.setState({ statusTypes: arr });
          break;
        }
        case ActType.WRITE_OFF: {
          const arr = [ProductState.LOST_FROM_STORAGE, ProductState.RECYCLED];
          this.setState({ statusTypes: arr });
          break;
        }
        case ActType.INCONSISTENCY: {
          const arr = [ProductState.CONFISCATED, ProductState.RECYCLED, ProductState.TRANSPORTER_SHORTAGE];
          this.setState({ statusTypes: arr });
          break;
        }
        default: {
          this.setState({ statusTypes: [] });
          break;
        }
      }
    }
  };

  generateStatus = () => this.state.statusTypes.map(status => <option value={status}>{status}</option>);

  genRows = () =>
    this.state.products.map((row, i) => (
      <tr key={i}>
        <td style={{ paddingTop: '20px', paddingLeft: '40px' }}>
          <Input type="checkbox" name={'checkbox' + i} onChange={this.handleCheckbox} />
        </td>
        <td>{row.name}</td>
        <td>{row.cost}</td>
        <td>{row.quantity}</td>
        <td>
          <AvForm>
            <AvInput
              bsSize="sm"
              name={'quantity' + i}
              className="form-control"
              type="number"
              onInput={this.handleQuantity}
              validate={{
                min: {
                  value: 0
                },
                max: {
                  value: this.state.products[i].quantity
                }
              }}
            />
          </AvForm>
        </td>
        <td>
          <AvForm>
            <AvInput bsSize="sm" name={'state' + i} className="form-control" onChange={this.handleState} type="select">
              <option defaultChecked />
              {this.generateStatus()}
            </AvInput>
          </AvForm>
        </td>
      </tr>
    ));

  handleClose = () => {
    this.props.history.push('/ttn');
  };

  render() {
    const { actEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="storeHouseApp.act.home.createOrEditLabel">
              <Translate contentKey="storeHouseApp.act.home.createOrEditLabel">Create or edit a Act</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <div>
                {this.state.isAlertShown && <Alert color="danger">Please select products</Alert>}
                <AvForm model={isNew ? {} : actEntity} id="form" onSubmit={this.saveEntity}>
                  {!isNew ? (
                    <AvGroup>
                      <Label for="id">
                        <Translate contentKey="global.field.id">ID</Translate>
                      </Label>
                      <AvInput id="act-id" type="text" className="form-control" name="id" required readOnly />
                    </AvGroup>
                  ) : null}
                  <AvGroup>
                    <Label id="dateLabel" for="date">
                      <Translate contentKey="storeHouseApp.act.date">Date</Translate>
                    </Label>
                    <AvField
                      id="act-date"
                      type="date"
                      className="form-control"
                      name="date"
                      validate={{
                        required: {
                          value: true,
                          errorMessage: translate('entity.validation.required')
                        }
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label id="costLabel" for="cost">
                      <Translate contentKey="storeHouseApp.act.cost">Cost</Translate>
                    </Label>
                    <AvField
                      id="act-cost"
                      type="string"
                      className="form-control"
                      name="cost"
                      value={this.state.cost}
                      onChange={this.handleCost}
                      validate={{
                        required: {
                          value: true,
                          errorMessage: translate('entity.validation.required')
                        },
                        number: {
                          value: true,
                          errorMessage: translate('entity.validation.number')
                        }
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label id="typeLabel">
                      <Translate contentKey="storeHouseApp.act.type">Type</Translate>
                    </Label>
                    <AvInput
                      id="act-type"
                      type="select"
                      className="form-control"
                      name="type"
                      value={!isNew && actEntity.type}
                      onChange={this.changeOptions}
                    >
                      <option defaultChecked />
                      <option value="THEFT">
                        <Translate contentKey="storeHouseApp.ActType.THEFT" />
                      </option>
                      <option value="INCONSISTENCY">
                        <Translate contentKey="storeHouseApp.ActType.INCONSISTENCY" />
                      </option>
                      <option value="LOSS">
                        <Translate contentKey="storeHouseApp.ActType.LOSS" />
                      </option>
                      <option value="WRITE_OFF">
                        <Translate contentKey="storeHouseApp.ActType.WRITE_OFF" />
                      </option>
                    </AvInput>
                  </AvGroup>
                </AvForm>
                <Table name="productsTable" responsive size="sm">
                  <thead>
                    <tr>
                      <th />
                      <th>
                        <Translate contentKey="storeHouseApp.tTN.name">Product Name</Translate>
                      </th>
                      <th>Cost</th>
                      <th>
                        <Translate contentKey="storeHouseApp.tTN.quantity">Quantity</Translate>
                      </th>
                      <th>
                        <span>Spoiled products</span>
                      </th>
                      <th>
                        <span>Status</span>
                      </th>
                    </tr>
                  </thead>
                  <tbody>{this.genRows()}</tbody>
                </Table>
                <Button color="primary" id="save-entity" type="submit" form="form" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </div>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  userId: storeState.authentication.account.id,
  actEntity: storeState.act.entity,
  loading: storeState.act.loading,
  updating: storeState.act.updating,
  updateSuccess: storeState.act.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
  getTtnProducts
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ActUpdate);
