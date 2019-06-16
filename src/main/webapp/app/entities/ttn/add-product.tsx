import React from 'react';
import { Button, Label, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { AddProductModal } from 'app/entities/ttn/add-product-modal';

export interface IAddProductState {
  rows: Array<{
    name: string;
    quantity: number;
    cost: number;
    weight: number;
    requiredFacility: string;
    state: string;
  }>;
  nameValue: string;
  quantityValue: string;
  costValue: string;
  weightValue: string;
  requiredFacilityValue: string;
  stateValue: string;
  showAddModal: boolean;
}

export interface IAddProductProps {
  getRows: Function;
}

export class AddProduct extends React.Component<IAddProductProps, IAddProductState> {
  state = {
    rows: [],
    nameValue: '',
    quantityValue: '',
    costValue: '',
    weightValue: '',
    requiredFacilityValue: '',
    stateValue: '',
    showAddModal: false
  };

  genRows = () =>
    this.state.rows.map((row, i) => (
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
    const newRows = [...this.state.rows];
    newRows.splice(elementId, 1); // filter, const value from event
    this.props.getRows(newRows);
    this.setState({ rows: newRows });
  };

  handleModalValues = (value: {
    name: string;
    quantity: number;
    cost: number;
    weight: number;
    requiredFacility: string;
    state: string;
  }) => {
    const rows = this.state.rows.concat(value);
    this.props.getRows(rows);
    this.setState({
      rows,
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

  render() {
    return (
      <div className="position-relative">
        <div className="d-flex">
          <Label className="mr-auto" for="productsTable">
            <Translate contentKey="storeHouseApp.tTN.products">Products</Translate>
          </Label>
          <Button size="sm" color="primary" className="mb-1" onClick={this.toggleAddModal}>
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
    );
  }
}
