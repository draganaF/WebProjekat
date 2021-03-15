package services;

import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Prodavac;
import dao.ManifestacijeDAO;
import dao.ProdavacDAO;

@Path("/prodavac")
public class ProdavacService {
	
	@Context
	ServletContext ctx;
	
	public ProdavacService() {}

	@PostConstruct
	public void init() throws FileNotFoundException {
		if(ctx.getAttribute("prodavci") == null) {
			ProdavacDAO dao = new ProdavacDAO();
			ctx.setAttribute("prodavci", dao);
		}
		if(ctx.getAttribute("manifestacije") == null) {
			ManifestacijeDAO dao = new ManifestacijeDAO();
			ctx.setAttribute("manifestacije", dao);
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String,Prodavac> sviProdavci() {
		ProdavacDAO dao = (ProdavacDAO) ctx.getAttribute("prodavci");
		return dao.getProdavci();
	}
	
	@POST
	@Path("/dodajProdavca")
	@Produces(MediaType.APPLICATION_JSON)
	public Prodavac dodajProdavca(Prodavac prodavac) {
		ProdavacDAO dao = (ProdavacDAO) ctx.getAttribute("prodavci");
		return dao.dodajProdavca(prodavac);
	}
	@GET
	@Path("/pretraga")
	@Produces(MediaType.APPLICATION_JSON)
	public Prodavac pretragaProdavac(@QueryParam("korisnickoIme")String korisnicko) {
		ProdavacDAO dao = (ProdavacDAO) ctx.getAttribute("prodavci");
		return dao.nadjiProdavca(korisnicko);
	}
	
	/*@GET
	@Path("/pretragaManifestacija")
	@Produces(MediaType.APPLICATION_JSON)
	public Prodavac pretragaManifestacijaProdavac(@QueryParam("manifestacija")String m) {
		ProdavacDAO dao = (ProdavacDAO) ctx.getAttribute("prodavci");
		ManifestacijeDAO d = (ManifestacijeDAO) ctx.getAttribute("manifestacije");
		ArrayList<Manifestacija> manifestacija = d.nadjiManifestacije(m);
		return dao.nadjiProdavcaNaOsnovuManifestacije(manifestacija);
	}*/
	
	

}
