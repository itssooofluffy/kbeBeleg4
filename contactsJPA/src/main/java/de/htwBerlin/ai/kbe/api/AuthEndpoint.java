package de.htwBerlin.ai.kbe.api;

import java.security.SecureRandom;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.htwBerlin.ai.kbe.storage.UsersDAO;

@Path("/auth")
public class AuthEndpoint {
	
	UsersDAO UsersDao;
	
    @Inject
    public AuthEndpoint(UsersDAO dao) {
        this.UsersDao = dao;
    }

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response authUser(@QueryParam("userId") String userId, @Context UriInfo uriInfo) {
		try {
			authenticate(userId);
			String token = issueToken(userId);
			return Response.ok(token).build();
		} catch (Exception e) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
	}

	private String issueToken(String userId) {	
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[64];
		random.nextBytes(bytes);
		String token = bytes.toString();	
		UsersDao.findUserById(userId).setToken(token);			
		return token;
	}

	public void authenticate(String userId) throws Exception {
		UsersDao.findUserById(userId);				
	}
}