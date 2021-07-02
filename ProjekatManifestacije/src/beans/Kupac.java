package beans;

import java.util.ArrayList;

public class Kupac extends Korisnik {
	private ArrayList<String> karte;
	private int brojSakupljenihBodova;
	private String tipKupca;
	private boolean sumnjiv;
	private boolean blokiran;
	
	public Kupac() {}
	public int getBrojSakupljenihBodova() {
		return brojSakupljenihBodova;
	}
	public void setBrojSakupljenihBodova(int brojSakupljenihBodova) {
		this.brojSakupljenihBodova = brojSakupljenihBodova;
	}
	public String getTipKupca() {
		return tipKupca;
	}
	public void setTipKupca(String tipKupca) {
		this.tipKupca = tipKupca;
	}
	public ArrayList<String> getKarte() {
		return karte;
	}
	public void setKarte(ArrayList<String> karte) {
		this.karte = karte;
	}
	public boolean isSumnjiv() {
		return sumnjiv;
	}
	public void setSumnjiv(boolean sumnjiv) {
		this.sumnjiv = sumnjiv;
	}
	public boolean isBlokiran() {
		return blokiran;
	}
	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}
	
	
	
}
