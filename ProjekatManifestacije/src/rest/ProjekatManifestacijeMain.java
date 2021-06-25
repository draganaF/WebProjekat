package rest;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Administrator;
import beans.Korisnik;
import beans.Kupac;
import beans.Prodavac;
import beans.Karta;
import dao.AdministratorDAO;
import dao.KartaDAO;
import dao.KupacDAO;
import dao.ProdavacDAO;

public class ProjekatManifestacijeMain  {

	private static Gson g = new Gson();
	private static KupacDAO kupacDAO = new KupacDAO();
	private static AdministratorDAO administratorDAO = new AdministratorDAO();
	private static ProdavacDAO prodavacDAO = new ProdavacDAO();
	private static KartaDAO kartaDAO = new KartaDAO();
	
	public static void main(String[] args) throws IOException {
		port(8080);
		
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		post("/registration", (req, res)-> {
			String reqBody = req.body();
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
			
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
				if(!kupacDAO.nadjiKupca(kIme,lozinka).isBlokiran()) {
					korisnicko = kIme;
					response.add(korisnicko);
					response.add("kupac");
				}else {
					korisnicko = "blokiran";
					response.add(korisnicko);
				}
			}
			else
			{
				if(administratorDAO.nadjiAdministratora(kIme, lozinka) != null) {
					korisnicko = kIme;
					response.add(korisnicko);
					response.add("admin");
				}else {
					if(prodavacDAO.nadjiProdavca(kIme, lozinka) != null) {
						if(!prodavacDAO.nadjiProdavca(kIme, lozinka).isBlokiran()) {
							korisnicko = kIme;
							response.add(korisnicko);
							response.add("prodavac");
						}else {
							korisnicko = "blokiran";
							response.add(korisnicko);
						}
					}else {
						response.add(korisnicko);
						
					}
					
				}
				
				
			}
				
			return g.toJson(response);
			
		});
		
