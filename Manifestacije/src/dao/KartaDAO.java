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

public class KartaDAO {
	private ArrayList<Karta> karte;
	
	public KartaDAO() {
		
	}

	public ArrayList<Karta> getKarte() {
		return karte;
	}

	public void setKarte(ArrayList<Karta> karte) {
		this.karte = karte;
	}
	
	public ArrayList<Karta> validneKarte(){
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana()) {
				validne.add(k);
			}
		}
		
		return validne;
	}
	
	public ArrayList<Karta> karteKupca(String korisnickoIme){
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana() && k.getKupac().getKorisnickoIme().equalsIgnoreCase(korisnickoIme)) {
				validne.add(k);
			}
		}
		
		return validne;
	}
	
	public ArrayList<Karta> kartePoslovca(String korisnickoIme){
		
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana() && k.getManifestacija().getProdavac().getKorisnickoIme().equalsIgnoreCase(korisnickoIme)) {
				validne.add(k);
			}
		}
		
		return validne;
	}
	public ArrayList<Karta> karteManifestacije(int idM){
		
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana() && k.getManifestacija().getId()==idM) {
				validne.add(k);
			}
		}
		
		return validne;
	}
	
	public Karta nadjiKartu(String id) {
		for(Karta k : karte) {
			if(k.getId().equalsIgnoreCase(id)) {
				return k;
			}
		}
		return null;
	}
	
	public void obrisiKartu(String id) {
		for(Karta k : karte) {
			if(k.getId().equalsIgnoreCase(id)) {
				k.setObrisana(true);
				break;
			}
		}
	}
	
	public void upisiKarte() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("karte.json");
		gson.toJson(this.karte, fw);
		fw.flush();
		fw.close();
	}
	
	public void ucitajKarte() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<ArrayList<Karta>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("karte.json"));
		this.karte = gson.fromJson(br, token);
	}
}
