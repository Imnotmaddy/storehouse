import React from 'react';
import { Button, Col, Label, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { AvGroup, AvInput, AvForm } from 'availity-reactstrap-validation';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export class AddStorageRoom extends React.Component {
  state = {
    rows: [],
    roomNumberValue: 0,
    typeValue: ''
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

  addRow = () => {
    const roomNumber = this.state.roomNumberValue;
    const type = this.state.typeValue;
    const newRows = this.state.rows;
    if (roomNumber && type) {
      newRows.push({ roomNumber, type });
      this.setState({ rows: newRows });
    }
  };

  deleteRow = event => {
    const newRows = this.state.rows;
    newRows.splice(event.currentTarget.value, 1);
    this.setState({ rows: newRows });
  };

  handleRoomNumberChange = event => {
    this.setState({ roomNumberValue: event.target.value });
  };

  handleTypeChange = event => {
    this.setState({ typeValue: event.target.value });
  };

  render() {
    return (
      <div className="position-relative">
        <Label for="storageRoomsTable">
          <Translate contentKey="storeHouseApp.storehouse.storageRooms">Storage rooms</Translate>
        </Label>
        <Row>
          <Col md="5">
            <AvGroup className="row">
              <Label for="roomNumber" className="col-4">
                <Translate contentKey="storeHouseApp.storehouse.roomNumber">Room number</Translate>
              </Label>
              <AvInput
                name="roomNumber"
                type="number"
                className="col-8"
                value={this.state.roomNumberValue}
                onChange={this.handleRoomNumberChange}
                required
              />
            </AvGroup>
          </Col>
          <Col md="5">
            <AvGroup className="row">
              <Label for="type" className="col-3">
                <Translate contentKey="storeHouseApp.storehouse.type">Type</Translate>
              </Label>
              <AvInput name="type" type="select" className="col-9" value={this.state.typeValue} onChange={this.handleTypeChange} required>
                <option defaultChecked />
                <option value="REFRIGERATOR">REFRIGERATOR</option>
                <option value="OPEN_SPACE">OPEN_SPACE</option>
                <option value="HEATED_SPACE">HEATED_SPACE</option>
                <option value="ORDINARY_ROOM">ORDINARY_ROOM</option>
              </AvInput>
            </AvGroup>
          </Col>
          <Col md="2">
            <Button color="primary" onClick={this.addRow}>
              <Translate contentKey="storeHouseApp.storehouse.addRoom">Add room</Translate>
            </Button>
          </Col>
        </Row>
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
      </div>
    );
  }
}
