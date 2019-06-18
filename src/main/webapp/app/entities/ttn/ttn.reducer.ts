import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITTN, defaultValue } from 'app/shared/model/ttn.model';
import { IStorageRoom } from 'app/shared/model/storage-room.model';

export const ACTION_TYPES = {
  SEARCH_TTNS: 'tTN/SEARCH_TTNS',
  FETCH_TTN_LIST: 'tTN/FETCH_TTN_LIST',
  FETCH_TTN: 'tTN/FETCH_TTN',
  CREATE_TTN: 'tTN/CREATE_TTN',
  UPDATE_TTN: 'tTN/UPDATE_TTN',
  DELETE_TTN: 'tTN/DELETE_TTN',
  RESET: 'tTN/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITTN>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TTNState = Readonly<typeof initialState>;

// Reducer

export default (state: TTNState = initialState, action): TTNState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TTNS):
    case REQUEST(ACTION_TYPES.FETCH_TTN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TTN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TTN):
    case REQUEST(ACTION_TYPES.UPDATE_TTN):
    case REQUEST(ACTION_TYPES.DELETE_TTN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_TTNS):
    case FAILURE(ACTION_TYPES.FETCH_TTN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TTN):
    case FAILURE(ACTION_TYPES.CREATE_TTN):
    case FAILURE(ACTION_TYPES.UPDATE_TTN):
    case FAILURE(ACTION_TYPES.DELETE_TTN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TTNS):
    case SUCCESS(ACTION_TYPES.FETCH_TTN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TTN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TTN):
    case SUCCESS(ACTION_TYPES.UPDATE_TTN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TTN):
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

const apiUrl = 'api/ttns';
const apiSearchUrl = 'api/_search/ttns';

// Actions

export const getSearchEntities: ICrudSearchAction<ITTN> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_TTNS,
  payload: axios.get<ITTN>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ITTN> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TTN_LIST,
  payload: axios.get<ITTN>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITTN> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TTN,
    payload: axios.get<ITTN>(requestUrl)
  };
};

export const getRooms: ICrudGetAction<any> = id => {
  const requestUrl = `/api/storage-rooms/getByStorehouseId/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TTN,
    payload: axios.get<IStorageRoom>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITTN> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TTN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITTN> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TTN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITTN> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TTN,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
