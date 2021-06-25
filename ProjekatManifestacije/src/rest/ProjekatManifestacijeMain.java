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

import beans.Administrator;
import beans.Korisnik;
import beans.Kupac;
import beans.Prodavac;
import dao.AdministratorDAO;
import dao.KomentarDAO;
import dao.KupacDAO;
import dao.LokacijaDAO;
import dao.ManifestacijeDAO;
import dao.ProdavacDAO;

public class ProjekatManifestacijeMain  {

	private static Gson g = new Gson();
	private static KupacDAO kupacDAO = new KupacDAO();
	private static AdministratorDAO administratorDAO = new AdministratorDAO();
	private static ProdavacDAO prodavacDAO = new ProdavacDAO();
	private static ManifestacijeDAO manifestacijeDAO = new ManifestacijeDAO();
	private static LokacijaDAO lokacijeDAO = new LokacijaDAO();
	private static KomentarDAO komentariDAO = new KomentarDAO();
	
	public static void main(String[] args) throws IOException {
		port(8080);
		
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		get("/manifestations", (req, res) -> {
			return g.toJson(manifestacijeDAO.getManifestacije().values());
		});
		
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
					if(prodavacDAO.nadjiProdavca(kIme, lozinka) != null) {
						korisnicko = kIme;
						response.add(korisnicko);
						response.add("prodavac");
					}else {
						response.add(korisnicko);
						
					}
					
				}
				
				
			}
				
			return g.toJson(response);
			
		});
		
		get("/searchManifestations", (req, res) -> {
			String filterAvaible = (req.queryParams("filterAvaiable")).strip();
			String filterType = (req.queryParams("filterType")).strip();
			String searchDateFrom = (req.queryParams("searchDateFrom")).strip();
			String searchDateTo = (req.queryParams("searchDateTo")).strip();
			String searchPriceFrom = (req.queryParams("searchPriceFrom")).strip();
			String searchPriceTo = (req.queryParams("searchPriceTo")).strip();
			String searchLocation = (req.queryParams("searchLocation")).strip();
			String searchName = (req.queryParams("searchName")).strip();
			return g.toJson(manifestacijeDAO.pretraga(filterAvaible, filterType, searchName, searchLocation, searchPriceFrom, searchPriceTo, searchDateFrom, searchDateTo));
		});

		get("/profile", (req, res)->{
			String kIme =  req.queryParams("korisnickoIme");
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Korisnik k = null;
			k = kupacDAO.nadjiKupcaProfil(kIme);
			if(k != null) {
				return gsonReg.toJson(k);
			}
			else
			{
				k = administratorDAO.nadjiAdministratoraProfil(kIme);
				if(k != null) {
					return gsonReg.toJson(k);
				}else {
					k = prodavacDAO.nadjiProdavcaProfil(kIme);
					if(k != null) {
						return gsonReg.toJson(k);
					}	
				}	
			}
			return gsonReg.toJson(k);
		});
		
		post("/updateInfo", (req, res)-> {
			String kIme =  req.queryParams("korisnickoIme");
			String reqBody = req.body();
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Korisnik k = null;
			k = kupacDAO.nadjiKupcaProfil(kIme);
			if(k != null) {
				Kupac kupac = gsonReg.fromJson(reqBody, Kupac.class);
				kupacDAO.izmeniKupca(kIme,kupac);
			}else {
				k = administratorDAO.nadjiAdministratoraProfil(kIme);
				if(k != null) {
					Administrator administrator = gsonReg.fromJson(reqBody, Administrator.class);
					administratorDAO.izmeniAdministratora(kIme,administrator);
				}else {
					k = prodavacDAO.nadjiProdavcaProfil(kIme);
					if(k != null) {
						Prodavac prodavac = gsonReg.fromJson(reqBody, Prodavac.class);
						prodavacDAO.izmeniProdavca(kIme,prodavac);
					}	
				}	
				
			}
			return true;
			
		});
		
		post("/changePassword", (req, res)-> {
			String kIme =  req.queryParams("korisnickoIme");
			String reqBody = req.body();
			//System.out.println(reqBody.split(":")[1].length());
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Korisnik k = null;
			k = kupacDAO.nadjiKupcaProfil(kIme);
			if(k != null) {
				Kupac kupac = gsonReg.fromJson(reqBody, Kupac.class);
				kupacDAO.izmeniLozinku(kIme,kupac.getLozinka());
			}else {
				k = administratorDAO.nadjiAdministratoraProfil(kIme);
				if(k != null) {
					System.out.println("DAAAAAAAA");
					Administrator administrator = gsonReg.fromJson(reqBody, Administrator.class);
					administratorDAO.izmeniLozinku(kIme,administrator.getLozinka());
				}else {
					k = prodavacDAO.nadjiProdavcaProfil(kIme);
					if(k != null) {
						Prodavac prodavac = gsonReg.fromJson(reqBody, Prodavac.class);
						prodavacDAO.izmeniLozinku(kIme,prodavac.getLozinka());
					}	
				}	
				
			}
			return true;
			
		});
		
		
		post("/registrationSeller", (req, res)-> {
			String reqBody = req.body();
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			
			Prodavac prodavac = gsonReg.fromJson(reqBody, Prodavac.class);
			prodavacDAO.dodajProdavca(prodavac);
			return true;
			
		});
		
		get("/manifestationLocations", (req, res) -> {
			return g.toJson(lokacijeDAO.getLokacije().values());
		});
		
		get("/comments", (req, res) -> {
			return g.toJson(komentariDAO.getKomentari().values());
		});
	}

}
