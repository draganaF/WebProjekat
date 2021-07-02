package beans;

public class ManifestacijaDAO {

	private Manifestacija manifestacija;
	private Lokacija lokacija;
	
	public ManifestacijaDAO() {
		
	}
	
	public ManifestacijaDAO(Manifestacija manifestacija, Lokacija lokacija) {
		this.manifestacija = manifestacija;
		this.lokacija = lokacija;
	}
	
	public Manifestacija getManifestacija() {
		return manifestacija;
	}
	public void setManifestacija(Manifestacija manifestacija) {
		this.manifestacija = manifestacija;
	}
	public Lokacija getLokacija() {
		return lokacija;
	}
	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}
	
	
}
