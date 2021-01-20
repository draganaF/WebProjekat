package beans;

public class TipKupca {

	private  String imeTipa;
	private float popust;
	private int trazeniBrojBodova;
	
	public TipKupca() {
		
	}
	public TipKupca(String imeTipa, float popust, int trazeniBrojBodova) {
		super();
		this.imeTipa = imeTipa;
		this.popust = popust;
		this.trazeniBrojBodova = trazeniBrojBodova;
	}
	public String getImeTipa() {
		return imeTipa;
	}
	public void setImeTipa(String imeTipa) {
		this.imeTipa = imeTipa;
	}
	public float getPopust() {
		return popust;
	}
	public void setPopust(float popust) {
		this.popust = popust;
	}
	public int getTrazeniBrojBodova() {
		return trazeniBrojBodova;
	}
	public void setTrazeniBrojBodova(int trazeniBrojBodova) {
		this.trazeniBrojBodova = trazeniBrojBodova;
	}
	
	
}
