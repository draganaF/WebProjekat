package beans;

public class Komentar {

	private int id;
	private String kupac;
	private int manifestacija;
	private String tekst;
	private int ocena;
	private boolean odobrena;
	private boolean obrisan;
	
	public Komentar() {
		
	}

	public Komentar(int id, String kupac, int manifestacija, String teskt, int ocena, boolean odobrena,
			boolean obrisan) {
		super();
		this.id = id;
		this.kupac = kupac;
		this.manifestacija = manifestacija;
		this.tekst = teskt;
		this.ocena = ocena;
		this.odobrena = odobrena;
		this.obrisan = obrisan;
	}

	public String getKupac() {
		return kupac;
	}

	public void setKupac(String kupac) {
		this.kupac = kupac;
	}

	public int getManifestacija() {
		return this.manifestacija;
	}

	public void setManifestacija(int manifestacija) {
		this.manifestacija = manifestacija;
	}

	public String getTeskt() {
		return tekst;
	}

	public void setTeskt(String teskt) {
		this.tekst = teskt;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public boolean isOdobrena() {
		return odobrena;
	}

	public void setOdobrena(boolean odobrena) {
		this.odobrena = odobrena;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
