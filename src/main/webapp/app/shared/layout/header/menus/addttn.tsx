import React from 'react';
import { Form, FormGroup, Label, Input } from 'reactstrap';

export const TtnForm = props => (
  <div>
    <Form>
      <FormGroup>
        <Label for="serial">Serial number</Label>
        <Input id="serial" type="text" required />
      </FormGroup>
    </Form>
  </div>
);
