package services;
import java.time.LocalDateTime;
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
		if(ctx.getAttribute("manifestacije")==null) {
			ManifestacijeDAO dao = new ManifestacijeDAO();
			ArrayList<Manifestacija> manifestacije = dao.getManifestacije(); 
			ctx.setAttribute("manifestacije",manifestacije);
		}
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> getManifestacije(){
		
		return (ArrayList<Manifestacija>) ctx.getAttribute("manifestacije");
	}
	
	@POST
	@Path("/dodaj")
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestacija addManifestacija(Manifestacija manifestacija) {
		@SuppressWarnings("unchecked")
		ArrayList<Manifestacija> manifestacije = (ArrayList<Manifestacija>) ctx.getAttribute("manifestacije");
		for(Manifestacija m : manifestacije) {
			if(m.getId() == manifestacija.getId() || (m.getDatum() == manifestacija.getDatum() && m.getLokacija() == manifestacija.getLokacija())) {
				return null;
			}
		}
		
		manifestacije.add(manifestacija);
		ctx.setAttribute("manifestacije", manifestacije);
		return manifestacija;
	}
	
	@GET
	@Path("/nadjiNaziv")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> nadjiPoNazivu(@QueryParam("naziv") String naziv){
		@SuppressWarnings("unchecked")
		ArrayList<Manifestacija> manifestacije = (ArrayList<Manifestacija>) ctx.getAttribute("manifestacije");
		for(Manifestacija m : manifestacije) {
			if(m.getNaziv().equalsIgnoreCase(naziv)) {
				manifestacije.add(m);
			}
		}
		return manifestacije;
	}
	
	@GET
	@Path("/nadjiDatum")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> nadjiPoDatumu(@QueryParam("od") LocalDateTime odDatum, @QueryParam("do") LocalDateTime doDatum){
		@SuppressWarnings("unchecked")
		ArrayList<Manifestacija> manifestacije = (ArrayList<Manifestacija>) ctx.getAttribute("manifestacije");
		for(Manifestacija m : manifestacije) {
			if(m.getDatum().isAfter(odDatum) && m.getDatum().isBefore(doDatum)) {
				manifestacije.add(m);
			}
		}
		return manifestacije;
	}
	
	@GET
	@Path("/nadjiCena")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> nadjiPoCeni(@QueryParam("cenaOd") float cenaOd, @QueryParam("cenaDo") float cenaDo){
		@SuppressWarnings("unchecked")
		ArrayList<Manifestacija> manifestacije = (ArrayList<Manifestacija>) ctx.getAttribute("manifestacije");
		for(Manifestacija m : manifestacije) {
			if(m.getCenaKarte()>= cenaOd && m.getCenaKarte()<=cenaDo) {
				manifestacije.add(m);
			}
		}
		return manifestacije;
	}
	
	@GET
	@Path("/nadjiLokacija")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> nadjiPoLokaciji(@QueryParam("lokacija") String lokacija){
		@SuppressWarnings("unchecked")
		ArrayList<Manifestacija> manifestacije = (ArrayList<Manifestacija>) ctx.getAttribute("manifestacije");
		for(Manifestacija m : manifestacije) {
			if(m.getLokacija().getAdresa().getDrzava().contains(lokacija) || m.getLokacija().getAdresa().getMesto().contains(lokacija)) {
				manifestacije.add(m);
			}
		}
		return manifestacije;
	}
}
