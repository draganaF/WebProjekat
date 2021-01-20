package dao;

import java.util.ArrayList;

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
}
