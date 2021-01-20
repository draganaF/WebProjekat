package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import beans.Manifestacija;


public class ManifestacijeDAO {
	private ArrayList<Manifestacija> manifestacije;
	
	public ManifestacijeDAO() {

		//Napraviti JSON ucitavanje
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
	public void upisiManifestacije() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		mapper.writeValue(new File("manifestacije.json"), manifestacije);
	}
}
