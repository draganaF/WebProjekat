package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Karta;
import beans.Kupac;
import beans.Manifestacija;
import beans.Tip;
import beans.TipKupca;

public class KupacDAO {
	
	private HashMap<String,Kupac> kupci;
	
	public KupacDAO() {
		
		kupci = new HashMap<String,Kupac>();
		try {
			ucitajKupce();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String,Kupac> getKupci() {
		return kupci;
	}

	public void setKupci(HashMap<String,Kupac> kupci) {
		this.kupci = kupci;
	}
	
	public Kupac nadjiKupca(String korisnickoIme, String lozinka) {
		for (Map.Entry<String, Kupac> entry : kupci.entrySet()) {
	        if(entry.getValue().getKorisnickoIme().equals(korisnickoIme) && entry.getValue().getLozinka().equals(lozinka) ) {
	        	return entry.getValue();
	        }
	    }
		return null;
	}
	
	public Kupac nadjiKupcaProfil(String korisnickoIme) {
		for (Map.Entry<String, Kupac> entry : kupci.entrySet()) {
	        if(entry.getValue().getKorisnickoIme().equals(korisnickoIme) ) {
	        	return entry.getValue();
	        }
	    }
		return null;
	}
	
	public void obrisiKupca(String korisnickoIme) {
		for (Map.Entry<String, Kupac> entry : kupci.entrySet()) {
	        if(entry.getValue().getKorisnickoIme().equals(korisnickoIme) ) {
	        	entry.getValue().setObrisan(true);
	        }
	    }
	}
	
	public void izmeniKupca(String korisnickoIme, Kupac kupac) {
		for (Map.Entry<String, Kupac> entry : kupci.entrySet()) {
	        if(entry.getValue().getKorisnickoIme().equals(korisnickoIme) ) {
	        	entry.getValue().setIme(kupac.getIme());
	        	entry.getValue().setPrezime(kupac.getPrezime());
	        	entry.getValue().setPol(kupac.getPol());
	        }
	    }
		try {
			upisiKupce();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void izmeniLozinku(String korisnickoIme, String lozinka) {
		for (Map.Entry<String, Kupac> entry : kupci.entrySet()) {
	        if(entry.getValue().getKorisnickoIme().equals(korisnickoIme) ) {
	        	entry.getValue().setLozinka(lozinka);	
	        }
	    }
		try {
			upisiKupce();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void upisiKupce() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("files/kupci.json");
		gson.toJson(this.kupci, fw);
		fw.flush();
		fw.close();
	}
	
	public Kupac dodajKupca(Kupac kupac) {
		kupac.setBrojSakupljenihBodova(0);
		kupac.setObrisan(false);
		kupac.setKarte(new ArrayList<String>());
		TipKupca tip = new TipKupca();
		tip.setImeTipa(Tip.OBICAN);
		tip.setPopust(0);
		tip.setTrazeniBrojBodova(0);
		kupac.setTipKupca("OBICAN");
		kupci.put(kupac.getKorisnickoIme(),kupac);
		try {
			this.upisiKupce();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return kupac;
	}
	
	public void ucitajKupce() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<HashMap<String,Kupac>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("files/kupci.json"));
		this.kupci = gson.fromJson(br, token);
	}
	
	public ArrayList<Kupac> nadjiSveKupceOdredjenogTipa(TipKupca tip){
		ArrayList<Kupac> lista = new ArrayList<Kupac>();
		for (Map.Entry<String, Kupac> entry : kupci.entrySet()) {
	        if(entry.getValue().getTipKupca().equals(tip) ) {
	        	lista.add(entry.getValue());
	        }
	    }
		return lista;
	}
	/*public Kupac nadjiKupcaNaOsnovuKarte(Karta karta) {
		for (Map.Entry<String, Kupac> entry : kupci.entrySet()) {
			for(Karta k : entry.getValue().getKarte())
	        if(k.equals(karta) ) {
	        	return entry.getValue();
	        }
	    }
		return null;
	}*/
	
	public void podesiSumnjive(ArrayList<Kupac> noviKupci) {
		for (Map.Entry<String, Kupac> entry : kupci.entrySet()) {
			for(Kupac k : noviKupci) {
				if(k.getKorisnickoIme().equals(entry.getValue().getKorisnickoIme())) {
					if(k.isSumnjiv()) {
						entry.getValue().setSumnjiv(true);
					}
				}
			}
	        	
	        }
		try {
			upisiKupce();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	


	public ArrayList<Kupac> nadjiKupce(ArrayList<Karta> karte){
	ArrayList<Kupac> lista = new ArrayList<Kupac>();
	for(Karta karta : karte) {
		for(Kupac k : kupci.values()) {
			if(karta.getKupac().equals(k.getKorisnickoIme()) && !k.isObrisan()) {
				lista.add(k);
			}
		}
	}
	return lista;
	}
	
	public void smanjiBodove(Kupac k, Karta karta) {
		for(Kupac kupac : kupci.values()) {
				if(kupac.getKorisnickoIme().equals(k.getKorisnickoIme())) {
					int trenutniBodovi = kupac.getBrojSakupljenihBodova();
					int noviBodovi = (int) (trenutniBodovi - ((karta.getCena()/1000)*133*4));
					if(noviBodovi <0) {
						kupac.setBrojSakupljenihBodova(0);
					}else {
						kupac.setBrojSakupljenihBodova(noviBodovi);
					}
					
				}
		}
		try {
			upisiKupce();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void dodajKarteKupcu(ArrayList<String> karte, String tipKarte, float cena,int kolicina,String korisnickoIme) {
		int value = 0;
		if(tipKarte.equals("REGULAR")) {
			value = (int) (((kolicina * cena)/1000) * 133*4);
		}else if(tipKarte.equals("VIP")) {
			value = (int) (((kolicina * cena)/1000) * 133*4 * 4);
		}else {
			value = (int) (((kolicina * cena)/1000) * 133*4 *2);
		}
		
		Kupac k = nadjiKupcaProfil(korisnickoIme);
		for(int i =0;i<karte.size();i++) {
			k.getKarte().add(karte.get(i));
		}
		
		k.setBrojSakupljenihBodova(k.getBrojSakupljenihBodova() +value);
		
		try {
			upisiKupce();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

