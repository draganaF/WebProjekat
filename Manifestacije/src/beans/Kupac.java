package beans;

public class Kupac extends Korisnik {
	//private ArrayList<Karta> karte;
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
	
	
	

}
