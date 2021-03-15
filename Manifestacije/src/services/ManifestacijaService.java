package services;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Lokacija;
import beans.Manifestacija;
import dao.ManifestacijeDAO;

@Path("/manifestacije")
public class ManifestacijaService {

	@Context
	ServletContext ctx;
	
	
	public ManifestacijaService() {
		
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("manifestacijeDAO")==null) {
			ManifestacijeDAO dao = new ManifestacijeDAO();
			
			ctx.setAttribute("manifestacijeDAO",dao);
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<Integer,Manifestacija> getManifestacije(){
		return  ((ManifestacijeDAO)ctx.getAttribute("manifestacijeDAO")).getManifestacije();
	}
	
	@POST
	@Path("/dodaj")
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestacija addManifestacija(Manifestacija manifestacija) {
		ManifestacijeDAO dao = (ManifestacijeDAO) ctx.getAttribute("manifestacijeDAO");
		HashMap<Integer,Manifestacija> manifestacije = dao.getManifestacije();
		for (Map.Entry<Integer, Manifestacija> entry : manifestacije.entrySet()) {
			if(entry.getValue().getId() == manifestacija.getId() ||(entry.getValue().getDatum() == manifestacija.getDatum() && entry.getValue().getLokacija() == manifestacija.getLokacija())) {
				return null;
			}
		}
			
		dao.getManifestacije().put(manifestacija.getId(),manifestacija);
		try {
			dao.upisiManifestacije();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		manifestacije.put(manifestacija.getId(),manifestacija);
		ctx.setAttribute("manifestacijeDAO", dao);
		return manifestacija;
	}
	
	@GET
	@Path("/nadjiNaziv")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> nadjiPoNazivu(@QueryParam("naziv") String naziv){
		return ((ManifestacijeDAO) ctx.getAttribute("manifestacijeDAO")).nadjiManifestacije(naziv);
	}
	
	@GET
	@Path("/nadjiDatum")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> nadjiPoDatumu(@QueryParam("od") LocalDateTime odDatum, @QueryParam("do") LocalDateTime doDatum){
		return ((ManifestacijeDAO) ctx.getAttribute("manifestacijeDAO")).nadjiPoDatumu(odDatum, doDatum);
	}
	
	@GET
	@Path("/nadjiCena")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> nadjiPoCeni(@QueryParam("cenaOd") float cenaOd, @QueryParam("cenaDo") float cenaDo){
		return ((ManifestacijeDAO) ctx.getAttribute("manifestacijeDAO")).nadjiPoCeni(cenaOd, cenaDo);
	}
	
	@GET
	@Path("/nadjiLokacija")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> nadjiPoLokaciji(@QueryParam("lokacija") Lokacija lokacija){
		return ((ManifestacijeDAO) ctx.getAttribute("manifestacijeDAO")).nadjiPoLokaciji(lokacija);
	}
}
