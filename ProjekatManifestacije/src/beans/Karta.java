package beans;

import java.time.LocalDateTime;
import java.util.Date;

import javax.json.bind.annotation.JsonbDateFormat;

public class Karta {

	private String id;
	private int manifestacija;
	private float cena;
	private boolean status;
	private TipKarte tipKarte;
	@JsonbDateFormat(JsonbDateFormat.TIME_IN_MILLIS)
	private Date datumOtkazivanja;
	private String kupac;
	private boolean obrisana;
	
	public Karta() {
		
	}

	public Karta(String id, int manifestacija, float cena, boolean status, TipKarte tipKarte,
			Date datumOtkazivanja, String kupac, boolean obrisana) {
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

	public int getManifestacija() {
		return manifestacija;
	}

	public void setManifestacija(int manifestacija) {
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

	public TipKarte getTipKarte() {
		return tipKarte;
	}

	public void setTipKarte(TipKarte tipKarte) {
		this.tipKarte = tipKarte;
	}

	public Date getDatumOtkazivanja() {
		return datumOtkazivanja;
	}

	public void setDatumOtkazivanja(Date datumOtkazivanja) {
		this.datumOtkazivanja = datumOtkazivanja;
	}

	public String getKupac() {
		return kupac;
	}

	public void setKupac(String kupac) {
		this.kupac = kupac;
	}

	public boolean isObrisana() {
		return obrisana;
	}

	public void setObrisana(boolean obrisana) {
		this.obrisana = obrisana;
	}
	
}
