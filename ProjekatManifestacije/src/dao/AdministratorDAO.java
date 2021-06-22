package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Administrator;
import beans.Prodavac;

public class AdministratorDAO {
	private HashMap<String,Administrator> administratori;
	
	public AdministratorDAO()
	{
		administratori = new HashMap<String,Administrator>();
		
		try {
			ucitajAdministratore();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String,Administrator> getAdministratori() {
		return administratori;
	}

	public void setAdministratori(HashMap<String,Administrator> administratori) {
		this.administratori = administratori;
	}
	
	public Administrator nadjiAdministratora(String korisnickoIme, String lozinka) {
		for (Map.Entry<String, Administrator> entry : administratori.entrySet()) {
	        if(entry.getValue().getKorisnickoIme().equals(korisnickoIme) && entry.getValue().getLozinka().equals(lozinka)) {
	        	return entry.getValue();
	        }
	    }	
		return null;
	}
	public void obrisiAdministratora(String korisnickoIme) {
		for (Map.Entry<String, Administrator> entry : administratori.entrySet()) {
	        if(entry.getValue().getKorisnickoIme().equals(korisnickoIme)) {
	        	entry.getValue().setObrisan(true);
	        }
	    }		
		
	}
	public void upisiAdministratore() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("files/administratori.json");
		gson.toJson(this.administratori, fw);
		fw.flush();
		fw.close();
	}
	
	public void ucitajAdministratore() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<HashMap<String,Administrator>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("files/administratori.json"));
		this.administratori = gson.fromJson(br, token);
	}

}
