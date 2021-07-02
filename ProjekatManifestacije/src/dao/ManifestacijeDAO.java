package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Karta;
import beans.Lokacija;
import beans.Manifestacija;
import beans.TipManifestacije;


public class ManifestacijeDAO {
	private HashMap<Integer,Manifestacija> manifestacije;
	private LokacijaDAO dao = new LokacijaDAO();
	
	public ManifestacijeDAO() {
		try {
			this.ucitajManifestacije();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<Integer,Manifestacija> getManifestacije(){
		return manifestacije;
	}
	
	public void setManifestacije(HashMap<Integer,Manifestacija> manifestacije) {
		this.manifestacije = manifestacije; 
	}
	
	public ArrayList<Manifestacija> sveValidne(){
		ArrayList<Manifestacija> validne = new ArrayList<Manifestacija>();
		for (Map.Entry<Integer, Manifestacija> entry : manifestacije.entrySet()) {
	        if(!entry.getValue().isObrisana()) {
	        	validne.add(entry.getValue());
	        }
	    }		
		return validne;
	}
	/*public ArrayList<Manifestacija> nadjiPoDatumu(LocalDateTime odDatum, LocalDateTime doDatum){
	public ArrayList<Manifestacija> nadjiPoDatumu(Date odDatum, Date doDatum){

		ArrayList<Manifestacija> validne = new ArrayList<Manifestacija>();
		for (Map.Entry<Integer, Manifestacija> entry : manifestacije.entrySet()) {
	        if(!entry.getValue().isObrisana() && entry.getValue().getDatum().after(odDatum) &&  entry.getValue().getDatum().before(doDatum) ) {
	        	validne.add(entry.getValue());
	        }
	    }		
		return validne;
	}*/
	
	public ArrayList<Manifestacija> nadjiManifestacije(String naziv) {
		ArrayList<Manifestacija> validne = new ArrayList<Manifestacija>();
		for (Map.Entry<Integer, Manifestacija> entry : manifestacije.entrySet()) {
	        if(!entry.getValue().isObrisana() && entry.getValue().getNaziv().equalsIgnoreCase(naziv)) {
	        	validne.add(entry.getValue());
	        }
	    }		
		return validne;
	}
	public ArrayList<Manifestacija> nadjiPoCeni(float cenaOd, float cenaDo){
		
		ArrayList<Manifestacija> validne = new ArrayList<Manifestacija>();
		for (Map.Entry<Integer, Manifestacija> entry : manifestacije.entrySet()) {
	        if(!entry.getValue().isObrisana() && entry.getValue().getCenaKarte()>= cenaOd &&entry.getValue().getCenaKarte()<=cenaDo) {
	        	validne.add(entry.getValue());
	        }
	    }	
		return validne;
	}
	
	/*public ArrayList<Manifestacija> nadjiPoLokaciji(Lokacija lokacija){
		ArrayList<Manifestacija> validne =new ArrayList<Manifestacija>();
		for (Map.Entry<Integer, Manifestacija> entry : manifestacije.entrySet()) {
	        if(!entry.getValue().isObrisana() && entry.getValue().getLokacija() == lokacija.getId() ) {
	        	validne.add(entry.getValue());
	        }
	    }		
		return validne;
	}
	*/
	public Manifestacija nadjiManifestaciju(int id){

		for (Map.Entry<Integer, Manifestacija> entry : manifestacije.entrySet()) {
	        if( entry.getValue().getId() == id) {
	        	return entry.getValue();
	        }
	    }		
		return null;
	}
	public void obrisiManifestaciju(int id) {
		for (Map.Entry<Integer, Manifestacija> entry : manifestacije.entrySet()) {
	        if(entry.getValue().getId() == id) {
	        	entry.getValue().setObrisana(true);
	        }
	    }		
		try {
			upisiManifestacije();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void postaviAktivnost(int id) {
		for (Map.Entry<Integer, Manifestacija> entry : manifestacije.entrySet()) {
	        if(entry.getValue().getId() == id) {
	        	entry.getValue().setAktivnost(true);
	        }
	    }		
		try {
			upisiManifestacije();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void upisiManifestacije() throws IOException{
		Gson gson = new Gson();
		FileWriter fw = new FileWriter("files/manifestacije.json");
		gson.toJson(this.manifestacije, fw);
		fw.flush();
		fw.close();
	}
	
	public void ucitajManifestacije() throws FileNotFoundException {
		
		Gson gson = new Gson();
		Type token = new TypeToken<HashMap<Integer,Manifestacija>>(){}.getType();
		BufferedReader br = new BufferedReader(new FileReader("files/manifestacije.json"));
		this.manifestacije = gson.fromJson(br, token);
	}
	
	public ArrayList<Manifestacija> pretraga(HashMap<Integer,Manifestacija> pretraziManifestacije,String filterAvaiable, String filterType, String searchName, String searchLocation, String searchPriceFrom, String searchPriceTo, String searchDateFrom, String searchDateTo) throws ParseException{
		ArrayList<Manifestacija> validne = new ArrayList<Manifestacija>();
		Date dateFrom = new Date();
		Date dateTo = new Date();
		
		//Proveramo datum pocetka
		if(searchDateFrom.equals(""))
		{
			dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse("1960-01-12");  		
		}
		else
		{
			dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(searchDateFrom);
		}
		//Proveravamo datum kraja perioda
		if(searchDateTo.equals(""))
		{
			dateTo = new SimpleDateFormat("yyyy-MM-dd").parse("2030-12-30");  		
		}
		else
		{
			dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(searchDateTo);  		

		}
		
		float priceFrom = 0;
		if(!searchPriceFrom.equals(""))
		{
			priceFrom = Float.parseFloat(searchPriceFrom);
		}
		
		float priceTo = 1999999;
		if(!searchPriceTo.equals(""))
		{
			priceTo = Float.parseFloat(searchPriceTo);
		}
		
		//System.out.println(filterAvaiable+ " " + filterType + " " + dateFrom + " " + dateTo + " " + searchName + " " + searchLocation + " "+ priceFrom + " " + priceTo + " ");

		for(Manifestacija m : pretraziManifestacije.values()) 
		{
			if(!m.isObrisana()) 
			{
				if((m.getNaziv()).toLowerCase().contains(searchName.toLowerCase()) || m.getDatum().before(dateTo) && m.getDatum().after(dateFrom) && m.getCenaKarte() >= priceFrom && m.getCenaKarte() <= priceTo) 
				{
					if(!searchLocation.equals("")) 
					{
						Lokacija lokacija = dao.getLokacije().get(Integer.parseInt(searchLocation));
						if(lokacija.getAdresa().getDrzava().toLowerCase().contains(searchLocation.toLowerCase()) || lokacija.getAdresa().getMesto().toLowerCase().contains(searchLocation.toLowerCase())) 
						{
							if(filterAvaiable.equalsIgnoreCase("true")) {
								if((m.getBrojMesta() - m.getSlobodnaMesta()) != 0) {
									if(filterType.equalsIgnoreCase("KONCERT")) 
									{
										if(m.getTipManifestacije() == TipManifestacije.KONCERT)
										{
											validne.add(m);
										}
									}
									else if(filterAvaiable.equalsIgnoreCase("POZORISTE")) 
									{
										if(m.getTipManifestacije() == TipManifestacije.POZORISTE)
										{
											validne.add(m);
										}
									}
									else if(filterType.equalsIgnoreCase("FESTIVAL")) 
									{
										if(m.getTipManifestacije() == TipManifestacije.FESTIVAL)
										{	
											validne.add(m);
										}	
									}
									else {
										validne.add(m);
									}
								}
							}
							else 
							{
								if(filterType.equalsIgnoreCase("KONCERT")) 
								{
									if(m.getTipManifestacije() == TipManifestacije.KONCERT)
									{
										validne.add(m);
									}
								}
								else if(filterAvaiable.equalsIgnoreCase("POZORISTE")) 
								{
									if(m.getTipManifestacije() == TipManifestacije.POZORISTE)
									{
										validne.add(m);
									}
								}
								else if(filterType.equalsIgnoreCase("FESTIVAL")) 
								{
									if(m.getTipManifestacije() == TipManifestacije.FESTIVAL)
									{	
										validne.add(m);
									}	
								}
								else 
								{
									validne.add(m);
								}
							}
						}
					}
					else{
						if(filterAvaiable.equalsIgnoreCase("true")) {
							if((m.getBrojMesta() - m.getSlobodnaMesta()) != 0) {
								if(filterType.equalsIgnoreCase("KONCERT")) 
								{
									if(m.getTipManifestacije() == TipManifestacije.KONCERT)
									{
										validne.add(m);
									}
								}
								else if(filterAvaiable.equalsIgnoreCase("POZORISTE")) 
								{
									if(m.getTipManifestacije() == TipManifestacije.POZORISTE)
									{
										validne.add(m);
									}
								}
								else if(filterType.equalsIgnoreCase("FESTIVAL")) 
								{
									if(m.getTipManifestacije() == TipManifestacije.FESTIVAL)
									{	
										validne.add(m);
									}	
								}
								else {
									validne.add(m);
								}
							}
						}
						else 
						{
							if(filterType.equalsIgnoreCase("KONCERT")) 
							{
								if(m.getTipManifestacije() == TipManifestacije.KONCERT)
								{
									validne.add(m);
								}
							}
							else if(filterAvaiable.equalsIgnoreCase("POZORISTE")) 
							{
								if(m.getTipManifestacije() == TipManifestacije.POZORISTE)
								{
									validne.add(m);
								}
							}
							else if(filterType.equalsIgnoreCase("FESTIVAL")) 
							{
								if(m.getTipManifestacije() == TipManifestacije.FESTIVAL)
								{	
									validne.add(m);
								}	
							}
							else 
							{
								validne.add(m);
							}
						}
					}
				}
			}
		}
		return validne;
	}

	
	public ArrayList<Karta> nadjiPoDatumima(ArrayList<Karta> tempKarte, String pretragaDatumOd,String pretragaDatumDo, String naziv){
		Date datumOd = new Date();
		try {
			datumOd = new SimpleDateFormat("yyyy-MM-dd").parse(pretragaDatumOd);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date datumDo = new Date();
		try {
			datumDo = new SimpleDateFormat("yyyy-MM-dd").parse(pretragaDatumDo);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Karta> izabrane = new ArrayList<Karta>();
		for(Karta k : tempKarte) {
			for(Manifestacija m : manifestacije.values()) {
				if(naziv.equals("")) {
					if(k.getManifestacija() == m.getId() && m.getDatum().before(datumDo) && m.getDatum().after(datumOd)){
						izabrane.add(k);
					}
				}else {
					if(k.getManifestacija() == m.getId() && m.getDatum().before(datumDo) && m.getDatum().after(datumOd) && m.getNaziv().contains(naziv) ) {
					izabrane.add(k);
					}
			}}
		}
		
		
		
		return izabrane;
	}
	public ArrayList<Karta> nadjiPoNazivu(ArrayList<Karta> tempKarte, String naziv){
		ArrayList<Karta> izabrane = new ArrayList<Karta>();
		for(Karta k : tempKarte) {
			for(Manifestacija m : manifestacije.values()) {
				if(naziv.equals("")) {
					return izabrane;
				}else {
					if(k.getManifestacija() == m.getId() && m.getNaziv().contains(naziv) ) {
						izabrane.add(k);
					}
				}
			}
		}
		
		
		
		return izabrane;
	}
	
	public void  promeniBrojMesta(Manifestacija manifestacija){
		
			for(Manifestacija m : manifestacije.values()) {
				if(m.getId() == manifestacija.getId()) {
					int brojMesta = m.getSlobodnaMesta();
					m.setSlobodnaMesta(brojMesta + 1);
					
				}
			}
		try {
			upisiManifestacije();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	public HashMap<Integer,Manifestacija> nadjiManifestacijeId(ArrayList<Integer> man) {
		HashMap<Integer,Manifestacija> izabrane = new HashMap<Integer,Manifestacija>();
		for(Manifestacija m : manifestacije.values()) {
			for(int i = 0; i< man.size();i++) {
				if(man.get(i) == m.getId()) {
					izabrane.put(m.getId(),m);
				}
			}
		}
		
		
		return izabrane;
	}

	public int nadjiSledeciId() {
		int maxValueKey = Collections.max(this.manifestacije.keySet());
		return maxValueKey + 1;
	}

	@SuppressWarnings("deprecation")
	public boolean checkAvaiability(Manifestacija manifestacija) {
		for(Manifestacija m : this.manifestacije.values()) {
			if(m.getDatum().getDate() == manifestacija.getDatum().getDate() && m.getDatum().getMonth() == manifestacija.getDatum().getMonth()) {
				return false;
			}
		}
		return false;
	}
	
	public void  smanjiBrojMesta(int kolicina, Manifestacija manifestacija){
		
		for(Manifestacija m : manifestacije.values()) {
			if(m.getId() == manifestacija.getId()) {
				int brojMesta = m.getSlobodnaMesta();
				m.setSlobodnaMesta(brojMesta-kolicina);
				
			}
		}
	try {
		upisiManifestacije();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

}
