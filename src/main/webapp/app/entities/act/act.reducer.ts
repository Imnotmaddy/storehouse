import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAct, defaultValue } from 'app/shared/model/act.model';

export const ACTION_TYPES = {
  SEARCH_ACTS: 'act/SEARCH_ACTS',
  FETCH_ACT_LIST: 'act/FETCH_ACT_LIST',
  FETCH_ACT: 'act/FETCH_ACT',
  CREATE_ACT: 'act/CREATE_ACT',
  UPDATE_ACT: 'act/UPDATE_ACT',
  DELETE_ACT: 'act/DELETE_ACT',
  RESET: 'act/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAct>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ActState = Readonly<typeof initialState>;

// Reducer

export default (state: ActState = initialState, action): ActState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_ACTS):
    case REQUEST(ACTION_TYPES.FETCH_ACT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ACT):
    case REQUEST(ACTION_TYPES.UPDATE_ACT):
    case REQUEST(ACTION_TYPES.DELETE_ACT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_ACTS):
    case FAILURE(ACTION_TYPES.FETCH_ACT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACT):
    case FAILURE(ACTION_TYPES.CREATE_ACT):
    case FAILURE(ACTION_TYPES.UPDATE_ACT):
    case FAILURE(ACTION_TYPES.DELETE_ACT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_ACTS):
    case SUCCESS(ACTION_TYPES.FETCH_ACT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ACT):
    case SUCCESS(ACTION_TYPES.UPDATE_ACT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ACT):
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

const apiUrl = 'api/acts';
const apiSearchUrl = 'api/_search/acts';

// Actions

export const getSearchEntities: ICrudSearchAction<IAct> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_ACTS,
  payload: axios.get<IAct>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IAct> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ACT_LIST,
  payload: axios.get<IAct>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IAct> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACT,
    payload: axios.get<IAct>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAct> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ACT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAct> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ACT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAct> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ACT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
