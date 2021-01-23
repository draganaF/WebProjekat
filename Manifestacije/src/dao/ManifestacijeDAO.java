package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Manifestacija;


public class ManifestacijeDAO {
	private ArrayList<Manifestacija> manifestacije;
	
	public ManifestacijeDAO() {
		try {
			this.ucitajManifestacije();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
	public ArrayList<Manifestacija> nadjiPoDatumu(LocalDateTime odDatum, LocalDateTime doDatum){

		ArrayList<Manifestacija> validne = new ArrayList<Manifestacija>();
		for(Manifestacija m : manifestacije) {
			if(!m.isObrisana() && m.getDatum().isAfter(odDatum) && m.getDatum().isBefore(doDatum)) {
				validne.add(m);
			}
		}
		return validne;
	}
	
	public ArrayList<Manifestacija> nadjiManifestacije(String naziv) {
		ArrayList<Manifestacija> validne = new ArrayList<Manifestacija>();
		for(Manifestacija m : manifestacije) {
			if(!m.isObrisana() && m.getNaziv().equalsIgnoreCase(naziv)) {
				validne.add(m);
			}
		}
		return validne;
	}
	public ArrayList<Manifestacija> nadjiPoCeni(float cenaOd, float cenaDo){
		
		ArrayList<Manifestacija> validne = new ArrayList<Manifestacija>();
		for(Manifestacija m : manifestacije) {
			if(!m.isObrisana() && m.getCenaKarte()>= cenaOd && m.getCenaKarte()<=cenaDo) {
				validne.add(m);
			}
		}
		return validne;
	}
	
	public ArrayList<Manifestacija> nadjiPoLokaciji(String lokacija){
		ArrayList<Manifestacija> validne =new ArrayList<Manifestacija>();
		for(Manifestacija m : manifestacije) {
			if(!m.isObrisana() && m.getLokacija().getAdresa().getDrzava().contains(lokacija) || m.getLokacija().getAdresa().getMesto().contains(lokacija)) {
				validne.add(m);
			}
		}
		return validne;
	}
	
	public void obrisiManifestaciju(String naziv) {
		for(Manifestacija m : manifestacije) {
			if(m.getNaziv().equals(naziv)) {
				m.setObrisana(true);
			}
		}
	}
	
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
