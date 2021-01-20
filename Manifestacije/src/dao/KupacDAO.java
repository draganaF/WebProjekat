package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import beans.Karta;
import beans.Kupac;
import beans.TipKupca;

public class KupacDAO {
	
	private ArrayList<Kupac> kupci;
	
	public KupacDAO() {}

	public ArrayList<Kupac> getKupci() {
		return kupci;
	}

	public void setKupci(ArrayList<Kupac> kupci) {
		this.kupci = kupci;
	}
	
	public Kupac nadjiKupca(String korisnickoIme) {
		for(Kupac kupac:kupci) {
			if(kupac.getKorisnickoIme().equals(korisnickoIme)) {
				return kupac;
			}
		}
		return null;
	}
	public void obrisiKupca(String korisnickoIme) {
		for(Kupac kupac:kupci) {
			if(kupac.getKorisnickoIme().equals(korisnickoIme)) {
				kupac.setObrisan(true);
				break;
			}
		}
	}
	public void upisiKupce() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("kupci.json");
		gson.toJson(this.kupci, fw);
		fw.flush();
		fw.close();
	}
	
	public void ucitajKupce() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<ArrayList<Kupac>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("kupci.json"));
		this.kupci = gson.fromJson(br, token);
	}
	
	public ArrayList<Kupac> nadjiSveKupceOdredjenogTipa(TipKupca tip){
		ArrayList<Kupac> lista = new ArrayList<Kupac>();
		for(Kupac kupac:kupci) {
			if(kupac.getTipKupca().equals(tip)) {
				lista.add(kupac);
			}
		}
		return lista;
	}
	
	public Kupac nadjiKupcaNaOsnovuKarte(Karta karta) {
		for(Kupac kupac:kupci) {
			for(Karta k :kupac.getKarte()) {
				if(k.equals(karta)) {
					return kupac;
				}
			}
		}
		return null;
	}


}
