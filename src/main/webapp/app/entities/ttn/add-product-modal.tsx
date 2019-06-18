import React from 'react';
import { translate, Translate } from 'react-jhipster';
import { Button, Label, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import { AvField, AvForm, AvGroup } from 'availity-reactstrap-validation';
import { IStorageRoom } from 'app/shared/model/storage-room.model';
import axios from 'axios';

export interface IAddProductModalState {
  nameValue: string;
  quantityValue: string;
  costValue: string;
  weightValue: string;
  requiredFacilityValue: string;
  rooms: IStorageRoom[];
  roomValue: string;
}

export interface IAddProductModalProps {
  show: boolean;
  toggle: Function;
  getValues: Function;
  storehouseId: Function;
}

export class AddProductModal extends React.Component<IAddProductModalProps, IAddProductModalState> {
  state = {
    nameValue: '',
    quantityValue: '',
    costValue: '',
    weightValue: '',
    requiredFacilityValue: '',
    rooms: [],
    roomValue: ''
  };

  submit = () => {
    this.props.getValues({
      name: this.state.nameValue,
      quantity: this.state.quantityValue,
      cost: this.state.costValue,
      weight: this.state.weightValue,
      requiredFacility: this.state.requiredFacilityValue,
      rooms: this.state.rooms,
      storageRoomId: this.state.roomValue
    });
    this.setState({
      nameValue: '',
      quantityValue: '',
      costValue: '',
      weightValue: '',
      requiredFacilityValue: '',
      roomValue: ''
    });
  };

  componentDidMount() {
    const requestUrl = `/api/storage-rooms/getByStorehouseId/${this.props.storehouseId()}`;
    axios
      .get<IStorageRoom[]>(requestUrl)
      .then(response => {
        const newState = { ...this.state, rooms: response.data };
        this.setState(newState);
      })
      .catch(error => {
        console.log('ERROR', error);
        this.setState({ rooms: [] });
      });
  }

  handleNameChange = event => {
    this.setState({ nameValue: event.target.value });
  };

  handleQuantityChange = event => {
    this.setState({ quantityValue: event.target.value });
  };

  handleCostChange = event => {
    this.setState({ costValue: event.target.value });
  };

  handleWeightChange = event => {
    this.setState({ weightValue: event.target.value });
  };

  handleRequiredFacilityChange = event => {
    this.setState({ requiredFacilityValue: event.target.value });
  };

  handleRoomChange = event => {
    this.setState({ roomValue: event.target.value });
  };

  render() {
    const { rooms } = this.state;
    return (
      <Modal isOpen={this.props.show} toggle={this.props.toggle}>
        <AvForm onValidSubmit={this.submit}>
          <ModalHeader toggle={this.props.toggle}>Add product</ModalHeader>
          <ModalBody>
            <AvGroup>
              <Label for="productName">
                <Translate contentKey="storeHouseApp.tTN.name">Name</Translate>
              </Label>
              <AvField
                name="name"
                value={this.state.nameValue}
                onChange={this.handleNameChange}
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
              <Label for="productQuantity">
                <Translate contentKey="storeHouseApp.tTN.quantity">Quantity</Translate>
              </Label>
              <AvField
                name="quantity"
                value={this.state.quantityValue}
                type="number"
                onChange={this.handleQuantityChange}
                validate={{
                  required: {
                    value: true,
                    errorMessage: translate('entity.validation.required')
                  },
                  min: {
                    value: 1,
                    errorMessage: translate('entity.validation.min', { min: 1 })
                  }
                }}
              />
            </AvGroup>
            <AvGroup>
              <Label for="productCost">
                <Translate contentKey="storeHouseApp.tTN.cost">Cost</Translate>
              </Label>
              <AvField
                name="cost"
                value={this.state.costValue}
                type="number"
                onChange={this.handleCostChange}
                validate={{
                  required: {
                    value: true,
                    errorMessage: translate('entity.validation.required')
                  },
                  min: {
                    value: 1,
                    errorMessage: translate('entity.validation.min', { min: 1 })
                  }
                }}
              />
            </AvGroup>
            <AvGroup>
              <Label for="productWeight">
                <Translate contentKey="storeHouseApp.tTN.weight">Weigth</Translate>
              </Label>
              <AvField
                name="weight"
                value={this.state.weightValue}
                type="number"
                onChange={this.handleWeightChange}
                validate={{
                  required: {
                    value: true,
                    errorMessage: translate('entity.validation.required')
                  },
                  min: {
                    value: 1,
                    errorMessage: translate('entity.validation.min', { min: 1 })
                  }
                }}
              />
            </AvGroup>
            <AvGroup>
              <Label for="requiredFacility">
                <Translate contentKey="storeHouseApp.tTN.requiredFacility">Required Facility</Translate>
              </Label>
              <AvField
                name="requiredFacility"
                type="select"
                value={this.state.requiredFacilityValue}
                onChange={this.handleRequiredFacilityChange}
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
            <AvGroup>
              <Label for="rooms">
                <span>Storage room</span>
              </Label>
              <AvField
                name="rooms"
                type="select"
                onChange={this.handleRoomChange}
                validate={{
                  required: {
                    value: true,
                    errorMessage: translate('entity.validation.required')
                  }
                }}
              >
                <option defaultChecked />
                {rooms
                  ? rooms.map((otherEntity, i) => (
                      <option value={otherEntity.id} key={i}>
                        {otherEntity.roomNumber + ' ' + otherEntity.type}
                      </option>
                    ))
                  : null}
              </AvField>
            </AvGroup>
          </ModalBody>
          <ModalFooter>
            <Button onClick={this.props.toggle}>Cancel</Button>
            <Button type="submit" color="primary">
              <Translate contentKey="storeHouseApp.tTN.addProduct">Add product</Translate>
            </Button>
          </ModalFooter>
        </AvForm>
      </Modal>
    );
  }
}
