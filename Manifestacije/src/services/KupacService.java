package services;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Karta;
import beans.Kupac;
import dao.KartaDAO;
import dao.KupacDAO;

@Path("/kupac")
public class KupacService {
	
	@Context
	ServletContext ctx;
	
	public KupacService() {}

	@PostConstruct
	public void init() throws FileNotFoundException {
		if(ctx.getAttribute("kupci")==null) {
			KupacDAO dao = new KupacDAO();
			ctx.setAttribute("kupci", dao);
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String,Kupac> sviKupci() {
		KupacDAO dao = (KupacDAO) ctx.getAttribute("kupci");
		return dao.getKupci();
	}
	
	@POST
	@Path("/dodajKupca")
	@Produces(MediaType.APPLICATION_JSON)
	public Kupac dodajKupca(Kupac kupac) {
		KupacDAO dao = (KupacDAO) ctx.getAttribute("kupci");
		return dao.dodajKupca(kupac);
	}
	
	@GET
	@Path("/pretragaKupcaTip")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Kupac> pretragKupaca(@QueryParam("tip")String t) {
		KupacDAO dao = (KupacDAO) ctx.getAttribute("kupci");
		return null;
	}

}
