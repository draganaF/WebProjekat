package beans;
import java.util.Date;

import javax.json.bind.annotation.JsonbDateFormat;

public class Manifestacija {
    private int id;
    private String naziv;
    private TipManifestacije tipManifestacije;
    private int brojMesta;
    private int slobodnaMesta;
    @JsonbDateFormat(JsonbDateFormat.TIME_IN_MILLIS)
    private Date datum;
    private float cenaKarte;
    private boolean status;
    private String slika;
    private String prodavac;
    private int lokacija;
    private boolean obrisana;
    private boolean aktivnost;
    
    public Manifestacija() {}
    public Manifestacija(int id,String naziv, TipManifestacije tipManifestacije, int brojMesta, Date datum, float cenaKarte,
            boolean status, String prodavac, int lokacija, boolean obrisana, String slika,boolean aktivnost) {
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
        this.aktivnost = aktivnost;
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
    public TipManifestacije getTipManifestacije() {
        return tipManifestacije;
    }
    public void setTipManifestacije(TipManifestacije tipManifestacije) {
        this.tipManifestacije = tipManifestacije;
    }
    public int getBrojMesta() {
        return brojMesta;
    }
    public void setBrojMesta(int brojMesta) {
        this.brojMesta = brojMesta;
    }
    public Date getDatum() {
        return datum;
    }
    public void setDatum(Date datum) {
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
    public String getProdavac() {
        return prodavac;
    }

    public void setProdavac(String prodavac) {
        this.prodavac = prodavac;
    }
    
    public int getLokacija() {
        return lokacija;
    }


    public void setLokacija(int lokacija) {
        this.lokacija = lokacija;
    }


    public boolean isObrisana() {
        return obrisana;
    }


    public void setObrisana(boolean obrisana) {
        this.obrisana = obrisana;
    }


    public boolean isAktivnost() {
        return aktivnost;
    }


    public void setAktivnost(boolean aktivnost) {
        this.aktivnost = aktivnost;
    }
    
    public int getSlobodnaMesta() {
        return slobodnaMesta;
    }
    
    public void setSlobodnaMesta(int slobodnaMesta) {
        this.slobodnaMesta = slobodnaMesta;
    }
    

    
}