package beans;


import java.time.LocalDateTime;

public class Manifestacija {
	private int id;
	private String naziv;
	private String tipManifestacije;
	private int brojMesta;
	private LocalDateTime datum;
	private float cenaKarte;
	private boolean status;
	private String slika;
	private Prodavac prodavac;
	private Lokacija lokacija;
	private boolean obrisana;
	
	public Manifestacija() {
		
	}
	
	
	public Manifestacija(int id,String naziv, String tipManifestacije, int brojMesta, LocalDateTime datum, float cenaKarte,
			boolean status, Prodavac prodavac, Lokacija lokacija, boolean obrisana, String slika) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.tipManifestacije = tipManifestacije;
		this.brojMesta = brojMesta;
		this.datum = datum;
		this.cenaKarte = cenaKarte;
		this.status = status;
		this.slika = slika;
		this.prodavac = prodavac;
		this.lokacija = lokacija;
		this.obrisana = obrisana;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getTipManifestacije() {
		return tipManifestacije;
	}
	public void setTipManifestacije(String tipManifestacije) {
		this.tipManifestacije = tipManifestacije;
	}
	public int getBrojMesta() {
		return brojMesta;
	}
	public void setBrojMesta(int brojMesta) {
		this.brojMesta = brojMesta;
	}
	public LocalDateTime getDatum() {
		return datum;
	}
	public void setDatum(LocalDateTime datum) {
		this.datum = datum;
	}
	public float getCenaKarte() {
		return cenaKarte;
	}
	public void setCenaKarte(float cenaKarte) {
		this.cenaKarte = cenaKarte;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String getSlika() {
		return slika;
	}
	public void setSlika(String slika) {
		this.slika = slika;
	} 
	public Prodavac getProdavac() {
		return prodavac;
	}

	public void setProdavac(Prodavac prodavac) {
		this.prodavac = prodavac;
	}
	
	public Lokacija getLokacija() {
		return lokacija;
	}


	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}


	public boolean isObrisana() {
		return obrisana;
	}


	public void setObrisana(boolean obrisana) {
		this.obrisana = obrisana;
	}

	
}
