package com.apps.ws.model.response;

public enum ErrorMessages {

	MISSING_REQUIRED_FIELD("missing required field. please check documentation"),
	RECORD_ALREADY_EXISTS("record already exists"),
	INTERNAL_SERVER_ERROR("Internal Server error"),
	NO_RECORD_FOUND("record with provided id is not found"),
	AUTHENTICATION_FAILED("Authentication failed"),
	COULD_NOT_UPDATE_RECORD("could not update record"),
	COULD_NOT_DELETE_RECORD("could not delete record"),
	EMAIL_ADDRESS_NOT_VERIFIED("email address could not be verified");
	
private String errorMessage;

ErrorMessages(String errorMessage){
	this.errorMessage=errorMessage;
}

public String getErrorMessage() {
	return errorMessage;
}

public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
}



}
