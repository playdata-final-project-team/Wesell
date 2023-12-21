enum ResponseCode {
  // comment: HttpStatus 200 //
  OK = 'OK',

  // comment: HttpStatus 201 //
  USER_CREATED = 'UC',

  // comment: HttpStatus 400 //
  VALIDATION_FAIL = 'VF',
  SIGN_IN_FAIL = 'SIF',
  NOT_FOUND_USER = 'NFU',
  NOT_CORRECT_PASSWORD = 'NCP',

  // comment: HttpStatus 401 //
  INVALID_ACCESS_TOKEN = 'IAT',
  INVALID_REFRESH_TOKEN = 'IRT',
  EXPIRED_ACCESS_TOKEN = 'EAT',

  // comment: HttpStatus 403 //
  FORCED_DELETED_USER = 'FDU',

  // comment: HttpStatus 500 //
  TEMPORARY_SERVER_ERROR = 'TSE',
  USER_SERVICE_FEIGN_ERROR = 'UFE',
}

export default ResponseCode;
