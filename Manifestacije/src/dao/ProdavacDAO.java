package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import beans.Prodavac;

public class ProdavacDAO {
	private HashMap<String,Prodavac> prodavci;
	
	public ProdavacDAO() {}

	public HashMap<String,Prodavac> getProdavci() {
		return prodavci;
	}

	public void setProdavci(HashMap<String,Prodavac> prodavci) {
		this.prodavci = prodavci;
	}

	public Prodavac nadjiProdavca(String korisnickoIme) {
		for (Map.Entry<String, Prodavac> entry : prodavci.entrySet()) {
	        if(entry.getValue().getKorisnickoIme().equals(korisnickoIme)) {
	        	return entry.getValue();
	        }
	    }		
		return null;
	}
	public void obrisiProdavca(String korisnickoIme) {
		for (Map.Entry<String, Prodavac> entry : prodavci.entrySet()) {
	        if(entry.getValue().getKorisnickoIme().equals(korisnickoIme)) {
	        	entry.getValue().setObrisan(true);
	        }
	    }	
	}
	public void upisiProdavce() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("files/prodavci.json");
		gson.toJson(this.prodavci, fw);
		fw.flush();
		fw.close();
	}
	
	public void ucitajProdavce() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<HashMap<String,Prodavac>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("files/prodavci.json"));
		this.prodavci = gson.fromJson(br, token);
	}
	/*public Prodavac nadjiProdavcaNaOsnovuManifestacije(ArrayList<Manifestacija> manifestacija) {
		for(Prodavac prodavac : prodavci) {
			for(Manifestacija m:prodavac.getManifestacije()) {
				if(m.equals(manifestacija)) {
					return prodavac;
				}
			}
		}
		return null;
	}*/
	
	public Prodavac dodajProdavca(Prodavac prodavac) {
		prodavci.put(prodavac.getKorisnickoIme(),prodavac);
		return prodavac;
	}

}
