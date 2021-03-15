package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Administrator;

public class AdministratorDAO {
	private ArrayList<Administrator> administratori;
	
	public AdministratorDAO() throws FileNotFoundException 
	{
		ucitajAdministratore();
	}

	public ArrayList<Administrator> getAdministratori() {
		return administratori;
	}

	public void setAdministratori(ArrayList<Administrator> administratori) {
		this.administratori = administratori;
	}
	
	public Administrator nadjiAdministratora(String korisnickoIme) {
		for(Administrator administrator:administratori) {
			if(administrator.getKorisnickoIme().equals(korisnickoIme)) {
				return administrator;
			}
		}
		return null;
	}
	public void obrisiAdministratora(String korisnickoIme) {
		for(Administrator administrator:administratori) {
			if(administrator.getKorisnickoIme().equals(korisnickoIme)) {
				administrator.setObrisan(true);
				break;
			}
		}
	}
	public void upisiAdministratore() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("administratori.json");
		gson.toJson(this.administratori, fw);
		fw.flush();
		fw.close();
	}
	
	public void ucitajAdministratore() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<ArrayList<Administrator>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("administratori.json"));
		this.administratori = gson.fromJson(br, token);
	}

}
