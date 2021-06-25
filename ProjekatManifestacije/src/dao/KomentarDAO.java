package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Komentar;
import beans.Kupac;

public class KomentarDAO {
	private ArrayList<Komentar> komentari;
	private HashMap<Integer, Komentar> mapaKomentara;
	
	public KomentarDAO() {
		komentari = new ArrayList<Komentar>();
		mapaKomentara = new HashMap<Integer, Komentar>();
		try {
			ucitajKomentare();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public HashMap<Integer,Komentar> getMapaKomentara() {
		return mapaKomentara;
	}

	public void setKomentari(HashMap<Integer, Komentar> komentari) {
		this.mapaKomentara = komentari;
	}
	
	public ArrayList<Komentar> validniKomentari(){
		ArrayList<Komentar> validni = new ArrayList<Komentar>();
		for(Komentar k : mapaKomentara.values()) {
			if(!k.isObrisan()) {
				validni.add(k);
			}
		}
		return validni;
	}
	
	public ArrayList<Komentar> potrebniKomentari(boolean odobreni){
		ArrayList<Komentar> validni = new ArrayList<Komentar>();
		for(Komentar k : mapaKomentara.values()) {
			if(!k.isObrisan() && k.isOdobrena() == odobreni) {
				validni.add(k);
			}
		}
		return validni;
	}
	
	public ArrayList<Komentar> komentariManifestacije(int id){
		ArrayList<Komentar> validni = new ArrayList<Komentar>();
		for(Komentar k : this.mapaKomentara.values()) {
			if(!k.isObrisan() && k.getManifestacija() == id) {
				validni.add(k);
			}
		}
		return validni;
		
	}
	
	public ArrayList<Komentar> komentariKupca(String korisnickoIme){
		ArrayList<Komentar> validni = new ArrayList<Komentar>();
		for(Komentar k : this.mapaKomentara.values()) {
			if(!k.isObrisan() && k.getKupac().equalsIgnoreCase(korisnickoIme)) {
				validni.add(k);
			}
		}
		return validni;
	}
	
	public void obrisiKomentar(int id) {
		
		for(Komentar k : this.mapaKomentara.values()) {
			if(k.getId()==id) {
				k.setObrisan(true);
				return;
			}
		}
		
	}
	
	public void odobriKomentar(int id) {
		for(Komentar k : this.mapaKomentara.values()) {
			if(k.getId()==id) {
				k.setOdobrena(true);
				return;
			}
		}
	}
	
	public void upisiKomentare() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("files/komentari.json");
		gson.toJson(this.mapaKomentara, fw);
		fw.flush();
		fw.close();
	}
	
	public void ucitajKomentare() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<HashMap<Integer, Komentar>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("files/komentari.json"));
		this.mapaKomentara = gson.fromJson(br, token);
	}
	
}
