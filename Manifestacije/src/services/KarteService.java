package services;

import java.io.IOException;
import java.util.ArrayList;

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
import beans.TipKarte;
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
			 
			ctx.setAttribute("karteDAO", dao);
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Karta> sveKarte(){
		return ((KartaDAO) ctx.getAttribute("karteDAO")).getKarte();
	}
	
	@POST
	@Path("/dodajKartu")
	@Produces(MediaType.APPLICATION_JSON)
	public Karta dodajKartu(Karta k) {
		KartaDAO dao = (KartaDAO) ctx.getAttribute("karteDAO");
		ArrayList<Karta> karteManifestacije = dao.karteManifestacije(k.getManifestacija().getId());
		for(Karta karta : karteManifestacije) {
			if(karta.getId()==k.getId()) {
				return null;
			}
		}
		if(karteManifestacije.size()>=k.getManifestacija().getBrojMesta()) {
			return null;
		}
		dao.getKarte().add(k);
		
		try {
			dao.upisiKarte();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return k;
	}
	
	@GET
	@Path("/nadjiPoManifestaciji")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Karta> nadjiPoManifestaciji(@QueryParam("id") int id){
		KartaDAO dao = (KartaDAO) ctx.getAttribute("karteDAO");
		return dao.karteManifestacije(id);
		
	}
	@GET
	@Path("/nadjiPoCeni")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Karta> nadjiPoCeni(@QueryParam("odCena") int odCena, @QueryParam("doCena") int doCena){
		KartaDAO dao = (KartaDAO) ctx.getAttribute("karteDAO");
		return dao.nadjiPoCeni(odCena, doCena);
	}
	
	@GET
	@Path("/nadjiPoStatusu")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Karta> nadjiPoStatusu(@QueryParam("status") boolean status){
		KartaDAO dao = (KartaDAO) ctx.getAttribute("karteDAO");
		return dao.nadjiPoStatusu(status);
	}
	
	@GET
	@Path("/nadjiPoTipu")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Karta> nadjiPoTipu(@QueryParam("tip") TipKarte tip){
		KartaDAO dao = (KartaDAO) ctx.getAttribute("karteDAO");
		return dao.nadjiPoTipu(tip);
	}
	
}
