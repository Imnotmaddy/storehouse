import React from 'react';
import { translate, Translate } from 'react-jhipster';
import { Button, Label, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import { AvField, AvForm, AvGroup } from 'availity-reactstrap-validation';

export interface IAddModalState {
  roomNumberValue: string;
  typeValue: string;
}

export interface IAddModalProps {
  show: boolean;
  toggle: Function;
  getValues: Function;
}

export class AddModal extends React.Component<IAddModalProps, IAddModalState> {
  state = {
    roomNumberValue: null,
    typeValue: null
  };

  submit = () => {
    this.props.getValues({
      roomNumber: this.state.roomNumberValue,
      type: this.state.typeValue
    });
  };

  handleRoomNumberChange = event => {
    this.setState({ roomNumberValue: event.target.value });
  };

  handleTypeChange = event => {
    this.setState({ typeValue: event.target.value });
  };

  render() {
    return (
      <Modal isOpen={this.props.show} toggle={this.props.toggle}>
        <ModalHeader toggle={this.props.toggle}>
          <h2>Add storage room</h2>
        </ModalHeader>
        <ModalBody>
          <AvForm id="storageRoomAddForm" onValidSubmit={this.submit}>
            <AvGroup>
              <Label for="roomNumber">
                <Translate contentKey="storeHouseApp.storehouse.roomNumber">Room number</Translate>
              </Label>
              <AvField
                name="roomNumber"
                type="number"
                value={this.state.roomNumberValue}
                onChange={this.handleRoomNumberChange}
                validate={{
                  required: {
                    value: true,
                    errorMessage: translate('entity.validation.required')
                  },
                  minLength: {
                    value: 1,
                    errorMessage: translate('entity.validation.minlength', { min: 1 })
                  },
                  maxLength: {
                    value: 50,
                    errorMessage: translate('entity.validation.maxlength', { max: 50 })
                  }
                }}
              />
            </AvGroup>
            <AvGroup>
              <Label for="type">
                <Translate contentKey="storeHouseApp.storehouse.type">Type</Translate>
              </Label>
              <AvField
                name="type"
                type="select"
                value={this.state.typeValue}
                onChange={this.handleTypeChange}
                validate={{
                  required: {
                    value: true,
                    errorMessage: translate('entity.validation.required')
                  }
                }}
              >
                <option defaultChecked />
                <option value="REFRIGERATOR">REFRIGERATOR</option>
                <option value="OPEN_SPACE">OPEN_SPACE</option>
                <option value="HEATED_SPACE">HEATED_SPACE</option>
                <option value="ORDINARY_ROOM">ORDINARY_ROOM</option>
              </AvField>
            </AvGroup>
          </AvForm>
        </ModalBody>
        <ModalFooter>
          <Button onClick={this.props.toggle}>Cancel</Button>
          <Button form="storageRoomAddForm" color="primary">
            <Translate contentKey="storeHouseApp.storehouse.addRoom">Add room</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}
