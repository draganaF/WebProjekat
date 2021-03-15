package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Lokacija;
import beans.Manifestacija;


public class ManifestacijeDAO {
	private HashMap<Integer,Manifestacija> manifestacije;
	private LokacijaDAO dao = new LokacijaDAO();
	
	public ManifestacijeDAO() {
		try {
			this.ucitajManifestacije();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<Integer,Manifestacija> getManifestacije(){
		return manifestacije;
	}
	
	public ArrayList<Manifestacija> sveValidne(){
		ArrayList<Manifestacija> validne = new ArrayList<Manifestacija>();
		for (Map.Entry<Integer, Manifestacija> entry : manifestacije.entrySet()) {
	        if(!entry.getValue().isObrisana()) {
	        	validne.add(entry.getValue());
	        }
	    }		
		return validne;
	}
	public ArrayList<Manifestacija> nadjiPoDatumu(LocalDateTime odDatum, LocalDateTime doDatum){

		ArrayList<Manifestacija> validne = new ArrayList<Manifestacija>();
		for (Map.Entry<Integer, Manifestacija> entry : manifestacije.entrySet()) {
	        if(!entry.getValue().isObrisana() && entry.getValue().getDatum().isAfter(odDatum) &&  entry.getValue().getDatum().isBefore(doDatum) ) {
	        	validne.add(entry.getValue());
	        }
	    }		
		return validne;
	}
	
	public ArrayList<Manifestacija> nadjiManifestacije(String naziv) {
		ArrayList<Manifestacija> validne = new ArrayList<Manifestacija>();
		for (Map.Entry<Integer, Manifestacija> entry : manifestacije.entrySet()) {
	        if(!entry.getValue().isObrisana() && entry.getValue().getNaziv().equalsIgnoreCase(naziv)) {
	        	validne.add(entry.getValue());
	        }
	    }		
		return validne;
	}
	public ArrayList<Manifestacija> nadjiPoCeni(float cenaOd, float cenaDo){
		
		ArrayList<Manifestacija> validne = new ArrayList<Manifestacija>();
		for (Map.Entry<Integer, Manifestacija> entry : manifestacije.entrySet()) {
	        if(!entry.getValue().isObrisana() && entry.getValue().getCenaKarte()>= cenaOd &&entry.getValue().getCenaKarte()<=cenaDo) {
	        	validne.add(entry.getValue());
	        }
	    }	
		return validne;
	}
	
	public ArrayList<Manifestacija> nadjiPoLokaciji(Lokacija lokacija){
		ArrayList<Manifestacija> validne =new ArrayList<Manifestacija>();
		for (Map.Entry<Integer, Manifestacija> entry : manifestacije.entrySet()) {
	        if(!entry.getValue().isObrisana() && entry.getValue().getLokacija() == lokacija ) {
	        	validne.add(entry.getValue());
	        }
	    }		
		return validne;
	}
	
	public void obrisiManifestaciju(String naziv) {
		for (Map.Entry<Integer, Manifestacija> entry : manifestacije.entrySet()) {
	        if(entry.getValue().getNaziv().equalsIgnoreCase(naziv)) {
	        	entry.getValue().setObrisana(true);
	        }
	    }		
	
	}
	
	public void upisiManifestacije() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("files/manifestacije.json");
		gson.toJson(this.manifestacije, fw);
		fw.flush();
		fw.close();
	}
	
	public void ucitajManifestacije() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<HashMap<Integer,Manifestacija>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("files/manifestacije.json"));
		this.manifestacije = gson.fromJson(br, token);
	}

}
