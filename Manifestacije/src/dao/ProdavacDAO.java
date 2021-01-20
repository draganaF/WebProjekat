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
import beans.Manifestacija;
import beans.Prodavac;

public class ProdavacDAO {
	private ArrayList<Prodavac> prodavci;
	
	public ProdavacDAO() {}

	public ArrayList<Prodavac> getProdavci() {
		return prodavci;
	}

	public void setProdavci(ArrayList<Prodavac> prodavci) {
		this.prodavci = prodavci;
	}

	public Prodavac nadjiProdavca(String korisnickoIme) {
		for(Prodavac prodavac:prodavci) {
			if(prodavac.getKorisnickoIme().equals(korisnickoIme)) {
				return prodavac;
			}
		}
		return null;
	}
	public void obrisiProdavca(String korisnickoIme) {
		for(Prodavac prodavac:prodavci) {
			if(prodavac.getKorisnickoIme().equals(korisnickoIme)) {
				prodavac.setObrisan(true);
				break;
			}
		}
	}
	public void upisiProdavce() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("prodavci.json");
		gson.toJson(this.prodavci, fw);
		fw.flush();
		fw.close();
	}
	
	public void ucitajProdavce() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<ArrayList<Prodavac>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("prodavci.json"));
		this.prodavci = gson.fromJson(br, token);
	}
	public Prodavac nadjiProdavcaNaOsnovuManifestacije(Manifestacija manifestacija) {
		for(Prodavac prodavac : prodavci) {
			for(Manifestacija m:prodavac.getManifestacije()) {
				if(m.equals(manifestacija)) {
					return prodavac;
				}
			}
		}
		return null;
	}

}
