package beans;

import java.util.ArrayList;

public class Prodavac extends Korisnik {
	private ArrayList<Manifestacija> manifestacije;
	private boolean blokiran;
	
	public Prodavac() {}

	public ArrayList<Manifestacija> getManifestacije() {
		return manifestacije;
	}

	public void setManifestacije(ArrayList<Manifestacija> manifestacije) {
		this.manifestacije = manifestacije;
	}

	public boolean isBlokiran() {
		return blokiran;
	}

	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}
	
	
	
}
