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

import beans.Manifestacija;
import beans.Prodavac;

public class ProdavacDAO {
	private HashMap<String,Prodavac> prodavci;
	
	public ProdavacDAO() 
	{
		prodavci = new HashMap<String,Prodavac>();
		
		try {
			ucitajProdavce();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public HashMap<String,Prodavac> getProdavci() {
		return prodavci;
	}

	public void setProdavci(HashMap<String,Prodavac> prodavci) {
		this.prodavci = prodavci;
	}

	public Prodavac nadjiProdavca(String korisnickoIme, String lozinka) {
		for (Map.Entry<String, Prodavac> entry : prodavci.entrySet()) {
	        if(entry.getValue().getKorisnickoIme().equals(korisnickoIme) && entry.getValue().getLozinka().equals(lozinka)) {
	        	return entry.getValue();
	        }
	    }		
		return null;
	}
	
	public Prodavac nadjiProdavcaProfil(String korisnickoIme) {
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
		try {
			upisiProdavce();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		prodavac.setManifestacije(new ArrayList<Integer>());
		prodavac.setObrisan(false);
		prodavci.put(prodavac.getKorisnickoIme(),prodavac);
		try {
			upisiProdavce();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prodavac;
	}
	
	public void izmeniProdavca(String korisnickoIme, Prodavac prodavac) {
		for (Map.Entry<String, Prodavac> entry : prodavci.entrySet()) {
	        if(entry.getValue().getKorisnickoIme().equals(korisnickoIme)) {
	        	entry.getValue().setIme(prodavac.getIme());
	        	entry.getValue().setPrezime(prodavac.getPrezime());
	        	entry.getValue().setPol(prodavac.getPol());
	        }
	    }	
		try {
			upisiProdavce();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void izmeniLozinku(String korisnickoIme, String lozinka) {
		for (Map.Entry<String, Prodavac> entry : prodavci.entrySet()) {
	        if(entry.getValue().getKorisnickoIme().equals(korisnickoIme)) {
	        	entry.getValue().setLozinka(lozinka);
	        	
	        }
	    }	
		try {
			upisiProdavce();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
