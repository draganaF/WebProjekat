package beans;

import java.util.ArrayList;

public class Kupac extends Korisnik {
	private ArrayList<Karta> karte;
	private int brojSakupljenihBodova;
	private TipKupca tipKupca;
	
	public Kupac() {}
	public int getBrojSakupljenihBodova() {
		return brojSakupljenihBodova;
	}
	public void setBrojSakupljenihBodova(int brojSakupljenihBodova) {
		this.brojSakupljenihBodova = brojSakupljenihBodova;
	}
	public TipKupca getTipKupca() {
		return tipKupca;
	}
	public void setTipKupca(TipKupca tipKupca) {
		this.tipKupca = tipKupca;
	}
	public ArrayList<Karta> getKarte() {
		return karte;
	}
	public void setKarte(ArrayList<Karta> karte) {
		this.karte = karte;
	}
	
	
	
	

}
