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


public class ManifestacijeDAO {
	private ArrayList<Manifestacija> manifestacije;
	
	public ManifestacijeDAO() {
		
	}
	
	public ArrayList<Manifestacija> getManifestacije(){
		return manifestacije;
	}
	
	public ArrayList<Manifestacija> sveValidne(){
		ArrayList<Manifestacija> validne = new ArrayList<Manifestacija>();
		for(Manifestacija m : manifestacije) {
			if(!m.isObrisana()) {
				validne.add(m);
				}
		}
		return validne;
	}
	
	public Manifestacija nadjiManifestaciju(String naziv) {
		for(Manifestacija m : manifestacije) {
			if(m.getNaziv().equalsIgnoreCase(naziv)) {
				return m;
			}
		}
		return null;
	}
	
	public void obrisiManifestaciju(String naziv) {
		for(Manifestacija m : manifestacije) {
			if(m.getNaziv().equals(naziv)) {
				m.setObrisana(true);
			}
		}
	}
	//NE znam da li je ovo ok i moze ovako 
	public void upisiManifestacije() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("manifestacije.json");
		gson.toJson(this.manifestacije, fw);
		fw.flush();
		fw.close();
	}
	
	public void ucitajManifestacije() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<ArrayList<Manifestacija>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("manifestacije.json"));
		this.manifestacije = gson.fromJson(br, token);
	}
}
