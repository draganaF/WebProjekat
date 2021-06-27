Vue.component("manifestation", {
  name: "manifestation",
  data: function(){
    return {
      manifestation: null,
      comments: [],
      location: null,
      role: localStorage.getItem('role'),
    }
  },
  methods: {
    nadjiStatus: function(){
      if(this.manifestation.status){
        return "AKTIVNA";
      }
      else{
        return "NEAKTIVNA";
      }
    },
    srednjaOcena: function(id){
			
			let srednjaVrednost = 0;
			let brojOcena = 0;
			for(let i = 0; i < this.comments.length; i++)
			{
				if(id == this.comments[i].manifestacija)
				{
					srednjaVrednost = srednjaVrednost + this.comments[i].ocena;
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
    nadjiKomentare: function(id){
      axios.get("/commentsForManifestation?id=" + id)
        .then(response2 => {
          this.comments = response2.data;
        });
    },
    nadjiLokaciju: function(id){
      axios.get("/manifestationLocation?id=" + this.manifestation.lokacija)
      .then(response1 => {
        this.location = response1.data;
        this.nadjiKomentare(id);
      });
    },
    nadjiManifestaciju: function(){
      let id = this.$route.params.id;
      axios.get('/manifestation?id=' + id)
      .then(response => {
        this.manifestation = response.data;
        this.nadjiLokaciju(id);
      })
      .catch(error =>{
        console.log(error);
      });
    }
  },
  mounted: function(){

    this.nadjiManifestaciju();      
    
    },
  beforeUpdate: function(){
    this.map = new ol.Map({
      target: 'map',
      layers: [
        new ol.layer.Tile({
        source: new ol.source.OSM()
        })
      ],
      view: new ol.View({
        center: ol.proj.fromLonLat([this.location.geografskaDuzina,this.location.geografskaSirina]),
        zoom: 16
      })
    });
    var markers = new ol.layer.Vector({
      source: new ol.source.Vector(),
      style: new ol.style.Style({
        image: new ol.style.Icon({
          anchor: [0.5, 2],
          src: "./images/marker.png",
        }),
      }),
    });
    this.map.addLayer(markers);

    var marker = new ol.Feature(
      new ol.geom.Point(ol.proj.fromLonLat([this.location.geografskaDuzina,this.location.geografskaSirina]))
    );
    markers.getSource().addFeature(marker);
  },
  template: `
  <div class="container-fluid body-reg">
    <div class="card" style="margin-bottom:1em;" width="350em">
      <div class="card-header"  style="background-color:#1fb579;    color:white">Pregled Manifestacije</div>
     
      <div class="card-body" width="350em">
        <div class="row">
          <div class="img-square-wrapper list-group-item" style="margin-left: 9em; padding-top:3em;" hight="360em">
            <img :src="manifestation.slika" width="330em" height="350em">
          </div>
          <div class="card" style="margin-left: 4em;">
            <ul class="list-group list-group-flush">
              <li class="list-group-item"> <strong> {{manifestation.naziv}}</strong></li>
              <li class="list-group-item">Tip: {{ manifestation.tipManifestacije }}</li>
              <li class="list-group-item">Datum i vreme: {{ manifestation.datum }}</li>
              <li class="list-group-item">Cena regularne karte: {{ manifestation.cenaKarte }}</li>
              <li class="list-group-item">Broj mesta: {{manifestation.brojMesta}}</li>
              <li class="list-group-item">Slobodna mesta: {{manifestation.slobodnaMesta}}</li>
              <li class="list-group-item">Status: {{nadjiStatus()}}</li>
              <li class="list-group-item">Ocena: {{srednjaOcena(manifestation.id)}}</li>
            </ul> 
          </div>
          <div class="list-group-item" style="margin-left:4em;">Lokacija: {{ location.adresa.drzava}}, {{location.adresa.mesto}}, {{ location.adresa.ulica }} {{location.adresa.broj}}
	          <div id="map" class="map" width="330em" height="350em">
	            <div id="popup" class="ol-popup" ref="container">
			          <a href="#" id="popup-closer" class="ol-popup-closer" ref="closer"></a>
				        <div id="popup-content" ref="content"></div>
		          </div>
	          </div>
          </div>
        </div>
      </div> 
    </div>
    <div class="card" style="margin-bottom:2em;">
      <div class="card-header">Komentari:</div>
      <div class="card-body">
        <div v-if="role!='Seller' && role!='Admin'" v-for="comment in comments" class="comment-widgets">
          <div v-if="comment.odobrena == 1" class="d-flex flex-row comment-row m-t-0">
            <div class="p-2"><img src="./images/user.jpg" alt="user" width="50" class="rounded-circle"></div>
              <div class="comment-text w-100">
                <h6 class="font-medium">{{ comment.kupac }}</h6> <span class="m-b-15 d-block">{{ comment.tekst }} </span>
              <div class="comment-footer" style="padding-bottom:1em;"> 
                <span class="text-muted float-right">Ocena: {{ comment.ocena }}</span>
              </div> 
              <hr>
            </div>
        </div>
      </div>
      <div v-if="role=='Seller' || role=='Admin'" v-for="comment in comments" class="comment-widgets">
        <div class="d-flex flex-row comment-row m-t-0">
          <div class="p-2"><img src="./images/user.jpg" alt="user" width="50" class="rounded-circle"></div>
          <div class="comment-text w-100">
              <h6 class="font-medium">{{ comment.kupac }}</h6> <span class="m-b-15 d-block">{{ comment.tekst }} </span>
              <div class="comment-footer">
                <span class="text-muted float-right">Ocena: {{ comment.ocena }}</span></div>
                <div v-if="comment.odobrena == 1"><span class="m-b-15 d-block">Odobren</span></div>
                <div v-if="comment.odobrena == 0"><span class="m-b-15 d-block">Odbijen</span></div>
              </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  `
});