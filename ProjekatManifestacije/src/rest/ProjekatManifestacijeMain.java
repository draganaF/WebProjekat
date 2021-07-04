package rest;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Administrator;
import beans.Korisnik;
import beans.Kupac;
import beans.Lokacija;
import beans.Manifestacija;
import beans.ManifestacijaDAO;
import beans.Prodavac;
import beans.TipKupca;
import beans.Karta;
import beans.Komentar;
import dao.AdministratorDAO;
import dao.KartaDAO;
import dao.KomentarDAO;

import dao.KupacDAO;
import dao.LokacijaDAO;
import dao.ManifestacijeDAO;
import dao.ProdavacDAO;
import dao.TipKupcaDAO;

public class ProjekatManifestacijeMain  {

	private static Gson g = new Gson();
	private static KupacDAO kupacDAO = new KupacDAO();
	private static AdministratorDAO administratorDAO = new AdministratorDAO();
	private static ProdavacDAO prodavacDAO = new ProdavacDAO();
	private static KartaDAO kartaDAO = new KartaDAO();
	private static ManifestacijeDAO manifestacijeDAO = new ManifestacijeDAO();
	private static LokacijaDAO lokacijeDAO = new LokacijaDAO();
	private static KomentarDAO komentariDAO = new KomentarDAO();
	private static TipKupcaDAO tipKupcaDAO = new  TipKupcaDAO();

	
	public static void main(String[] args) throws IOException {
		port(8080);
		
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		get("/manifestations", (req, res) -> {
			return g.toJson(manifestacijeDAO.getValidneManifestacije());
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
		
		get("/searchManifestations", (req, res) -> {
			String filterAvaible = (req.queryParams("filterAvaiable")).strip();
			String filterType = (req.queryParams("filterType")).strip();
			String searchDateFrom = (req.queryParams("searchDateFrom")).strip();
			String searchDateTo = (req.queryParams("searchDateTo")).strip();
			String searchPriceFrom = (req.queryParams("searchPriceFrom")).strip();
			String searchPriceTo = (req.queryParams("searchPriceTo")).strip();
			String searchLocation = (req.queryParams("searchLocation")).strip();
			String searchName = (req.queryParams("searchName")).strip();
			HashMap<Integer,Manifestacija> manifestacije = manifestacijeDAO.getManifestacije();
			return g.toJson(manifestacijeDAO.pretraga(manifestacije,filterAvaible, filterType, searchName, searchLocation, searchPriceFrom, searchPriceTo, searchDateFrom, searchDateTo));
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
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
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
					if(p.equals("") && !t.equals("")) {
						if( entry.getValue().getTipKupca().contains(t) && !entry.getValue().isObrisan())
							kupci.add( entry.getValue());
					}else {
						if((entry.getValue().getIme().contains(p) || entry.getValue().getPrezime().contains(p) || entry.getValue().getKorisnickoIme().contains(p) || entry.getValue().getTipKupca().contains(t)) && !entry.getValue().isObrisan())
							kupci.add( entry.getValue());
					}
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
					if(p.equals("") && !t.equals("")) {
						if( entry.getValue().getTipKupca().contains(t) && !entry.getValue().isObrisan() && entry.getValue().isSumnjiv())
							kupci.add( entry.getValue());
					}else {
						if((entry.getValue().getIme().contains(p) || entry.getValue().getPrezime().contains(p) || entry.getValue().getKorisnickoIme().contains(p) || entry.getValue().getTipKupca().contains(t)) && !entry.getValue().isObrisan() && entry.getValue().isSumnjiv())
							kupci.add( entry.getValue());
					}
					}
			return g.toJson(kupci);
			
		});

		get("/manifestationLocations", (req, res) -> {
			return g.toJson(lokacijeDAO.getLokacije().values());
		});
		
		get("/comments", (req, res) -> {
			return g.toJson(komentariDAO.getMapaKomentara().values());
		});
		
		post("/manifestationsActivation", (req, res)-> {
			String id =  req.queryParams("id");
			String aktivacija = req.queryParams("activation");
			Manifestacija m = null;
			m = manifestacijeDAO.nadjiManifestaciju(Integer.parseInt(id));
			if(m == null) {
				return false;
			}else {
				if(aktivacija.equals("p")) {
					manifestacijeDAO.postaviAktivnost(Integer.parseInt(id));
				}else {
					manifestacijeDAO.obrisiManifestaciju(Integer.parseInt(id));
				}
			}
			return true;
			
		});
		
		get("/manifestation", (req, res) -> {
			String id = req.queryParams("id");
			Manifestacija m = manifestacijeDAO.nadjiManifestaciju(Integer.parseInt(id));
			return g.toJson(m);
		});
		
		get("/commentsForManifestation", (req, res) -> {
			String id = req.queryParams("id");
			return g.toJson(komentariDAO.komentariManifestacije(Integer.parseInt(id)));
		});
		
		get("/manifestationLocation", (req, res) -> {
			String id = req.queryParams("id");
			return g.toJson(lokacijeDAO.lokacijaManifestacije(Integer.parseInt(id)));
		});
		
		get("/tickets", (req, res) -> {
			return g.toJson(kartaDAO.validneKarte());
		});
		
		get("/ticketManifestation", (req, res)->{
			String i =  req.queryParams("id");
			
			Manifestacija m = manifestacijeDAO.nadjiManifestaciju(Integer.parseInt(i));
			if(m == null) {
				return false;
			}
			return g.toJson(m);
			
		});
		
		get("/reservedTickets", (req, res) -> {
			String korisnickoIme =  req.queryParams("korisnickoIme");
			Prodavac p = prodavacDAO.nadjiProdavcaProfil(korisnickoIme);
			HashMap<Integer,Manifestacija> manifestacije = manifestacijeDAO.nadjiManifestacijeId(p.getManifestacije());
			return g.toJson(kartaDAO.rezervisaneKarte(manifestacije));
		});
		
		get("/searchTickets", (req, res)->{
			String pretragaManifestacija =  req.queryParams("pretragaManifestacija");
			String pretragaCenaOd =  req.queryParams("pretragaCenaOd");
			String pretragaCenaDo =  req.queryParams("pretragaCenaDo");
			String pretragaDatumOd = req.queryParams("pretragaDatumOd");
			String pretragaDatumDo = req.queryParams("pretragaDatumDo");
			
			
			String role = req.queryParams("role");
			String korisnickoIme = req.queryParams("korisnickoIme");
			ArrayList<Karta> rezervisaneProdavac = new ArrayList<Karta>();
			if(role.equals("prodavac")) {
				Prodavac p = prodavacDAO.nadjiProdavcaProfil(korisnickoIme);
				HashMap<Integer,Manifestacija> manifestacije = manifestacijeDAO.nadjiManifestacijeId(p.getManifestacije());
				rezervisaneProdavac = kartaDAO.rezervisaneKarte(manifestacije);
			}else if(role.equals("kupac")) {
				Kupac k = kupacDAO.nadjiKupcaProfil(korisnickoIme);
				rezervisaneProdavac = kartaDAO.nadjiKarteKupca(k.getKarte());
			}
			
			String status =  req.queryParams("status");
			String tip = req.queryParams("tip");
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
			ArrayList<Karta> karte = new ArrayList<Karta>();
			ArrayList<Karta> tempKarte = new ArrayList<Karta>();
			if(pretragaCenaOd.equals("") || pretragaCenaDo.equals("")) {
				if(pretragaDatumOd.equals("") || pretragaDatumDo.equals("")) {
					if(role.equals("admin")) {
						tempKarte = kartaDAO.nadjiPoParametrima(kartaDAO.getKarte(),tip,status);
					}else if(role.equals("prodavac") || role.equals("kupac")){
						tempKarte = kartaDAO.nadjiPoParametrima(rezervisaneProdavac,tip,status);
					}
					if(tempKarte.size() == 0) {
						if(role.equals("admin")) {
							karte = manifestacijeDAO.nadjiPoNazivu(kartaDAO.getKarte(),pretragaManifestacija);
							return  gsonReg.toJson(karte);
						}else if(role.equals("prodavac") || role.equals("kupac")){
							karte = manifestacijeDAO.nadjiPoNazivu(rezervisaneProdavac,pretragaManifestacija);
							return  gsonReg.toJson(karte);
						}
					}else {
						if(pretragaManifestacija.equals("")) {
							
							karte = tempKarte;
							return  gsonReg.toJson(karte);
						}else {
							karte = manifestacijeDAO.nadjiPoNazivu(tempKarte,pretragaManifestacija);
							return  gsonReg.toJson(karte);
					}
						}
					
				}else {
					if(role.equals("admin")) {
						tempKarte = kartaDAO.nadjiPoParametrima(kartaDAO.getKarte(), tip,status);
					}else if(role.equals("prodavac") || role.equals("kupac")) {
						tempKarte = kartaDAO.nadjiPoParametrima(rezervisaneProdavac, tip,status);
					}
					if(tempKarte.size() == 0) {
						if(role.equals("admin")) {
							karte = manifestacijeDAO.nadjiPoDatumima(kartaDAO.getKarte(),pretragaDatumOd, pretragaDatumDo, pretragaManifestacija);
							return  gsonReg.toJson(karte);
						}else if(role.equals("prodavac") || role.equals("kupac")) {
							karte = manifestacijeDAO.nadjiPoDatumima(rezervisaneProdavac,pretragaDatumOd, pretragaDatumDo, pretragaManifestacija);
							return  gsonReg.toJson(karte);
						}
					}else {
						//if(pretragaManifestacija.equals("")) {
						//	karte = tempKarte;
						//	return  gsonReg.toJson(karte);
						//}else {
							karte = manifestacijeDAO.nadjiPoDatumima(tempKarte,pretragaDatumOd, pretragaDatumDo, pretragaManifestacija);
							return  gsonReg.toJson(karte);
						//}
						
					}
					
				}
			}else {
				if(pretragaDatumOd.equals("") || pretragaDatumDo.equals("")) {
					if(role.equals("admin")) {
						tempKarte = kartaDAO.nadjiPoCeniStatusuTipu(kartaDAO.getKarte(), tip,status,pretragaCenaOd, pretragaCenaDo);
					}else if(role.equals("prodavac") || role.equals("kupac")) {
						tempKarte = kartaDAO.nadjiPoCeniStatusuTipu(rezervisaneProdavac, tip,status,pretragaCenaOd, pretragaCenaDo);
					}
					//if(tempKarte.size() == 0) {
						if(role.equals("admin")) {
							karte = manifestacijeDAO.nadjiPoNazivu(kartaDAO.getKarte(),pretragaManifestacija);
							return  gsonReg.toJson(karte);
						}else if(role.equals("prodavac") || role.equals("kupac")) {
							karte = manifestacijeDAO.nadjiPoNazivu(rezervisaneProdavac,pretragaManifestacija);
							return  gsonReg.toJson(karte);
					//	}
					//}else {
						//if(pretragaManifestacija.equals("")) {
							//karte = tempKarte;
							//return  gsonReg.toJson(karte);
						//}else {
						//	karte = manifestacijeDAO.nadjiPoNazivu(tempKarte,pretragaManifestacija);
							//return  gsonReg.toJson(karte);
						//}
					}
					
				}else {
					if(role.equals("admin")) {
						tempKarte = kartaDAO.nadjiPoCeniStatusuTipu(kartaDAO.getKarte(), tip,status,pretragaCenaOd, pretragaCenaDo);
					}else if(role.equals("prodavac") || role.equals("kupac")) {
						tempKarte = kartaDAO.nadjiPoCeniStatusuTipu(rezervisaneProdavac, tip,status,pretragaCenaOd, pretragaCenaDo);
					}
					//if(tempKarte.size() == 0) {
						if(role.equals("admin")) {
							karte = manifestacijeDAO.nadjiPoDatumima(kartaDAO.getKarte(),pretragaDatumOd, pretragaDatumDo,pretragaManifestacija);
							return  gsonReg.toJson(karte);
						}else if(role.equals("prodavac") || role.equals("kupac")) {
							karte = manifestacijeDAO.nadjiPoDatumima(rezervisaneProdavac,pretragaDatumOd, pretragaDatumDo,pretragaManifestacija);
							return  gsonReg.toJson(karte);
					//	}
					//}else {
					//	if(pretragaManifestacija.equals("")) {
							
						//	karte = tempKarte;
					//		return  gsonReg.toJson(karte);
					//	}else {
						//	karte = manifestacijeDAO.nadjiPoDatumima(tempKarte,pretragaDatumOd, pretragaDatumDo,pretragaManifestacija);
						//	return  gsonReg.toJson(karte);
					//	}
					}
					
				}
			}
			return true;
			 
			
		});
		
		
		post("/deleteTicket", (req, res)-> {
			String id =  req.queryParams("id");
			Karta k = kartaDAO.nadjiKartu(id);
			kartaDAO.obrisiKartu(id);
			Manifestacija m = manifestacijeDAO.nadjiManifestaciju(k.getManifestacija());
			manifestacijeDAO.promeniBrojMesta(m);
			return true;
			
		});
		
		get("/sellerManifestationsUsers", (req, res) -> {
			String korisnickoIme =  req.queryParams("korisnickoIme");
			Prodavac p = prodavacDAO.nadjiProdavcaProfil(korisnickoIme);
			HashMap<Integer,Manifestacija> manifestacije = manifestacijeDAO.nadjiManifestacijeId(p.getManifestacije());
			ArrayList<Karta> karte = kartaDAO.rezervisaneKarte(manifestacije);
			return g.toJson(kupacDAO.nadjiKupce(karte));
		});
		
		get("/searchUser", (req, res)->{
			String p =  req.queryParams("pretraga");
			String kIme =  req.queryParams("korisnickoIme");
			String t =  req.queryParams("tip");
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
			
			Prodavac prodavac = prodavacDAO.nadjiProdavcaProfil(kIme);
			HashMap<Integer,Manifestacija> manifestacije = manifestacijeDAO.nadjiManifestacijeId(prodavac.getManifestacije());
			ArrayList<Karta> karte = kartaDAO.rezervisaneKarte(manifestacije);
			ArrayList<Kupac> sviKupci = kupacDAO.nadjiKupce(karte);
			ArrayList<Kupac> kupci = new ArrayList<Kupac>();
			for (Kupac k : sviKupci) {
				if(p.equals("")) {
					if( k.getTipKupca().contains(t)) {
						kupci.add( k);
					}
				}else {
					if(k.getIme().contains(p) || k.getPrezime().contains(p) || k.getKorisnickoIme().contains(p) || k.getTipKupca().contains(t)) {
						kupci.add( k);
						
		    	}
				}
			}
			return gsonReg.toJson(kupci);
			
		});
		
		get("/sellerManifestations", (req, res) -> {
			String korisnickoIme =  req.queryParams("korisnickoIme");
			Prodavac p = prodavacDAO.nadjiProdavcaProfil(korisnickoIme);
			HashMap<Integer,Manifestacija> manifestacije = manifestacijeDAO.nadjiManifestacijeId(p.getManifestacije());
			return g.toJson(manifestacije.values());
		});
		
		get("/searchManifestationsSeller", (req, res) -> {
			String filterAvaible = req.queryParams("filterAvaiable");
			String filterType = req.queryParams("filterType");
			String searchDateFrom = req.queryParams("searchDateFrom");
			String searchDateTo = req.queryParams("searchDateTo");
			String searchPriceFrom = req.queryParams("searchPriceFrom");
			String searchPriceTo = req.queryParams("searchPriceTo");
			String searchLocation = req.queryParams("searchLocation");
			String searchName = req.queryParams("searchName");
			String korisnickoIme = req.queryParams("korisnickoIme");
			Prodavac p = prodavacDAO.nadjiProdavcaProfil(korisnickoIme);
			HashMap<Integer,Manifestacija> manifestacije = manifestacijeDAO.nadjiManifestacijeId(p.getManifestacije());
			return g.toJson(manifestacijeDAO.pretraga(manifestacije,filterAvaible, filterType, searchName, searchLocation, searchPriceFrom, searchPriceTo, searchDateFrom, searchDateTo));
		});
		
		get("/manifestationTickets", (req, res) -> {
			String id = req.queryParams("id");
			return g.toJson(kartaDAO.karteManifestacije(Integer.parseInt(id)));
		});
		
		post("/addComment", (req, res) -> {			
			String reqBody = req.body();
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
			
			Komentar k = gsonReg.fromJson(reqBody, Komentar.class);
			
			int id = komentariDAO.nadjiSledeciId();
			k.setId(id);
			
			HashMap<Integer, Komentar> komentari = komentariDAO.getMapaKomentara();
			komentari.put(id, k);
			komentariDAO.setKomentari(komentari);
			komentariDAO.upisiKomentare();
			
			return true;
			
		});
		
		get("/approveComment", (req, res) -> {
			int id = Integer.parseInt(req.queryParams("id"));
			int approve = Integer.parseInt(req.queryParams("odobrena"));
			
			komentariDAO.odobriKomentar(id, approve);
			komentariDAO.upisiKomentare();
			return true;
		});

		get("/commentsToApprove", (req, res) -> {
			String username = req.queryParams("username");
			HashMap<Integer, Manifestacija> manifestacije = manifestacijeDAO.getManifestacije();
			
			return g.toJson(komentariDAO.komentariZaManifestacijeProdavca(manifestacije, username));
		});
		
		get("/getManifestation", (req, res) -> {
			int id = Integer.parseInt(req.queryParams("id"));
			return g.toJson(manifestacijeDAO.nadjiManifestaciju(id));
		});
		post("/updateManifestation", (req, res) -> {
			String reqBody = req.body();
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm").create();
			
			ManifestacijaDAO m = gsonReg.fromJson(reqBody, ManifestacijaDAO.class);
			Lokacija lokacija = m.getLokacija();
			if(!manifestacijeDAO.checkAvaiability(m.getManifestacija(), lokacija)) {
				return false;
			}
			HashMap<Integer, Lokacija> lokacije = lokacijeDAO.getLokacije();
			for(Lokacija l : lokacije.values()) {
				
				if(l.getId() == lokacija.getId()) {
					l.setGeografskaDuzina(lokacija.getGeografskaDuzina());
					l.setGeografskaSirina(lokacija.getGeografskaSirina());
					l.setAdresa(lokacija.getAdresa());
					break;
				}
			}
			
			lokacijeDAO.setLokacije(lokacije);
			lokacijeDAO.upisiLokacije();
			
			Manifestacija manifestacija = m.getManifestacija();
			if(manifestacija.getBrojMesta() < manifestacija.getSlobodnaMesta()) {
				return false;
			}
			boolean slikaPromijenjena = false;
			if(manifestacija.getSlika()!=null) {
				String imageString = manifestacija.getSlika().split(",")[1];
				BufferedImage image = null;
	            byte[] imageByte;
	 
	            
	            imageByte = Base64.getDecoder().decode(imageString);
	            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
	            image = ImageIO.read(bis);
	            bis.close();
	            String imageName= "manifestacija" + manifestacija.getId() + ".png";
	            
	            File outputfile = new File(System.getProperty("user.dir")+ "\\static\\images\\" + imageName);
	            ImageIO.write(image, "png", outputfile);
	            
	            manifestacija.setSlika("../images/" + imageName);
	            slikaPromijenjena = true;
			}
		
			
			
			HashMap<Integer, Manifestacija> manifestacije = manifestacijeDAO.getManifestacije();
			for(Manifestacija mm : manifestacije.values()) {
				if(mm.getId() == manifestacija.getId()) {
					mm.setAktivnost(manifestacija.isAktivnost());
					mm.setBrojMesta(manifestacija.getBrojMesta());
					mm.setCenaKarte(manifestacija.getCenaKarte());
					mm.setDatum(manifestacija.getDatum());
					mm.setLokacija(lokacija.getId());
					mm.setNaziv(manifestacija.getNaziv());
					mm.setSlobodnaMesta(manifestacija.getSlobodnaMesta());
					mm.setStatus(manifestacija.isStatus());
					mm.setTipManifestacije(manifestacija.getTipManifestacije());
					if(slikaPromijenjena) {
						mm.setSlika(manifestacija.getSlika());
					}
					break;
				}
			}
			
			manifestacijeDAO.setManifestacije(manifestacije);
			manifestacijeDAO.upisiManifestacije();
			
			return true;
		});
		post("/createManifestation", (req, res) -> {
			String reqBody = req.body();
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm").create();
			
			ManifestacijaDAO m = gsonReg.fromJson(reqBody, ManifestacijaDAO.class);
			int lokacijaId = lokacijeDAO.nadjiSledeciId();
			Lokacija lokacija = m.getLokacija();
			lokacija.setId(lokacijaId);
			
			int id = manifestacijeDAO.nadjiSledeciId();
			Manifestacija manifestacija = m.getManifestacija();
			manifestacija.setId(id);
			manifestacija.setLokacija(lokacijaId);
		
			if(manifestacija.getSlika() == null) {
				return false;
			}
			String imageString = manifestacija.getSlika().split(",")[1];
			BufferedImage image = null;
            byte[] imageByte;
 
            
            imageByte = Base64.getDecoder().decode(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
            String imageName= "manifestacije" + id + ".png";
            
            
			if(manifestacijeDAO.checkAvaiability(manifestacija, lokacija)) {
				File outputfile = new File(System.getProperty("user.dir")+ "\\static\\images\\" + imageName);
	            ImageIO.write(image, "png", outputfile);
	            
	            manifestacija.setSlika("../images/" + imageName);
	            manifestacija.setAktivnost(false);
				HashMap<Integer, Manifestacija> manifestacije = manifestacijeDAO.getManifestacije();
				manifestacije.put(id, manifestacija);
				manifestacijeDAO.setManifestacije(manifestacije);
				manifestacijeDAO.upisiManifestacije();
			}
			else {
				return false;
			}
			
			HashMap<Integer, Lokacija> lokacije = lokacijeDAO.getLokacije();
			lokacije.put(lokacijaId, lokacija);
			lokacijeDAO.setLokacije(lokacije);
			lokacijeDAO.upisiLokacije();
			
			HashMap<String, Prodavac> prodavci = prodavacDAO.getProdavci();
			for(Prodavac p : prodavci.values()) {
				if(manifestacija.getProdavac().equalsIgnoreCase(p.getKorisnickoIme())) {
					p.getManifestacije().add(manifestacija.getId());
				}
			}
			prodavacDAO.upisiProdavce();
			return true;
			
		});

		
		
		
		get("/userTickets", (req, res) -> {
			String korisnickoIme =  req.queryParams("korisnickoIme");
			Kupac k  = kupacDAO.nadjiKupcaProfil(korisnickoIme);
			ArrayList<Karta> karte = kartaDAO.nadjiKarteKupca(k.getKarte());
			return g.toJson(karte);
		});
		
		post("/cancelTicket", (req, res)-> {
			String korisnickoIme = req.queryParams("korisnickoIme");
			String id =  req.queryParams("id");
			Karta karta = kartaDAO.nadjiKartu(id);
			Kupac kupac = kupacDAO.nadjiKupcaProfil(korisnickoIme);
			Manifestacija m = manifestacijeDAO.nadjiManifestaciju(karta.getManifestacija());
			boolean value = kartaDAO.odustaniOdKarte(id, m);
			if(value) {
				try {
					kartaDAO.upisiKarte();
					manifestacijeDAO.promeniBrojMesta(m);
					kupacDAO.smanjiBodove(kupac, karta);
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}else {
				return false;
			}
			
			return true;
			
		});
		
		get("/userType", (req, res) -> {
			String korisnickoIme =  req.queryParams("korisnickoIme");
			Kupac k  = kupacDAO.nadjiKupcaProfil(korisnickoIme);
			TipKupca t = tipKupcaDAO.nadjiKupca(k.getTipKupca());
			return g.toJson(t);
		});
		
		post("/reserveTicket", (req, res)-> {
			String korisnickoIme =  req.queryParams("korisnickoIme");
			String manifestacijaId = req.queryParams("manifestacija");
			String tipKarte = req.queryParams("tipKarte");
			String kolicina = req.queryParams("kolicina");
			
			Kupac k = kupacDAO.nadjiKupcaProfil(korisnickoIme);
			Manifestacija manifestacija = manifestacijeDAO.nadjiManifestaciju(Integer.parseInt(manifestacijaId));
			
			ArrayList<String> karte = kartaDAO.napraviKarte(tipKarte,kolicina, korisnickoIme,manifestacija.getId(), manifestacija.getCenaKarte());
			kupacDAO.dodajKarteKupcu(karte, tipKarte,manifestacija.getCenaKarte(),Integer.parseInt(kolicina),korisnickoIme);
			
			if(k.getTipKupca().equals("OBICAN")) {
				TipKupca t  = tipKupcaDAO.nadjiKupca("BRONZANI");
				if(k.getBrojSakupljenihBodova() == t.getTrazeniBrojBodova()) {
					k.setTipKupca("BRONZANI");
				}
			}else if(k.getTipKupca().equals("BRONZANI")) {
				TipKupca t  = tipKupcaDAO.nadjiKupca("SREBRNI");
				if(k.getBrojSakupljenihBodova() == t.getTrazeniBrojBodova()) {
					k.setTipKupca("SREBRNI");
				}
			}else if(k.getTipKupca().equals("SREBRNI")) {
				TipKupca t = tipKupcaDAO.nadjiKupca("ZLATNI");
				if(k.getBrojSakupljenihBodova() == t.getTrazeniBrojBodova()) {
					k.setTipKupca("ZLATNI");
				}
			}
			kupacDAO.upisiKupce();
			
			manifestacijeDAO.smanjiBrojMesta(Integer.parseInt(kolicina),manifestacija);
		
			return true;
			
		});
		
	}

}
