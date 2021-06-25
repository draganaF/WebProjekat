package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Adresa;
import beans.Lokacija;
import beans.Prodavac;

public class LokacijaDAO {
	private HashMap<Integer,Lokacija> lokacije;
	
	public LokacijaDAO() {
		lokacije = new HashMap<Integer,Lokacija>();
		
		try {
			ucitajLokacije();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<Integer,Lokacija> getLokacije() {
		return lokacije;
	}

	public void setLokacije(HashMap<Integer,Lokacija> lokacije) {
		this.lokacije = lokacije;
	}
	
	public Lokacija nadjiLokaciju(Adresa adresa) {
		for (Map.Entry<Integer, Lokacija> entry : lokacije.entrySet()) {
	        if(entry.getValue().getAdresa().equals(adresa)){
	        	return entry.getValue();
	        }
	    }	
		return null;
	}
	public void obrisiLokaciju(Adresa adresa) {
		for (Map.Entry<Integer, Lokacija> entry : lokacije.entrySet()) {
	        if(entry.getValue().getAdresa().equals(adresa)){
	        	entry.getValue().setObrisana(true);
	        }
	    }	
	}
	public void upisiLokacije() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("files/lokacije.json");
		gson.toJson(this.lokacije, fw);
		fw.flush();
		fw.close();
	}
	
	public void ucitajLokacije() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<HashMap<Integer,Lokacija>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("files/lokacije.json"));
		this.lokacije = gson.fromJson(br, token);
		
		System.out.println(lokacije.get(1).getAdresa().getDrzava());
	}

}
