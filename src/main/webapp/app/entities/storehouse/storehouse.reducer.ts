import axios from 'axios';
import { ICrudDeleteAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudSearchAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, IStorehouse } from 'app/shared/model/storehouse.model';

export const ACTION_TYPES = {
  SEARCH_STOREHOUSES: 'storehouse/SEARCH_STOREHOUSES',
  FETCH_STOREHOUSE_LIST: 'storehouse/FETCH_STOREHOUSE_LIST',
  FETCH_COMPANY_STOREHOUSE_LIST: 'storehouse/FETCH_COMPANY_STOREHOUSE_LIST',
  FETCH_STOREHOUSE: 'storehouse/FETCH_STOREHOUSE',
  CREATE_STOREHOUSE: 'storehouse/CREATE_STOREHOUSE',
  UPDATE_STOREHOUSE: 'storehouse/UPDATE_STOREHOUSE',
  DELETE_STOREHOUSE: 'storehouse/DELETE_STOREHOUSE',
  RESET: 'storehouse/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IStorehouse>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type StorehouseState = Readonly<typeof initialState>;

// Reducer

export default (state: StorehouseState = initialState, action): StorehouseState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_STOREHOUSES):
    case REQUEST(ACTION_TYPES.FETCH_COMPANY_STOREHOUSE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_STOREHOUSE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_STOREHOUSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_STOREHOUSE):
    case REQUEST(ACTION_TYPES.UPDATE_STOREHOUSE):
    case REQUEST(ACTION_TYPES.DELETE_STOREHOUSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_STOREHOUSES):
    case FAILURE(ACTION_TYPES.FETCH_STOREHOUSE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMPANY_STOREHOUSE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_STOREHOUSE):
    case FAILURE(ACTION_TYPES.CREATE_STOREHOUSE):
    case FAILURE(ACTION_TYPES.UPDATE_STOREHOUSE):
    case FAILURE(ACTION_TYPES.DELETE_STOREHOUSE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_STOREHOUSES):
    case SUCCESS(ACTION_TYPES.FETCH_STOREHOUSE_LIST):
    case SUCCESS(ACTION_TYPES.FETCH_COMPANY_STOREHOUSE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_STOREHOUSE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_STOREHOUSE):
    case SUCCESS(ACTION_TYPES.UPDATE_STOREHOUSE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_STOREHOUSE):
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

const apiUrl = 'api/storehouses';
const apiSearchUrl = 'api/_search/storehouses';

// Actions

export const getSearchEntities: ICrudSearchAction<IStorehouse> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_STOREHOUSES,
  payload: axios.get<IStorehouse>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IStorehouse> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_STOREHOUSE_LIST,
  payload: axios.get<IStorehouse>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getCompanyStorehouses = (company: string) => ({
  type: ACTION_TYPES.FETCH_COMPANY_STOREHOUSE_LIST,
  payload: axios.get<IStorehouse>(`${apiUrl}/company?companyName=${company}`)
});

export const getEntity: ICrudGetAction<IStorehouse> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_STOREHOUSE,
    payload: axios.get<IStorehouse>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IStorehouse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_STOREHOUSE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getCompanyStorehouses(entity.companyName));
  return result;
};

export const updateEntity: ICrudPutAction<IStorehouse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_STOREHOUSE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getCompanyStorehouses(entity.companyName));
  return result;
};

export const deleteEntity = (id, company: string) => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_STOREHOUSE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getCompanyStorehouses(company));
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
