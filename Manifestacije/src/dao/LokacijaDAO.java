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

import beans.Adresa;
import beans.Lokacija;

public class LokacijaDAO {
	private ArrayList<Lokacija> lokacije;
	
	public LokacijaDAO() {}

	public ArrayList<Lokacija> getLokacije() {
		return lokacije;
	}

	public void setLokacije(ArrayList<Lokacija> lokacije) {
		this.lokacije = lokacije;
	}
	
	public Lokacija nadjiLokaciju(Adresa adresa) {
		for(Lokacija lokacija:lokacije) {
			if(lokacija.getAdresa().equals(adresa)) {
				return lokacija;
			}
		}
		return null;
	}
	public void obrisiLokaciju(Adresa adresa) {
		for(Lokacija lokacija:lokacije) {
			if(lokacija.getAdresa().equals(adresa)) {
				lokacija.setObrisana(true);
				break;
			}
		}
	}
	public void upisiLokacije() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("lokacije.json");
		gson.toJson(this.lokacije, fw);
		fw.flush();
		fw.close();
	}
	
	public void ucitajLokacije() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<ArrayList<Lokacija>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("lokacije.json"));
		this.lokacije = gson.fromJson(br, token);
	}

}
