package beans;

public class Lokacija {
	public int geografskaDuzina;
	public int geografskaSirina;
	public Adresa adresa;
	
	public Lokacija() {}

	public int getGeografskaDuzina() {
		return geografskaDuzina;
	}

	public void setGeografskaDuzina(int geografskaDuzina) {
		this.geografskaDuzina = geografskaDuzina;
	}

	public int getGeografskaSirina() {
		return geografskaSirina;
	}

	public void setGeografskaSirina(int geografskaSirina) {
		this.geografskaSirina = geografskaSirina;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}
	
}
