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

import beans.Komentar;

public class KomentarDAO {
	private ArrayList<Komentar> komentari;

	public KomentarDAO() {
		super();
	}

	public ArrayList<Komentar> getKomentari() {
		return komentari;
	}

	public void setKomentari(ArrayList<Komentar> komentari) {
		this.komentari = komentari;
	}
	
	public ArrayList<Komentar> validniKomentari(){
		ArrayList<Komentar> validni = new ArrayList<Komentar>();
		for(Komentar k : komentari) {
			if(!k.isObrisan()) {
				validni.add(k);
			}
		}
		return validni;
	}
	
	public ArrayList<Komentar> potrebniKomentari(boolean odobreni){
		ArrayList<Komentar> validni = new ArrayList<Komentar>();
		for(Komentar k : komentari) {
			if(!k.isObrisan() && k.isOdobrena() == odobreni) {
				validni.add(k);
			}
		}
		return validni;
	}
	
	public ArrayList<Komentar> komentariManifestacije(String naziv){
		ArrayList<Komentar> validni = new ArrayList<Komentar>();
		for(Komentar k : komentari) {
			if(!k.isObrisan() && k.getManifestacija().getNaziv().equalsIgnoreCase(naziv)) {
				validni.add(k);
			}
		}
		return validni;
		
	}
	
	public ArrayList<Komentar> komentariKupca(String korisnickoIme){
		ArrayList<Komentar> validni = new ArrayList<Komentar>();
		for(Komentar k : komentari) {
			if(!k.isObrisan() && k.getKupac().getKorisnickoIme().equalsIgnoreCase(korisnickoIme)) {
				validni.add(k);
			}
		}
		return validni;
	}
	
	public void obrisiKomentar(int id) {
		
		for(Komentar k : komentari) {
			if(k.getId()==id) {
				k.setObrisan(true);
				return;
			}
		}
		
	}
	
	public void odobriKomentar(int id) {
		for(Komentar k : komentari) {
			if(k.getId()==id) {
				k.setOdobrena(true);
				return;
			}
		}
	}
	
	public void upisiKomentare() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("komentari.json");
		gson.toJson(this.komentari, fw);
		fw.flush();
		fw.close();
	}
	
	public void ucitajKomentare() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<ArrayList<Komentar>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("komentari.json"));
		this.komentari = gson.fromJson(br, token);
	}
	
}
