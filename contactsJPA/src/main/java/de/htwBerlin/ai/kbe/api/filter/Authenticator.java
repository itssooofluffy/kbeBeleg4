package de.htwBerlin.ai.kbe.api.filter;

import java.util.Collection;
import javax.inject.Inject;
import de.htwBerlin.ai.kbe.data.User;
import de.htwBerlin.ai.kbe.storage.UsersDAO;

public class Authenticator {
    	
        private UsersDAO UsersDao;

        @Inject
        public Authenticator(UsersDAO dao) {
            this.UsersDao = dao;
        }

    	public boolean authenticate(String authToken) {
    		Collection<User> users = UsersDao.findAllUsers();
    		for(User u : users) {
    			if (u.getToken().equals(authToken)) {
    				return true;
    			}
    		}
    		return false;
    	}
    }