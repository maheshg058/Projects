package com.mahesh.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.mahesh.messenger.model.ErrorMessage;

/*
This is kind of generic exception handler to cath all types of errors,
It is good to have all custom exection
*/
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		ErrorMessage errMsg = new ErrorMessage(exception.getMessage(), 404, "http://error.com");
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errMsg).build();
	}

}
