package dao;

import java.util.ArrayList;

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
	
}
