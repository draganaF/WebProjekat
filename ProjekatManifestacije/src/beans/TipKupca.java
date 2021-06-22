package beans;

public class TipKupca {

	private Tip tip;
	private float popust;
	private int trazeniBrojBodova;
	
	public TipKupca() {
		
	}
	public TipKupca(Tip tip, float popust, int trazeniBrojBodova) {
		super();
		this.tip = tip;
		this.popust = popust;
		this.trazeniBrojBodova = trazeniBrojBodova;
	}
	public Tip getImeTipa() {
		return tip;
	}
	public void setImeTipa(Tip tip) {
		this.tip = tip;
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
