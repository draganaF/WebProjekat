package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Karta;
import beans.TipKarte;
import sorter.KartaSorter;

public class KartaDAO {
	private ArrayList<Karta> karte;
	
	public KartaDAO() {
		try {
			this.ucitajKarte();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Karta> getKarte() {
		return karte;
	}

	public void setKarte(ArrayList<Karta> karte) {
		this.karte = karte;
	}
	
	public ArrayList<Karta> validneKarte(){
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana()) {
				validne.add(k);
			}
		}
		
		return validne;
	}
	
	public ArrayList<Karta> karteKupca(String korisnickoIme){
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana() && k.getKupac().equalsIgnoreCase(korisnickoIme)) {
				validne.add(k);
			}
		}
		
		return validne;
	}
	
	public ArrayList<Karta> karteKupcaSumnjivi(String korisnickoIme){
		ArrayList<Karta> validne = karteKupca(korisnickoIme);
		ArrayList<Karta> karteUMesecDana = new ArrayList<Karta>();
		for(Karta k : validne) {
			// status odustanak
			if(!k.isStatus()) {
				
				karteUMesecDana.add(k);
			}
			
		}
		
		if(karteUMesecDana.size() == 5) {
			
			Collections.sort(karteUMesecDana, new KartaSorter());
			long value = karteUMesecDana.get(karteUMesecDana.size()-1).getDatumOtkazivanja().getTime() -  karteUMesecDana.get(0).getDatumOtkazivanja().getTime();
			int days = (int) (value / (1000*60*60*24));
			if(days <= 31 || days <= 30) {
				return karteUMesecDana;
			}
		}
		
		return null;
		
	}
	
	/*public ArrayList<Karta> kartePoslovca(String korisnickoIme){
		
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
<<<<<<< HEAD
			if(!k.isObrisana() && k.getManifestacija()(korisnickoIme)) {
=======
			if(!k.isObrisana() && k.getManifestacija().getProdavac().equalsIgnoreCase(korisnickoIme)) {
>>>>>>> b0aed92daf20727a6c2f6ba9e555dbe4b54d8196
				validne.add(k);
			}
		}
		
		return validne;
	}*/
	public ArrayList<Karta> karteManifestacije(int idM){
		
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana() && k.getManifestacija() ==idM) {
				validne.add(k);
			}
		}
		
		return validne;
	}
	
	public Karta nadjiKartu(String id) {
		for(Karta k : karte) {
			if(k.getId().equalsIgnoreCase(id)) {
				return k;
			}
		}
		return null;
	}
	
	public void obrisiKartu(String id) {
		for(Karta k : karte) {
			if(k.getId().equalsIgnoreCase(id)) {
				k.setObrisana(true);
				break;
			}
		}
	}
	public ArrayList<Karta> nadjiPoCeni(int odCena, int doCena){
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana() && k.getCena() >= odCena && k.getCena()<= doCena) {
				validne.add(k);
			}
		}
		return validne;
	}
	public ArrayList<Karta> nadjiPoStatusu(boolean status){
		ArrayList<Karta> validne = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana() && k.isStatus()==status) {
				validne.add(k);
			}
		}
		return validne;
	}
	
	public ArrayList<Karta> nadjiPoTipu(TipKarte tip){
		ArrayList<Karta> karte = new ArrayList<Karta>();
		for(Karta k : karte) {
			if(!k.isObrisana() && k.getTipKarte().equals(tip)) {
				karte.add(k);
			}
		}
		return karte;
	}

	public void upisiKarte() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("files/karte.json");
		gson.toJson(this.karte, fw);
		fw.flush();
		fw.close();
	}
	
	public void ucitajKarte() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<ArrayList<Karta>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("files/karte.json"));
		this.karte = gson.fromJson(br, token);
	}
}
