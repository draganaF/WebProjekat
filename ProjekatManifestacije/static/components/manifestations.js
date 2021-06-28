Vue.component("manifestations", {
  name: "manifestations",
  data: function() {
    return {
      manifestationList: [],
			locationList: [],
			oceneList: [],
			searchName: "",
      searchDateFrom: "",
      searchDateTo: "",
      searchLocation: "",
      searchPriceFrom: "",
      searchPriceTo: "",
      filterType: "",
      filterAvailable: "",
      sortType: true,
      sortCriteria: "",
	  role:"",
	  username:"",
    }
  },
  methods: {
		detaljanPregled: function(id){
			router.push({ path: `/manifestation/${id}` })
			
		},
		manifestationLocation: function(id){
			let i = 0;
			for(i ; i < this.locationList.length; i++){
				if(this.locationList[i].id == id){
					return this.locationList[i];
				}
			}
		},
		srednjaOcena: function(id){
			
			let srednjaVrednost = 0;
			let brojOcena = 0;
			for(let i = 0; i < this.oceneList.length; i++)
			{
				if(id == this.oceneList[i].manifestacija)
				{
					srednjaVrednost = srednjaVrednost + this.oceneList[i].ocena;
					brojOcena++;
				}
			}
			if(srednjaVrednost == 0){
				srednjaVrednost = "Nije ocenjena";
			}
			else{
				srednjaVrednost = (srednjaVrednost/brojOcena).toFixed(2);
			}
			return srednjaVrednost;
		},
		pretraga: function(){
			console.log(this.filterAvailable);
			if(this.searchName == "" && this.searchDateFrom == "" && this.searchDateTo == "" && this.searchsearchDateTo == "" && this.searchLocation == "" && this.searchPriceFrom == "" && this.searchPriceTo == "" && this.filterType == "" && this.filterAvailable == "")
			{
				alert("Unesite parametar za pretragu!");
			}
			else
			{
				if(this.role == 'prodavac'){
					axios.get('/searchManifestationsSeller', {params:{searchName:this.searchName, korisnickoIme:this.username, searchLocation:this.searchLocation, searchPriceFrom:this.searchPriceFrom,searchPriceTo:this.searchPriceTo, searchDateFrom:this.searchDateFrom,searchDateTo:this.searchDateTo,filterAvaiable:this.filterAvailable,filterType:this.filterType}})
					.then(response => {
						this.manifestationList = response.data;
				});

				}else{
					var searchParameters = "searchName=" + this.searchName+ "&searchLocation=" + this.searchLocation + "&searchPriceFrom=" + this.searchPriceFrom + "&searchPriceTo=" + this.searchPriceTo + "&searchDateFrom=" + this.searchDateFrom + "&searchDateTo=" + this.searchDateTo + "&filterAvaiable=" + this.filterAvailable + "&filterType=" + this.filterType;

					axios.get("/searchManifestations?" + searchParameters)
							.then(response => {
								this.manifestationList = response.data;
							})
				}
				
			}
		},
		compareData: function(a,b){
			let first, second;
			if(this.sortCriteria == "naziv")
			{
				first = a.naziv;
				second = b.naziv;
			}
			else if(this.sortCriteria == "cena")
			{
				first = a.cenaKarte;
				second = b.cenaKarte;
			}
			if(first > second)
			{
				if(this.sortType == 'rastuce')
				{
					return 1;
				}
				else
				{
					return -1;
				}
			}
			else if(first < second)
			{
				if(this.sortType == 'rastuce')
				{
					return -1;
				}
				else
				{
					return 1;
				}
			}
			else
			{
				return 0;
			}
		},
		compareLocations: function(c, d){
			let a = this.manifestationLocation(c.lokacija);
			let b = this.manifestationLocation(d.lokacija);
			if(a.drzava > b.drzava)
			{
				if(this.sortType == 'rastuce')
				{
					return 1;
				}
				else
				{
					return -1;
				}
			}
			else if(a.drzava < b.drzava)
			{
				if(this.sortType == 'rastuce')
				{
					return -1;
				}
				else
				{
					return 1;
				}
			}
			else
			{
				if(a.mesto > b.mesto)
				{
					if(this.sortType == 'rastuce')
					{
						return 1;
					}
					else
					{
						return -1;
					}
				}
				else if(a.mesto < b.mesto)
				{
					if(this.sortType == 'rastuce')
					{
						return -1;
					}
					else
					{
						return 1;
					}
				}
				else
				{
					if(a.adresa.ulica > b.adresa.ulica)
					{
						if(this.sortType == 'rastuce')
						{
							return 1;
						}
						else
						{
							return -1;
						}
					}
					else if(a.adresa.ulica < b.adresa.ulica)
					{
						if(this.sortType == 'rastuce')
						{
							return -1;
						}
						else
						{
							return 1;
						}
					}
					else
					{
						return 0;
					}
				}
			}
		},
		compareDate: function(a,b){
			if(this.sortType == 'rastuce')
			{
				if (Date.parse(a.datum) > Date.parse(b.datum)) {
					return 1;
			  }  
				else if (Date.parse(a.datum) < Date.parse(b.datum)) {
					return -1;
			  } 
				else {
					return 0;
			  }
			}
			else
			{
				if (Date.parse(a.datum) < Date.parse(b.datum)) 
				{
					return 1;
			  } 
				else if (Date.parse(a.datum) > Date.parse(b.datum)) 
				{
					return -1;
			  } 
				else {
					return 0;
			  }
			}
		},
		sortData: function(){
			if(sortType != "rastuce" && this.sortType != "opadajuce")
			{
				alert("Unesite smer sortiranja pretrage");
			}
			else
			{
				if(this.sortCriteria == "lokacija")
					this.manifestationList.sort(this.compareLocations)
				else if(this.sortCriteria == "datumVreme")
					this.manifestationList.sort(this.compareDate)
				else
					this.manifestationList.sort(this.compareData);
			}
		},
  },
  mounted: function(){
	this.username = window.localStorage.getItem('kIme');
	this.role = window.localStorage.getItem('role');
	if(this.role == "prodavac"){
		axios.get("/sellerManifestations", {params:{korisnickoIme:this.username}})
		.then(response => {
			this.manifestationList = response.data;
		});
		axios.get("/manifestationLocations")
			.then(response => {
				this.locationList = response.data;
			});
	}else{
    axios.get("/manifestations")
			.then(response => {
				this.manifestationList = response.data;
			});
		
		axios.get("/manifestationLocations")
			.then(response => {
				this.locationList = response.data;
			});

		axios.get("/comments")
			.then(response => {
				this.oceneList = response.data;
			});
		}
  },
  template: `
  <div class="card">
		<div class="card-header" style="background-color:#1fb579; color:white">Manifestacije</div>
		<div  class="row">
			<div class="col-md-18 col-20" style="margin-left: 9.5em; margin-top: 2em">
				<div class="row rone">
					<input class="form-control jk" type="text" v-model="searchName"  placeholder="Pretrazi po imenu" style="margin: 0.3em; width: 12em;">
					<input class="form-control jk" type="text" v-model="searchLocation"  placeholder="Pretrazi po gradu" style="margin: 0.3em; width: 12em;">
		
					<label style="color:black; margin: 0.5em;">Datum:</label>
					<input class="form-control jk" v-model="searchDateFrom"  type="date" style="margin: 0.3em; width: 11em;">
					<input class="form-control jk" v-model="searchDateTo"  type="date" style="margin: 0.3em; width: 11em;">
					<label style="color:black; margin: 0.5em; margin-right:1.2em;">Cena: </label>
					<input class="form-control jk" v-model="searchPriceFrom"  type="text" placeholder="Od" style="margin: 0.3em; width: 8em;">
					<input class="form-control jk" v-model="searchPriceTo"  type="text" placeholder="Do" style="margin: 0.3em; width: 8em;">
				</div>
				
				<div class="row rone">
					<label style="color:black; margin: 0.5em;">Fileteri</label>
					<label style="color:black; margin-left: 40.5em; margin-top:0.5em;" for="sortCriteria">Sortiranje</label>
				</div>
				<div class="row rone">
					<select class="form-control jk" v-model="filterType" style="margin: 0.3em; width: 29.2em;">
						<option value='All'>Sve</option>
						<option value="KONCERT" >Koncert</option>
						<option value="FESTIVAL">Festival</option>
						<option value="POZORISTE" >Pozoriste</option>
					</select>
					<select class="form-control jk" v-model="sortCriteria" style="margin: 0.3em; margin-left:14em; width: 14.2em;">
						<option value='naziv'>Naziv manifestacije</option>
						<option value="datumVreme">Datum i vreme odrzavanja</option>
						<option value="cena">Cena karte</option>
						<option value="lokacija">Lokacija</option>
					</select>
					<select class="form-control jk" v-model="sortType" style="margin: 0.3em; width: 14.2em;">
						<option value='rastuce'>Rastuce</option>
						<option value="opadajuce">Opadajuce</option>
					</select>
				</div>
				<div class="row rone">
					<label style="color:black; margin: 0.5em;">Nerasprodate manifestacije</label>
					<input class="form-control jk" type="checkbox" v-model="filterAvailable" style="height:1em; width:1em; margin: 0.7em;">
				</div>
				<div class="row rone">
					<button class="btn" style="margin: 1px; background-color:#1fb579; color:white"  v-on:click="pretraga">Pretrazi</button>
					<button class="btn" style="margin: 1px; background-color:#1fb579; color:white"  v-on:click="sortData">Sortiraj</button>
				</div>
		</div>
		</hr>
		<div class="justify-content-center">
		<div class="row row-cols-4 row-cols-md-3 g-2" style="margin-left: 1em; margin-top: 5em;">
				<div class="col-lg-4" v-for="(manifestation, index) in manifestationList" width="350em">
				<div class="card" style="margin-bottom:1em;" width="350em">
					<div class="img-square-wrapper" style="margin-left: 4.5em;" hight="360em">
						<img :src="manifestation.slika" width="330em" height="350em">
					</div>
				
					<div class="card-body" width="350em">
						<h5 class="card-title">{{ manifestation.naziv }}</h5>
						<ul class="list-group list-group-flush">
							<li class="list-group-item">Tip: {{ manifestation.tipManifestacije }}</li>
							<li class="list-group-item">Datum i vreme: {{ manifestation.datum }}</li>
							<li class="list-group-item">Cena regularne karte: {{ manifestation.cenaKarte }}</li>
							<li class="list-group-item">Lokacija: {{ (manifestationLocation(manifestation.lokacija)).adresa.drzava}}, {{(manifestationLocation(manifestation.lokacija)).adresa.mesto}}, {{ (manifestationLocation(manifestation.lokacija)).adresa.ulica}}</li>
							<li class="list-group-item">Ocena: {{srednjaOcena(manifestation.id)}}</li>
							<li class="list-group-item"><button type="button" class="btn btn-outline-dark" v-on:click="detaljanPregled(manifestation.id)">Detaljan pregled</button></li>
						</ul>
						</hr>
					</div>	
				</div>
			</div>
			</div>
		</div>
	 </div>
	</div>
  `,
});