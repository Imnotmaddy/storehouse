import React from 'react';
import { Button, Col, Label, Modal, ModalBody, ModalFooter, ModalHeader, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { AvField, AvForm, AvGroup } from 'availity-reactstrap-validation';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { AddModal } from 'app/entities/storehouse/addModal';

export interface IAddStorageRoomState {
  rows: Array<{
    roomNumber: number;
    type: string;
  }>;
  roomNumberValue: string;
  typeValue: string;
  showAddModal: boolean;
}

export interface IAddStorageRoomProps {
  getRows: Function;
}

export class AddStorageRoom extends React.Component<IAddStorageRoomProps, IAddStorageRoomState> {
  state = {
    rows: [],
    roomNumberValue: '',
    typeValue: '',
    showAddModal: false
  };

  genRows = () =>
    this.state.rows.map((row, i) => (
      <tr key={i}>
        <td>{row.roomNumber}</td>
        <td>{row.type}</td>
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

  add = () => {
    const roomNumber = this.state.roomNumberValue;
    const type = this.state.typeValue;
    if (roomNumber && type) {
      const newRows = this.state.rows.concat({ roomNumber, type });
      this.props.getRows(newRows);
      this.setState({
        rows: newRows,
        roomNumberValue: '',
        typeValue: '',
        showAddModal: false
      });
    }
  };

  deleteRow = event => {
    const newRows = this.state.rows;
    newRows.splice(event.currentTarget.value, 1); // filter, const value from event
    this.setState({ rows: newRows });
  };

  handleModalValues = (value: { roomNumber: string; type: string }) => {
    const rows = this.state.rows.concat(value);
    this.setState({
      rows,
      roomNumberValue: '',
      typeValue: '',
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
          <Label className="mr-auto" for="storageRoomsTable">
            <Translate contentKey="storeHouseApp.storehouse.storageRooms">Storage rooms</Translate>
          </Label>
          <Button size="sm" color="primary" className="mb-1" onClick={this.toggleAddModal}>
            <Translate contentKey="storeHouseApp.storehouse.addRoom">Add room</Translate>
          </Button>
        </div>
        <Table name="storageRoomsTable" responsive size="sm">
          <thead>
            <tr>
              <th>
                <Translate contentKey="storeHouseApp.storehouse.roomNumber">Room number</Translate>
              </th>
              <th>
                <Translate contentKey="storeHouseApp.storehouse.type">Type</Translate>
              </th>
              <th />
            </tr>
          </thead>
          <tbody>{this.genRows()}</tbody>
        </Table>
        <AddModal show={this.state.showAddModal} toggle={this.toggleAddModal} getValues={this.handleModalValues} />
      </div>
    );
  }
}
