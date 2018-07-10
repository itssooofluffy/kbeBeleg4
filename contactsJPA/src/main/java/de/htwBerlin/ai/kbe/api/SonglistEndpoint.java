package de.htwBerlin.ai.kbe.api;

import java.util.Collection;

import javax.inject.Inject;
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
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.htwBerlin.ai.kbe.data.Songlist;
import de.htwBerlin.ai.kbe.data.User;
import de.htwBerlin.ai.kbe.storage.SonglistDAO;
import de.htwBerlin.ai.kbe.storage.UsersDAO;

@Path("/UserId")
public class SonglistEndpoint {

    private UsersDAO UsersDao;
    private SonglistDAO SonglistDao;

    @Inject
    public SonglistEndpoint(UsersDAO userDao, SonglistDAO songlistDao) {
        this.UsersDao = userDao;
        this.SonglistDao = songlistDao;
    }
    
    @GET
    @Path("/{id}/songlists")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Collection<Songlist> getSonglists(@PathParam("id") String id) {
    	Collection<Songlist> songlist = null;
    	User user = UsersDao.findUserById(id);
    	if (id != null) {
    		songlist = SonglistDao.findPublicSonglistsByUserId(id);
    	}
    	return songlist;
    }

    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces(MediaType.TEXT_PLAIN)
    public Response createSonglist(Songlist songlist) {
        int newId = SonglistDao.saveSonglist(songlist);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Integer.toString(newId));
        return Response.created(uriBuilder.build()).build();
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}")
    public Response updateSonglist(@PathParam("id") Integer id, Songlist songlist) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("PUT not implemented").build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        SonglistDao.deleteSonglist(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}