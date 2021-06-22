package rest;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Kupac;
import dao.KupacDAO;

public class ProjekatManifestacijeMain  {

	private static Gson g = new Gson();
	private static KupacDAO kupacDAO = new KupacDAO();
	
	
	public static void main(String[] args) throws IOException {
		port(8080);
		
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		post("/registration", (req, res)-> {
			String reqBody = req.body();
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			
			Kupac kupac = gsonReg.fromJson(reqBody, Kupac.class);
			kupacDAO.dodajKupca(kupac);
			return true;
			
		});
		
		
		

	}

}
