package com.mahesh.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.mahesh.messenger.model.ErrorMessage;

/*@provider annotation can be used to handle any errors to give respose in a proper way
instead error handled by tomcat
*/
@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException exception) {
		ErrorMessage errMsg = new ErrorMessage(exception.getMessage(), 404, "http://error.com");
		return Response.status(Status.NOT_FOUND).entity(errMsg).build();
	}

}
