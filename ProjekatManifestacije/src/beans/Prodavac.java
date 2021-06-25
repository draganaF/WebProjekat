package beans;

import java.util.ArrayList;

public class Prodavac extends Korisnik {
	private boolean blokiran;

	private ArrayList<Integer> manifestacije;

	
	public Prodavac() {}

	public ArrayList<Integer> getManifestacije() {
		return manifestacije;
	}

	public void setManifestacije(ArrayList<Integer> manifestacije) {
		this.manifestacije = manifestacije;
	}

	public boolean isBlokiran() {
		return blokiran;
	}

	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}
	
	
	
}
