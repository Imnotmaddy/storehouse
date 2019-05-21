import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IStorageRoom, defaultValue } from 'app/shared/model/storage-room.model';

export const ACTION_TYPES = {
  SEARCH_STORAGEROOMS: 'storageRoom/SEARCH_STORAGEROOMS',
  FETCH_STORAGEROOM_LIST: 'storageRoom/FETCH_STORAGEROOM_LIST',
  FETCH_STORAGEROOM: 'storageRoom/FETCH_STORAGEROOM',
  CREATE_STORAGEROOM: 'storageRoom/CREATE_STORAGEROOM',
  UPDATE_STORAGEROOM: 'storageRoom/UPDATE_STORAGEROOM',
  DELETE_STORAGEROOM: 'storageRoom/DELETE_STORAGEROOM',
  RESET: 'storageRoom/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IStorageRoom>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type StorageRoomState = Readonly<typeof initialState>;

// Reducer

export default (state: StorageRoomState = initialState, action): StorageRoomState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_STORAGEROOMS):
    case REQUEST(ACTION_TYPES.FETCH_STORAGEROOM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_STORAGEROOM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_STORAGEROOM):
    case REQUEST(ACTION_TYPES.UPDATE_STORAGEROOM):
    case REQUEST(ACTION_TYPES.DELETE_STORAGEROOM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_STORAGEROOMS):
    case FAILURE(ACTION_TYPES.FETCH_STORAGEROOM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_STORAGEROOM):
    case FAILURE(ACTION_TYPES.CREATE_STORAGEROOM):
    case FAILURE(ACTION_TYPES.UPDATE_STORAGEROOM):
    case FAILURE(ACTION_TYPES.DELETE_STORAGEROOM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_STORAGEROOMS):
    case SUCCESS(ACTION_TYPES.FETCH_STORAGEROOM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_STORAGEROOM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_STORAGEROOM):
    case SUCCESS(ACTION_TYPES.UPDATE_STORAGEROOM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_STORAGEROOM):
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

const apiUrl = 'api/storage-rooms';
const apiSearchUrl = 'api/_search/storage-rooms';

// Actions

export const getSearchEntities: ICrudSearchAction<IStorageRoom> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_STORAGEROOMS,
  payload: axios.get<IStorageRoom>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IStorageRoom> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_STORAGEROOM_LIST,
  payload: axios.get<IStorageRoom>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IStorageRoom> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_STORAGEROOM,
    payload: axios.get<IStorageRoom>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IStorageRoom> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_STORAGEROOM,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IStorageRoom> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_STORAGEROOM,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IStorageRoom> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_STORAGEROOM,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
