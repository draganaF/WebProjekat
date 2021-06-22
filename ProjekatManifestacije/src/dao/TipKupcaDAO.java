package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Kupac;
import beans.TipKupca;

public class TipKupcaDAO {
	private HashMap<String,TipKupca> tipovi;
	
	public TipKupcaDAO() throws FileNotFoundException {
		ucitajTipove();
	}

	public HashMap<String,TipKupca> getTipove() {
		return tipovi;
	}

	public void setTipove(HashMap<String,TipKupca> tipovi) {
		this.tipovi = tipovi;
	}
	
	public void ucitajTipove() throws FileNotFoundException {	
		Gson gson = new Gson();
		Type token = new TypeToken<HashMap<String,Kupac>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("files/tipoviKupaca.json"));
		this.tipovi = gson.fromJson(br, token);
	}
	
	public void upisiTipovi() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("files/tipoviKupaca.json");
		gson.toJson(this.tipovi, fw);
		fw.flush();
		fw.close();
	}
	

}
