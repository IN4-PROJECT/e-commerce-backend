package com.youtube.ecommerce.exception;

public enum ErrorCodes {

  PRODUCT_NOT_FOUND(1000),
  PRODUCT_NOT_VALID(1001),
  PRODUCT_ALREADY_IN_USE(1002),

  ORDER_NOT_FOUND(2000),
  ORDER_NOT_VALID(2001),
  ORDER_ALREADY_IN_USE(2002),

  UTILISATEUR_NOT_FOUND(12000),
  UTILISATEUR_NOT_VALID(12001),
  UTILISATEUR_ALREADY_EXISTS(12002),
  UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID(12003),

  BAD_CREDENTIALS(12003),

  // Liste des exception techniaues
  UPDATE_PHOTO_EXCEPTION(14000),
  UNKNOWN_CONTEXT(14001)
  ;

  private int code;

  ErrorCodes(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
