package services;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Karta;
import dao.KartaDAO;


@Path("/karte")
public class KarteService {
	@Context
	ServletContext ctx;
	
	
	public KarteService() {
		
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("karte")==null) {
			KartaDAO dao = new KartaDAO();
			ArrayList<Karta> karte = dao.getKarte(); 
			ctx.setAttribute("karte", karte);
		}
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Karta> sveKarte(){
		return (ArrayList<Karta>) ctx.getAttribute("karte");
	}
	
	@POST
	@Path("/dodajKartu")
	@Produces(MediaType.APPLICATION_JSON)
	public Karta dodajKartu(Karta k) {
		KartaDAO dao = new KartaDAO();
		ArrayList<Karta> karteManifestacije = dao.karteManifestacije(k.getManifestacija().getId());
		for(Karta karta : karteManifestacije) {
			if(karta.getId()==k.getId()) {
				return null;
			}
		}
		if(karteManifestacije.size()>=k.getManifestacija().getBrojMesta()) {
			return null;
		}
		
		return k;
	}
	
	
}
