package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Karta;
import beans.Manifestacija;
import beans.TipKarte;
import sorter.KartaSorter;

public class KartaDAO {
	private ArrayList<Karta> karte;
	
	public KartaDAO() {
		try {
			this.ucitajKarte();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Karta> getKarte() {
		return karte;
	}

	public void setKarte(ArrayList<Karta> karte) {
		this.karte = karte;
	}
	
	public ArrayList<Karta> validneKarte(){
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana()) {
				validne.add(k);
			}
		}
		
		return validne;
	}
	
	public ArrayList<Karta> karteKupca(String korisnickoIme){
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana() && k.getKupac().equalsIgnoreCase(korisnickoIme)) {
				validne.add(k);
			}
		}
		
		return validne;
	}
	
	public ArrayList<Karta> karteKupcaSumnjivi(String korisnickoIme){
		ArrayList<Karta> validne = karteKupca(korisnickoIme);
		ArrayList<Karta> karteUMesecDana = new ArrayList<Karta>();
		for(Karta k : validne) {
			// status odustanak
			if(!k.isStatus()) {
				
				karteUMesecDana.add(k);
			}
			
		}
		
		if(karteUMesecDana.size() == 5) {
			
			Collections.sort(karteUMesecDana, new KartaSorter());
			long value = karteUMesecDana.get(karteUMesecDana.size()-1).getDatumOtkazivanja().getTime() -  karteUMesecDana.get(0).getDatumOtkazivanja().getTime();
			int days = (int) (value / (1000*60*60*24));
			if(days <= 31 || days <= 30) {
				return karteUMesecDana;
			}
		}
		
		return null;
		
	}
	
	/*public ArrayList<Karta> kartePoslovca(String korisnickoIme){
		
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
<<<<<<< HEAD
			if(!k.isObrisana() && k.getManifestacija()(korisnickoIme)) {
=======
			if(!k.isObrisana() && k.getManifestacija().getProdavac().equalsIgnoreCase(korisnickoIme)) {
>>>>>>> b0aed92daf20727a6c2f6ba9e555dbe4b54d8196
				validne.add(k);
			}
		}
		
		return validne;
	}*/
	public ArrayList<Karta> karteManifestacije(int idM){
		
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana() && k.getManifestacija() ==idM) {
				validne.add(k);
			}
		}
		
		return validne;
	}
	public ArrayList<Karta> rezervisaneKarte(HashMap<Integer,Manifestacija> manifestacije){
		
		ArrayList<Karta> rezervisane = new ArrayList<Karta>();
		for(Manifestacija m :manifestacije.values()) {
			for(Karta k : karte) {
				if(!k.isObrisana() && k.isStatus() && k.getManifestacija() == m.getId()) {
					rezervisane.add(k);
				}
			}
		}
		return rezervisane;
	}

	public Karta nadjiKartu(String id) {
		for(Karta k : karte) {
			if(k.getId().equalsIgnoreCase(id)) {
				return k;
			}
		}
		return null;
	}
	
	public void obrisiKartu(String id) {
		for(Karta k : karte) {
			if(k.getId().equalsIgnoreCase(id)) {
				k.setObrisana(true);
				break;
			}
		}
		try {
			upisiKarte();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<Karta> nadjiPoCeni(int odCena, int doCena){
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana() && k.getCena() >= odCena && k.getCena()<= doCena) {
				validne.add(k);
			}
		}
		return validne;
	}
	public ArrayList<Karta> nadjiPoStatusu(boolean status){
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana() && k.isStatus()==status) {
				validne.add(k);
			}
		}
		return validne;
	}
	
	public ArrayList<Karta> nadjiPoTipu(TipKarte tip){
		ArrayList<Karta> karte = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana() && k.getTipKarte().equals(tip)) {
				karte.add(k);
			}
		}
		return karte;
	}
	
	public ArrayList<Karta> nadjiPoParametrima(ArrayList<Karta>kk, String tip, String status){
		ArrayList<Karta> izabrane = new ArrayList<Karta>();
		for(Karta k : kk) {
			if(status.equals("rezervisana")) {
				if(tip.equals("")) {
					if(!k.isObrisana() && k.isStatus()) {
						izabrane.add(k);
					}
				}else {
					if(!k.isObrisana() && k.getTipKarte().name().contains(tip) && k.isStatus()) {
						izabrane.add(k);
					}
				}
			}else if(status.equals("odustanak")) {
				if(tip.equals("")) {
					if(!k.isObrisana() && !k.isStatus()) {
						izabrane.add(k);
					}
				}else {
					if(!k.isObrisana() && k.getTipKarte().name().contains(tip) && !k.isStatus()) {
					izabrane.add(k);
					}
				}
			}else {
				if(tip.equals("")) {
					if(!k.isObrisana()) {
						izabrane.add(k);
					}
				}else {
					if(!k.isObrisana() && k.getTipKarte().name().contains(tip)) {
					izabrane.add(k);
					}
				}
			}
		}
		return izabrane;
	}
	public ArrayList<Karta> nadjiPoCeniStatusuTipu(ArrayList<Karta> kk,String tip, String status, String pretragaCenaOd, String pretragaCenaDo){
		double cenaOd = Double.parseDouble(pretragaCenaOd);
		double cenaDo = Double.parseDouble(pretragaCenaDo);
		ArrayList<Karta> nove = new ArrayList<Karta>();
		ArrayList<Karta> karteIzabrane = new ArrayList<Karta>();
		nove = nadjiPoParametrima(kk,tip, status);
		for(Karta k : nove) {
			if(k.getCena() <= cenaDo && k.getCena()>=cenaOd ) {
				karteIzabrane.add(k);
			}
		}
		return karteIzabrane;
	}

	public void upisiKarte() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("files/karte.json");
		gson.toJson(this.karte, fw);
		fw.flush();
		fw.close();
	}
	
	public void ucitajKarte() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<ArrayList<Karta>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("files/karte.json"));
		this.karte = gson.fromJson(br, token);
	}
	
	
	public ArrayList<Karta> nadjiKarteKupca(ArrayList<String> kk){
		ArrayList<Karta> karteIzabrane = new ArrayList<Karta>();
		for(Karta k : karte) {
			for(int i = 0; i< kk.size();i++) {
				if(kk.get(i).equals(k.getId())) {
					karteIzabrane.add(k);
				}
			}
			
		}
		return karteIzabrane;
	}
	
	public boolean odustaniOdKarte(String id, Manifestacija m) {
		for(Karta k : karte) {
			if(k.getId().equalsIgnoreCase(id)) {
				ZoneId defaultZoneId = ZoneId.systemDefault();
				LocalDate localDate = LocalDate.now();
			    Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
				double value = m.getDatum().getTime() - date.getTime();
				int days = (int) (value / (1000*60*60*24));
				if(days <= 7) {
					return false;
				}
				k.setStatus(false);
				k.setDatumOtkazivanja(date);
				return true;
			}
		}
		return false;
	}
	
	 static String napraviIdKarte()
	    {
	        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	                                    + "abcdefghijklmnopqrstuvxyz";
	        StringBuilder sb = new StringBuilder(10);
	        for (int i = 0; i < 10; i++) {
	            int index
	                = (int)(AlphaNumericString.length()
	                        * Math.random());
	            sb.append(AlphaNumericString
	                          .charAt(index));
	        }
	        return sb.toString();
	    }
	 
	 
	 public ArrayList<String> napraviKarte(String tipKarte, String kolicina, String korisnickoIme,int idManifestacije, float cena){
		 ArrayList<String> listaId = new ArrayList<String>();
		 
		 int n = Integer.parseInt(kolicina);
		 for(int i = 0;i<n;i++) {
			 Karta k = new Karta();
			 k.setId(napraviIdKarte());
			 k.setDatumOtkazivanja(null);
			 k.setKupac(korisnickoIme);
			 k.setObrisana(false);
			 k.setStatus(true);
			 k.setManifestacija(idManifestacije);
			 if(tipKarte.equals("REGULAR")) {
				 k.setTipKarte(TipKarte.REGULAR);
				 k.setCena(cena);
			 }else if(tipKarte.equals("VIP")) {
				 k.setTipKarte(TipKarte.VIP);
				 k.setCena(4*cena);
			 }else {
				 k.setTipKarte(TipKarte.FANPIT);
				 k.setCena(2*cena);
			 }
			 listaId.add(k.getId());
			 karte.add(k); 
		 }
		 
		 try {
				upisiKarte();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 return listaId;
		 
	 }
}
