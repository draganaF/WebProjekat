package beans;

import java.time.LocalDateTime;

public class Karta {

	private String id;
	private Manifestacija manifestacija;
	private float cena;
	private boolean status;
	private String tipKarte;
	private LocalDateTime datumOtkazivanja;
	private Kupac kupac;
	private boolean obrisana;
	
	public Karta() {
		
	}

	public Karta(String id, Manifestacija manifestacija, float cena, boolean status, String tipKarte,
			LocalDateTime datumOtkazivanja, Kupac kupac, boolean obrisana) {
		super();
		this.id = id;
		this.manifestacija = manifestacija;
		this.cena = cena;
		this.status = status;
		this.tipKarte = tipKarte;
		this.datumOtkazivanja = datumOtkazivanja;
		this.kupac = kupac;
		this.obrisana= obrisana;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Manifestacija getManifestacija() {
		return manifestacija;
	}

	public void setManifestacija(Manifestacija manifestacija) {
		this.manifestacija = manifestacija;
	}

	public float getCena() {
		return cena;
	}

	public void setCena(float cena) {
		this.cena = cena;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getTipKarte() {
		return tipKarte;
	}

	public void setTipKarte(String tipKarte) {
		this.tipKarte = tipKarte;
	}

	public LocalDateTime getDatumOtkazivanja() {
		return datumOtkazivanja;
	}

	public void setDatumOtkazivanja(LocalDateTime datumOtkazivanja) {
		this.datumOtkazivanja = datumOtkazivanja;
	}

	public Kupac getKupac() {
		return kupac;
	}

	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}

	public boolean isObrisana() {
		return obrisana;
	}

	public void setObrisana(boolean obrisana) {
		this.obrisana = obrisana;
	}
	
}
