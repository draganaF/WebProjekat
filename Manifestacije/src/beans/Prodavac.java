package beans;

import java.util.ArrayList;

public class Prodavac extends Korisnik {
	private ArrayList<Manifestacija> manifestacije;
	
	public Prodavac() {}

	public ArrayList<Manifestacija> getManifestacije() {
		return manifestacije;
	}

	public void setManifestacije(ArrayList<Manifestacija> manifestacije) {
		this.manifestacije = manifestacije;
	}
	
}
