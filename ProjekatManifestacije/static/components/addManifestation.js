Vue.component('add-manifestation',{
  name: "add-manifestation",
	data: function() {
		return {
            manifestationId: null,
            manifestation: null,
            location: null,
            address: null,
            name: null,
            type: null,
            freeSeat: null,
            date: null,
            price: null,
            longitude: null,
            latitude: null,
            street: null,
            number: null,
            city: null,
            picture: null,
            pictureShow: null,
            pictureForBackend: null,
            check: false,
            dataForm: null,
            error: null,
            user: localStorage.getItem('kIme'),

		}
	},
	methods: {
		pictureChange: function(event){
            const file = event.target.files[0];
            this.createBase64Image(file);
            this.pictureShow = (URL.createObjectURL(file));
        },
        createBase64Image(file){
            const reader= new FileReader();
 
            reader.onload = (e) =>{
                this.imagesForBackend = (e.target.result);
            }
            reader.readAsDataURL(file);
        },
        checkInputs: function(e){
            e.preventDefault();
            console.log("rrrrrrrrrr"+this.imagesForBackend);
            if(this.manifestationId != -1){
                if(this.name == this.manifestation.naziv && this.type == this.manifestation.tipManifestacije &&
                    this.freeSeat == this.manifestation.slobodnaMesta && this.date == null &&
                    this.price == this.manifestation.cena && this.longitude == this.location.geografskaDuzina &&
                    this.latitude == this.location.geografskaSirina && this.street == this.address.ulica &&
                    this.number == this.address.broj && this.city == this.address.mesto
                   ){
                        this.error = "Morate izmeniti podatke.";
                        return;
                    }
            }
            if((this.name != "" && this.name != null) && this.freeSeat != "" && this.freeSeat != null && 
                this.price != "" && this.price != null && this.longitude != "" && this.longitude != null
                 && this.latitude != "" && this.latitude != null && this.street != "" && this.street != null &&
                    this.number != "" && this.number != null && this.city != "" && this.city && this.imagesForBackend!=null && this.imagesForBackend!="" ) {
                        if(this.isNumeric(this.freeSeat)){
                            if(this.isFloat(this.price)){
                                if(this.isFloat(this.longitude) || this.isFloat(this.latitude)) {
                                        if(this.isNumeric(this.number)){
                                            if(this.date != null && this.manifestationId != -1){
                                                if(Date.parse(this.manifestation.datum) > Date.now() && Date.parse(this.date) < Date.now()) {
                                                        this.error = "Ne mozete postaviti datum u prošlosti."
                                                    }
                                                    else{
                                                        this.createObjects();
                                                    }
                                            }
                                            if(this.manifestationId == -1){
                                                if((Date.parse(this.datum) < Date.parse(Date.now()))){
                                                    this.error = "Ne mozete postaviti datum u prošlosti.";
                                                    return;
                                                }
                                            }
                                            this.createObjects();
                                        }
                                        else{
                                            this.error = "Broj zgrade mora biti ceo broj."
                                        }
                                    }
                                else{
                                    this.error = "Geografska dužina i geografska sirina moraju biti brojevi."
                                }
                            }
                            else{
                                this.error = "Cena mora biti broj.";
                            }
                        }
                        else{
                            this.error = "Broj slobodnih sedišta mora biti ceo pozitivan broj.";
                        }
            }
            else{
                this.error = "Polja ne smeju biti prazna."
            }
        
        },
        isNumeric: function (value) {
            return /^[0-9]+$/.test(value);
        },
        isFloat: function(value){
            return /^[0-9]+(\.)?[0-9]*$/.test(value);
        },
        createObjects: function(){
            if(this.manifestationId == -1){
                this.manifestation = {};
                this.location = {};
                this.address = {};
            }
            this.manifestation.slika = this.imagesForBackend;
            this.manifestation.naziv = this.name;
            this.manifestation.tipManifestacije = this.type;
            this.manifestation.prodavac = this.user;
            if(this.date != null){
                date = new Date(this.date);
                this.manifestation.datum = date;
            }
            else{
                this.manifestation.datum = new Date(this.manifestation.datum);
            }
            this.manifestation.slobodnaMesta = parseInt(this.freeSeat);
            this.manifestation.cena = parseFloat(this.price);
            this.location.geografskaDuzina = parseFloat(this.longitude);
            this.location.geografskaSirina = parseFloat(this.latitude);
            this.address.ulica = this.street;
            this.address.broj = this.number;
            this.address.mesto = this.city;
            this.address.drzava = this.state;
            this.address.postanskiBroj = this.zipCode;
            this.location.adresa = this.address;
           
            if(this.manifestationId == -1)
                this.createManifestation();
            else
                this.updateManifestation();
        },
        createManifestation: function(){
            axios.post("/createManifestation", {
              manifestacija: this.manifestation,
              lokacija: this.location,
            })
            .then(response =>{
                if(response.data){
                    alert("Manifestacija je uspesno kreirana.")
                }
                else{
                    this.error = "Uneta lokacija je zauzeta u unetom terminu."
                }
            })
        },
        updateManifestation: function(){
            axios.post("/updateManifestation", {
              manifestacija: this.manifestation,
              lokacija: this.location,
            })
            .then(resp=>{
                if(resp.data == true){
                    alert("Manifestacija je uspesno kreirana");
                }else{
                    this.error = "Uneta lokacija je zauzeta u unetom terminu."
                }
            })
        },

        getLocation(){
          axios.get('/manifestationLocation?id='+this.manifestation.id)
          .then(resp=>{
              this.location = resp.data;
              this.longitude = this.location.geografskaDuzina;
              this.latitude = this.location.geografskaSirina;
              this.address = this.location.adresa;
              this.street = this.address.ulica;
              this.number = this.address.broj;
              this.city = this.address.mesto ;
              this.state = this.address.drzava;
              this.zipCode = this.address.postanskiBroj;
              this.check = true;
          })
        },
        getManifestation(){
          axios.get("/getManifestation?id=" + this.manifestationId)
          .then(response => {
              this.manifestation = response.data;
              this.name = this.manifestation.naziv;
              this.type = this.manifestation.tipManifestacije;
              this.freeSeat = this.manifestation.slobodnaMesta;
              this.price = this.manifestation.cenaKarte;
              this.pictureShow = this.manifestation.slika;
              this.getLocation();
          })
        },
    },
    mounted: function(){
        this.manifestationId = this.$route.params.id;
        map = new ol.Map({
            target: 'map',
            layers: [
              new ol.layer.Tile({
                source: new ol.source.OSM()
              })
            ],
            view: new ol.View({
              center: ol.proj.fromLonLat([19.83,45.26]),
              zoom: 13
            })
          });
        vm=this;
        map.on('singleclick', function (evt) {
            
            coordinate = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');
            vm.longitude = coordinate[0];
            vm.latitude = coordinate[1];
        });
        if(this.manifestationId != -1){
            this.getManifestation();
        }
        else{
            this.check = true;
        }
    },
    template:`
    <div class="card" style="margin-bottom:1em;">
      <div class="card-header" style="background-color:#1fb579; color:white">CRUDManifestacije</div>
      <div class="card-body">
      <div class="row">
        <h3 style="color: #1fb579; margin-left:20em;">OSNOVNE INFORMACIJE</h3>
      </div>
      <br>
      <br>
      <div class="row">
        <div class="form-group col-md-4">
          <img v-if="!pictureShow" src="" width="330em" height="350em">
          <img v-if="pictureShow" :src="pictureShow" width="330em" height="350em">
        </div>
        <div class="form-group col-md-8">
          <div class="row">
            <div class="form-group col-md-6 fone py-3">
              <label style="color: black">Naziv</label><br> 
              <input v-model="name" type="text" class="form-control"> 
            </div>
            <div class="form-group col-md-6 ftwo py-3"> 
              <label style="color: black">Tip</label>
              <select v-model="type" class="form-control">
                  <option value="KONCERT" >Koncert</option>
                  <option value="FESTIVAL">Festival</option>
                  <option value="POZORISTE" >Pozorište</option>
              </select> 
            </div>
          </div>
          <div class="row">
            <div class="form-group col-md-6 fthree py-3">
              <label style="color: black">Slobodna mesta</label>
              <input v-model="freeSeat" type="text" class="form-control jk"> 
            </div>
            <div class="form-group col-md-6 ffour py-3"> 
              <label style="color: black">Cena</label><br>
              <input v-model="price" type="text" class="form-control lm"> 
            </div>
          </div>
          <div class="row">
            <div class="form-group col-md-6 ffive py-3"> 
              <label v-if="manifestation" style="color: black">Datum odrzavanja(stari: {{ manifestation.datum }})</label>
              <label v-if="!manifestation" style="color: black">Datum odrzavanja:</label>
              <input v-model="date" type="datetime-local" class="form-control"> 
            </div>
            <div class="form-group col-md-6 fsix py-3"> 
              <label style="color: black">Poster</label>
              <input type="file" v-on:change="pictureChange" class="form-control form-control-sm"> 
            </div>
          </div>
        </div>
      </div>
      <div class="row" style="margin-left:40em;">
        <h3 style="color:#1fb579;">LOKACIJA</h3>
      </div>
      <br>
      <br>
      <div class="row">
        <div style="width:50%;height:420px;" class="col-md-4" id="map">
        </div>
        <div class="form-group col-md-8 fsix py-3"> 
          <div class="row">
            <div class="form-group col-md-6 fsix py-3">
              <label style="color: black">Geografska sirina</label>
              <input v-model="latitude" type="text" class="form-control">
            </div>
            <div class="form-group col-md-6 fsix py-3">
              <label style="color: black">Geografska duzna</label>
              <input v-model="longitude" type="text" class="form-control">
            </div>
          </div>
          <div class="row">
            <div class="form-group col-md-6 ffive py-3"> 
              <label style="color: black">Drzava</label><br>
              <input v-model="state" type="text" class="form-control"> 
            </div>
            <div class="form-group col-md-6 fsix py-3">
              <label style="color: black">Grad</label><br>
              <input v-model="city" type="text" class="form-control">
            </div>
          </div>
          <div class="row"> 
            <div class="form-group col-md-6 fsix py-3">
              <label style="color: black">Ulica</label><br>
              <input v-model="street" type="text" class="form-control"> 
            </div>
            <div class="form-group col-md-3 fsix py-3">
              <label style="color: black">Broj</label>
              <input v-model="number" type="text" class="form-control"> 
            </div>
            <div class="form-group col-md-3 fsix py-3"> 
              <label style="color: black">Postanski broj</label>
              <input v-model="zipCode" type="text" class="form-control"> 
            </div>
          </div>
        </div>
      </div>
      
</div>
<div class="row">
        <div style="margin-left: 45em;" class="form-group col-md-6 fseven py-3"> 
          <button v-if="manifestation" class="btn btn-success" v-on:click="createObjects">Izmeni</button> 
          <button v-if="!manifestation" class="btn btn-success" v-on:click="createObjects">Dodaj</button> 
        </div>
        <div v-if="error" class="form-group col-md-6 feight py-3">
          <p class="text-muted">{{ error }}</p>
        </div>
      </div>
</div>
`
})
