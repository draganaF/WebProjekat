package beans;

public class Komentar {

	private int id;
	private Kupac kupac;
	private Manifestacija manifestacija;
	private String teskt;
	private int ocena;
	private boolean odobrena;
	private boolean obrisan;
	
	public Komentar() {
		
	}

	public Komentar(int id, Kupac kupac, Manifestacija manifestacija, String teskt, int ocena, boolean odobrena,
			boolean obrisan) {
		super();
		this.id = id;
		this.kupac = kupac;
		this.manifestacija = manifestacija;
		this.teskt = teskt;
		this.ocena = ocena;
		this.odobrena = odobrena;
		this.obrisan = obrisan;
	}

	public Kupac getKupac() {
		return kupac;
	}

	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}

	public Manifestacija getManifestacija() {
		return this.manifestacija;
	}

	public void setManifestacija(Manifestacija manifestacija) {
		this.manifestacija = manifestacija;
	}

	public String getTeskt() {
		return teskt;
	}

	public void setTeskt(String teskt) {
		this.teskt = teskt;
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
