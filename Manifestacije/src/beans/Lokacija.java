package beans;

public class Lokacija {
	private int id;
	private int geografskaDuzina;
	private int geografskaSirina;
	private Adresa adresa;
	private boolean obrisana;
	
	public Lokacija() {}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public boolean isObrisana() {
		return obrisana;
	}

	public void setObrisana(boolean obrisana) {
		this.obrisana = obrisana;
	}	
}
