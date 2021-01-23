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

import beans.Komentar;
import dao.KomentarDAO;

@Path("/komentari")
public class KomentarService {
	
	@Context
	ServletContext ctx;
	
	public KomentarService() {
		
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("komentariDAO")==null) {
			KomentarDAO dao = new KomentarDAO();
			ctx.setAttribute("komentariDAO", dao);
		}
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Komentar> sviKomentari(){
		return ((KomentarDAO) ctx.getAttribute("komentariDAO")).getKomentari();
	}
	
	@POST
	@Path("/dodajKomentar")
	@Produces(MediaType.APPLICATION_JSON)
	public Komentar dodajKomentar(Komentar k) {
		KomentarDAO dao = (KomentarDAO) ctx.getAttribute("komentariDAO");
		ArrayList<Komentar> komentari = dao.getKomentari();
		for(Komentar komentar : komentari) {
			if(komentar.getId() == k.getId()) {
				return null;
			}
		}
		
		
		dao.getKomentari().add(k);
		
		try {
			dao.upisiKomentare();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return k;
	}
	
	@GET
	@Path("/nadjiPoKupcu")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Komentar> nadjiPoKupcu(@QueryParam("korisnickoIme") String korisnickoIme){
		KomentarDAO dao = (KomentarDAO) ctx.getAttribute("komentariDAO");
		return dao.komentariKupca(korisnickoIme);
	}
	
	@GET
	@Path("/nadjiPoManifestaciji")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Komentar> nadjiPoManifestaciji(@QueryParam("id") int id){
		KomentarDAO dao = (KomentarDAO) ctx.getAttribute("komentariDAO");
		return dao.komentariManifestacije(id);
	}
	
}
