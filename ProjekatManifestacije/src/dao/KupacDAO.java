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
import beans.Kupac;
import beans.Tip;
import beans.TipKupca;

public class KupacDAO {
	
	private HashMap<String,Kupac> kupci;
	
	public KupacDAO() {
		
		kupci = new HashMap<String,Kupac>();
		try {
			ucitajKupce();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
		kupac.setTipKupca(tip);
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
	}

