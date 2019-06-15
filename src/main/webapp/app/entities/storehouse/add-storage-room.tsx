import React from 'react';
import { Button, Label, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { AddModal } from 'app/entities/storehouse/addModal';
import { IStorageRoom } from 'app/shared/model/storage-room.model';

export interface IAddStorageRoomState {
  rows: IStorageRoom[];
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

  deleteRow = event => {
    const elementId = event.currentTarget.value;
    const newRows = [...this.state.rows];
    newRows.splice(elementId, 1); // filter, const value from event
    this.props.getRows(newRows);
    this.setState({ rows: newRows });
  };

  handleModalValues = (value: IStorageRoom[]) => {
    const rows = this.state.rows.concat(value);
    this.props.getRows(rows);
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
