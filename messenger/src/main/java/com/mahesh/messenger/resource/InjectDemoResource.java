package com.mahesh.messenger.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Hiiii";
	}

	/*
	 * Only the difference between QUery param and Matrix param is values are
	 * resource is separated by "&" in Query param and ";" in matrix param
	 */
	@GET
	@Path("/annotation")
	@Produces(MediaType.TEXT_PLAIN)
	public String getParameterUsingAnnotation(@MatrixParam("value") String value,
			@HeaderParam("HeaderParam1") String headerParam, @CookieParam("name") String name) {
		return "Matrix param :" + value + " Header param headerParam:" + headerParam + "  Coockie:" + name;
	}

	/*
	 * When parameters become too many, we can use context
	 */
	@GET
	@Path("context")
	@Produces(MediaType.TEXT_PLAIN)
	public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders headerInfo) {
		String path = uriInfo.getAbsolutePath().toString();
		String cookie = headerInfo.getCookies().toString();
		return "Path=" + path + "  Cooke name:" + cookie;
	}
}
