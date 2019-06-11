import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITransporter, defaultValue } from 'app/shared/model/transporter.model';

export const ACTION_TYPES = {
  SEARCH_TRANSPORTERS: 'transporter/SEARCH_TRANSPORTERS',
  FETCH_TRANSPORTER_LIST: 'transporter/FETCH_TRANSPORTER_LIST',
  FETCH_TRANSPORTER: 'transporter/FETCH_TRANSPORTER',
  CREATE_TRANSPORTER: 'transporter/CREATE_TRANSPORTER',
  UPDATE_TRANSPORTER: 'transporter/UPDATE_TRANSPORTER',
  DELETE_TRANSPORTER: 'transporter/DELETE_TRANSPORTER',
  RESET: 'transporter/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITransporter>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TransporterState = Readonly<typeof initialState>;

// Reducer

export default (state: TransporterState = initialState, action): TransporterState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TRANSPORTERS):
    case REQUEST(ACTION_TYPES.FETCH_TRANSPORTER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TRANSPORTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TRANSPORTER):
    case REQUEST(ACTION_TYPES.UPDATE_TRANSPORTER):
    case REQUEST(ACTION_TYPES.DELETE_TRANSPORTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_TRANSPORTERS):
    case FAILURE(ACTION_TYPES.FETCH_TRANSPORTER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TRANSPORTER):
    case FAILURE(ACTION_TYPES.CREATE_TRANSPORTER):
    case FAILURE(ACTION_TYPES.UPDATE_TRANSPORTER):
    case FAILURE(ACTION_TYPES.DELETE_TRANSPORTER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TRANSPORTERS):
    case SUCCESS(ACTION_TYPES.FETCH_TRANSPORTER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TRANSPORTER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TRANSPORTER):
    case SUCCESS(ACTION_TYPES.UPDATE_TRANSPORTER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TRANSPORTER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/transporters';
const apiSearchUrl = 'api/_search/transporters';

// Actions

export const getSearchEntities: ICrudSearchAction<ITransporter> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_TRANSPORTERS,
  payload: axios.get<ITransporter>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ITransporter> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TRANSPORTER_LIST,
  payload: axios.get<ITransporter>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITransporter> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TRANSPORTER,
    payload: axios.get<ITransporter>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITransporter> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TRANSPORTER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITransporter> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TRANSPORTER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITransporter> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TRANSPORTER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
