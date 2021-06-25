package beans;

import java.util.ArrayList;

public class Prodavac extends Korisnik {
	private ArrayList<Integer> manifestacije;
	
	public Prodavac() {}

	public ArrayList<Integer> getManifestacije() {
		return manifestacije;
	}

	public void setManifestacije(ArrayList<Integer> manifestacije) {
		this.manifestacije = manifestacije;
	}
	
}
