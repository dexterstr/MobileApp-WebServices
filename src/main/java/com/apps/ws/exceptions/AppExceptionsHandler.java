package com.apps.ws.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.apps.ws.model.response.ErrorMessage;

@ControllerAdvice
public class AppExceptionsHandler {

	
	//method to handle exception
	//it needs to return a response entity
	//2 args - exception that needs to be handles and webrequest data type- it gives access to http request/cookies /params and so on
	//string message will be displayed and http status also
	@ExceptionHandler(value= {UserServiceException.class})
	public ResponseEntity<Object> handleUserServiceException(UserServiceException ex,WebRequest req){
		
		//created a custom error object and displayed our style of error message
		ErrorMessage errorMessage = new ErrorMessage(new Date(),ex.getMessage());
		
		return new ResponseEntity<>(errorMessage,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR );
	}
	
	//tohandle all other exceptions
	@ExceptionHandler(value= {Exception.class})
	public ResponseEntity<Object> handleOtherExceptions(Exception ex,WebRequest req){
		
		//created a custom error object and displayed our style of error message
		ErrorMessage errorMessage = new ErrorMessage(new Date(),ex.getMessage());
		
		return new ResponseEntity<>(errorMessage,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR );
	}
	
}
