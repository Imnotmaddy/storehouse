import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISender, defaultValue } from 'app/shared/model/sender.model';

export const ACTION_TYPES = {
  SEARCH_SENDERS: 'sender/SEARCH_SENDERS',
  FETCH_SENDER_LIST: 'sender/FETCH_SENDER_LIST',
  FETCH_SENDER: 'sender/FETCH_SENDER',
  CREATE_SENDER: 'sender/CREATE_SENDER',
  UPDATE_SENDER: 'sender/UPDATE_SENDER',
  DELETE_SENDER: 'sender/DELETE_SENDER',
  RESET: 'sender/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISender>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type SenderState = Readonly<typeof initialState>;

// Reducer

export default (state: SenderState = initialState, action): SenderState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SENDERS):
    case REQUEST(ACTION_TYPES.FETCH_SENDER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SENDER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SENDER):
    case REQUEST(ACTION_TYPES.UPDATE_SENDER):
    case REQUEST(ACTION_TYPES.DELETE_SENDER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_SENDERS):
    case FAILURE(ACTION_TYPES.FETCH_SENDER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SENDER):
    case FAILURE(ACTION_TYPES.CREATE_SENDER):
    case FAILURE(ACTION_TYPES.UPDATE_SENDER):
    case FAILURE(ACTION_TYPES.DELETE_SENDER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SENDERS):
    case SUCCESS(ACTION_TYPES.FETCH_SENDER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SENDER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SENDER):
    case SUCCESS(ACTION_TYPES.UPDATE_SENDER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SENDER):
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

const apiUrl = 'api/senders';
const apiSearchUrl = 'api/_search/senders';

// Actions

export const getSearchEntities: ICrudSearchAction<ISender> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SENDERS,
  payload: axios.get<ISender>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ISender> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SENDER_LIST,
  payload: axios.get<ISender>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ISender> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SENDER,
    payload: axios.get<ISender>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISender> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SENDER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISender> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SENDER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISender> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SENDER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
