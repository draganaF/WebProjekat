package rest;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Kupac;

import dao.AdministratorDAO;
import dao.KupacDAO;
import dao.ProdavacDAO;

public class ProjekatManifestacijeMain  {

	private static Gson g = new Gson();
	private static KupacDAO kupacDAO = new KupacDAO();
	private static AdministratorDAO administratorDAO = new AdministratorDAO();
	private static ProdavacDAO prodavacDAO = new ProdavacDAO();
	
	public static void main(String[] args) throws IOException {
		port(8080);
		
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		post("/registration", (req, res)-> {
			String reqBody = req.body();
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			
			Kupac kupac = gsonReg.fromJson(reqBody, Kupac.class);
			kupacDAO.dodajKupca(kupac);
			return true;
			
		});
		
		
		post("/login", (req, res)-> {
			String kIme  = req.queryParams("korisnickoIme");
			String lozinka = req.queryParams("lozinka");
			
			String korisnicko = " ";
			ArrayList<String> response = new ArrayList<String>();
			if(kupacDAO.nadjiKupca(kIme, lozinka) != null) {
				korisnicko = kIme;
				response.add(korisnicko);
				response.add("kupac");
			}
			else
			{
				if(administratorDAO.nadjiAdministratora(kIme, lozinka) != null) {
					korisnicko = kIme;
					response.add(korisnicko);
					response.add("admin");
				}else {
					response.add(korisnicko);
					
				}
				
			}
				
			return g.toJson(response);
			
		});
		
		

	}

}