		get("/profile", (req, res)->{
			String kIme =  req.queryParams("korisnickoIme");
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
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
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
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
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
			Korisnik k = null;
			k = kupacDAO.nadjiKupcaProfil(kIme);
			if(k != null) {
				Kupac kupac = gsonReg.fromJson(reqBody, Kupac.class);
				kupacDAO.izmeniLozinku(kIme,kupac.getLozinka());
			}else {
				k = administratorDAO.nadjiAdministratoraProfil(kIme);
				if(k != null) {
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
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
			Prodavac prodavac = gsonReg.fromJson(reqBody, Prodavac.class);
			prodavacDAO.dodajProdavca(prodavac);
			return true;
			
		});
		
		get("/admins", (req, res)->{
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
			
			ArrayList<Administrator> admini = new ArrayList<Administrator>();
			for (Map.Entry<String, Administrator> entry : administratorDAO.getAdministratori().entrySet()) {
				if(!entry.getValue().isObrisan())
					admini.add( entry.getValue());
		        
		    }	
			return gsonReg.toJson(admini);
			
		});
		get("/sellers", (req, res)->{
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
			
			ArrayList<Prodavac> prodavci = new ArrayList<Prodavac>();
			for (Map.Entry<String, Prodavac> entry : prodavacDAO.getProdavci().entrySet()) {
				if(!entry.getValue().isObrisan())
					prodavci.add( entry.getValue());
		        
		    }	
			return gsonReg.toJson(prodavci);
			
		});
		get("/users", (req, res)->{
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
			
			ArrayList<Kupac> kupci = new ArrayList<Kupac>();
			for (Map.Entry<String, Kupac> entry : kupacDAO.getKupci().entrySet()) {
				if(!entry.getValue().isObrisan())
		        	kupci.add( entry.getValue());
		        
		    }	
			return gsonReg.toJson(kupci);
			
		});
		
		post("/delete", (req, res)-> {
			String kIme =  req.queryParams("korisnickoIme");
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
			Korisnik k = null;
			k = kupacDAO.nadjiKupcaProfil(kIme);
			if(k != null) {
				kupacDAO.obrisiKupca(kIme);
			}else {
					k = prodavacDAO.nadjiProdavcaProfil(kIme);
					if(k != null) {
						prodavacDAO.obrisiProdavca(kIme);
					}	
			}
			return true;
			
		});
		
		get("/searchK", (req, res)->{
			String p =  req.queryParams("pretraga");
			String u =  req.queryParams("uloga");
			String t =  req.queryParams("tip");
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
			
			ArrayList<Kupac> kupci = new ArrayList<Kupac>();
			if(u.equals("kupac") || u.equals("")) {
				for (Map.Entry<String, Kupac> entry : kupacDAO.getKupci().entrySet()) {
					
					if((entry.getValue().getIme().contains(p) || entry.getValue().getPrezime().contains(p) || entry.getValue().getKorisnickoIme().contains(p) || entry.getValue().getTipKupca().getImeTipa().toString().contains(t)) && !entry.getValue().isObrisan())
						
						kupci.add( entry.getValue());
		    	}	
			}
			return gsonReg.toJson(kupci);
			
		});
		get("/searchA", (req, res)->{
			String p =  req.queryParams("pretraga");
			String u = req.queryParams("uloga");
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
			
			ArrayList<Administrator> admini = new ArrayList<Administrator>();
			for (Map.Entry<String, Administrator> entry : administratorDAO.getAdministratori().entrySet()) {
				if(u.equals("administrator")|| u.equals("")) {
				if((entry.getValue().getIme().contains(p) || entry.getValue().getPrezime().contains(p) || entry.getValue().getKorisnickoIme().contains(p)) && !entry.getValue().isObrisan() )
		        	admini.add( entry.getValue());
				} 
		    }	
			return gsonReg.toJson(admini);
			
		});
		get("/searchP", (req, res)->{
			String p =  req.queryParams("pretraga");
			String u =  req.queryParams("uloga");
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
			
			ArrayList<Prodavac> prodavci = new ArrayList<Prodavac>();
			for (Map.Entry<String, Prodavac> entry : prodavacDAO.getProdavci().entrySet()) {
				if(u.equals("prodavac")|| u.equals("")) {
					if((entry.getValue().getIme().contains(p) || entry.getValue().getPrezime().contains(p) || entry.getValue().getKorisnickoIme().contains(p)) && !entry.getValue().isObrisan())
						prodavci.add( entry.getValue());
				}
		    }	
			return gsonReg.toJson(prodavci);
			
		});
		
		get("/suspiciousUsers", (req, res)->{
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
			ArrayList<Kupac> kupci = new ArrayList<Kupac>();
			for (Map.Entry<String, Kupac> entry : kupacDAO.getKupci().entrySet()) {
				if(!entry.getValue().isObrisan()) {
					ArrayList<Karta> karteKupca = kartaDAO.karteKupcaSumnjivi(entry.getValue().getKorisnickoIme());
					if(karteKupca != null) {
						entry.getValue().setSumnjiv(true);
						kupci.add( entry.getValue());
					}
				}
		    }
			kupacDAO.podesiSumnjive(kupci);
			return gsonReg.toJson(kupci);
			
		});
		
		get("/searchSuspiciousK", (req, res)->{
			String p =  req.queryParams("pretraga");
			
			String t =  req.queryParams("tip");
			//Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
			
			ArrayList<Kupac> kupci = new ArrayList<Kupac>();
				for (Map.Entry<String, Kupac> entry : kupacDAO.getKupci().entrySet()) {
					if((entry.getValue().getIme().contains(p) || entry.getValue().getPrezime().contains(p) || entry.getValue().getKorisnickoIme().contains(p) || entry.getValue().getTipKupca().getImeTipa().toString().contains(t)) && !entry.getValue().isObrisan() && entry.getValue().isSumnjiv())
						
						kupci.add( entry.getValue());
		    	}	
			return g.toJson(kupci);
			
		});

	}

}
