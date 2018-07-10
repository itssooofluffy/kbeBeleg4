package de.htwBerlin.ai.kbe.api.filter;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {
	
    public static final String AUTHENTICATION_HEADER = "Authorization";
    
    @Inject
    Authenticator authenticator;
    
    @Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {

        String authToken = containerRequest.getHeaderString(AUTHENTICATION_HEADER);

        if (authToken == null || authToken.isEmpty()) {
        	Response.status(Response.Status.UNAUTHORIZED).entity("You cannot access this resource").build();
        	return;
        }
        if (!authenticator.authenticate(authToken)) 
            containerRequest.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("You cannot access this resource").build());
            return;
        } 
    
}

	





