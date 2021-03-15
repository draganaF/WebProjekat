package services;

import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


import beans.Lokacija;
import dao.LokacijaDAO;

@Path("/lokacija")
public class LokacijaService {
	
	@Context
	ServletContext ctx;
	
	public LokacijaService() {}
	
	@PostConstruct
	public void init() throws FileNotFoundException {
		if(ctx.getAttribute("lokacije")==null) {
			LokacijaDAO dao = new LokacijaDAO();
			ctx.setAttribute("lokacije", dao);
		}
	}
	
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<Integer,Lokacija> sveLokacije() {
		LokacijaDAO dao = (LokacijaDAO) ctx.getAttribute("lokacije");
		return dao.getLokacije();
	}
	
	
	

}
