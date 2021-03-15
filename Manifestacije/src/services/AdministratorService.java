package services;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Administrator;
import dao.AdministratorDAO;


@Path("/administrator")
public class AdministratorService {
	
	@Context
	ServletContext ctx;
	
	public AdministratorService() {}
	
	@PostConstruct
	public void init() throws FileNotFoundException {
		if(ctx.getAttribute("administratori")==null) {
			AdministratorDAO dao = new AdministratorDAO();
			ctx.setAttribute("administratori", dao);
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Administrator> sviAdministratori() {
		AdministratorDAO dao = (AdministratorDAO) ctx.getAttribute("administratori");
		return dao.getAdministratori();
	}
}
