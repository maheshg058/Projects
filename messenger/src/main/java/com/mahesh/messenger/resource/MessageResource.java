package com.mahesh.messenger.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.mahesh.messenger.exception.DataNotFoundException;
import com.mahesh.messenger.model.Message;
import com.mahesh.messenger.resource.filter.MessageFilterBean;
import com.mahesh.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
//@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
// To support client request for both JSON and xml
//In header client can pass "Accept" parameter
public class MessageResource {

	private MessageService messageService = new MessageService();

	/*
	 * We can use below approach if only few parametrs are there. If there are
	 * multiple parametrs it is better to use @BeanParam
	 * 
	 * @QueryParam("year") int year,
	 * 
	 * @QueryParam("start") int start,
	 * 
	 * @QueryParam("size") int size
	 * 
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON) // Overrides class level annotation so that different impl can have for diff
											// client Media types. Same concept for consumes too
	public List<Message> getJsonMessages(@BeanParam MessageFilterBean filterBean) {
		if (filterBean.getYear() > 0) {
			return messageService.getMessagesForYear(filterBean.getYear());
		}
		if (filterBean.getStart() > 0 && filterBean.getSize() > 0) {
			return messageService.getMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXmlMessages(@BeanParam MessageFilterBean filterBean) {
		if (filterBean.getYear() > 0) {
			return messageService.getMessagesForYear(filterBean.getYear());
		}
		if (filterBean.getStart() > 0 && filterBean.getSize() > 0) {
			return messageService.getMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}

	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") int messageId, @Context UriInfo uriInfo) {
		Message message = messageService.getMessage(messageId);
		if (message == null) {
			throw new DataNotFoundException("There is no message found for message id:" + messageId);
		}
		// HATEOAS - Add addition infos
		String selfLink = getUriInfoForSelf(uriInfo, message);
		String profileLink = getUriInfoForProfile(uriInfo, message);
		String commentLink = getUriInfoForComment(uriInfo, message);
		message.addLink(selfLink, "self");
		message.addLink(profileLink, "profile");
		message.addLink(commentLink, "comment");
		return message;
	}

	private String getUriInfoForComment(UriInfo uriInfo, Message message) {
		return uriInfo.getBaseUriBuilder().path(MessageResource.class)
				// Build paths using resource and corresponding method for sub resource
				.path(MessageResource.class, "getCommentResource").path(CommentResource.class)
				// Resolve parameter values
				.resolveTemplate("messageId", message.getId()).build().toString();
	}

	private String getUriInfoForProfile(UriInfo uriInfo, Message message) {
		return uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(message.getAuthor()).build().toString();
	}

	private String getUriInfoForSelf(UriInfo uriInfo, Message message) {
		return uriInfo.getBaseUriBuilder().path(MessageResource.class).path(String.valueOf(message.getId())).build()
				.toString();
	}

	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {
		// messageService.addMessage(message);
		// Use resource builder to send proper msg status. We can build respose with all
		// required values
		Message createdMsg = messageService.addMessage(message);
		// create new link of created msg
		URI newMsg = uriInfo.getAbsolutePathBuilder().path(String.valueOf(createdMsg.getId())).build();
		return Response.created(newMsg)
				// .status(Status.CREATED) created method add this status
				.entity(createdMsg).build();
	}

	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}

	@DELETE
	@Path("/{messageId}")
	public void removeMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}

	/*
	 * handling comment in MessageResource is not good way . use sub resource
	 * Example for sub resource Do not give GET/POST/PUT .. so whenever request
	 * comes for comment, It will deligate resource to comment resource. Remainder
	 * of the path will be sent to that resource
	 */
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}

}
