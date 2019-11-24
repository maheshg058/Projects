package com.mahesh.messenger.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class CommentResource {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Hiii";
	}

	/*
	 * messageId is automatically deligated from previous resource
	 */
	@GET
	@Path("/{commentId}")
	public String getComment(@PathParam("commentId") long commentId, @PathParam("{messageId}") long msgId) {
		return "Message Id:" + msgId + "  Comment Id:" + commentId;
	}

	/*
	 * Implement comment service and all similar to message and profile
	 */}
