Vue.component("manifestation", {
  name: "manifestation",
  data: function(){
    return {
      manifestation: null,
      comments: [],
      location: null,
      tickets: [],
      role: "",
      canComment: false,
      canApprove: false,
      user: localStorage.getItem('kIme'),

      contentComment: "",
      commentGrade: -1,
      errorMessage: "",
      reservation: {
        cardType: "REGULAR",
        price: 0,
        quantity: 0,
      },
      typeUser: {},
    }
  },
  methods: {
    komentarisi: function() {
      if(this.commentGrade == -1) {
        this.errorMessage = "Morate da unesete ocenu";
        return;
      }
      if(!this.contentComment) {
        this.errorMessage = "Morate da unesete sadrzaj komentara";
        return;
      }
      if(this.contentComment) {
        axios
        .post('/addComment', {id: -1, 
                    kupac: this.user,
                    manifestacija: this.manifestation.id,
                    tekst: this.contentComment,
                    ocena: this.commentGrade,
                    odobrena: -1,
                    obrisan: false
                    })
        .then(response => {
          alert("Uspesno ste dodali komentar, ukoliko bude odobren moci cete da ga pregledate");
          console.log(response)
        });
      }
    },
    ostaviKomentar: function() {
      var date = new Date();
      if (Date.parse(this.manifestation.datum) >= date) {
        this.canComment = false;
      }  
      else {
        for(var i = 0; i < this.tickets.length; i++) {
          if(this.tickets[i].kupac == this.user) {
            for(var j = 0; j < this.comments.length; j++) {
              if(this.comments.kupac == this.user){
                this.canComment = false;
                return;
              }
            }
            this.canComment = true;
          }
        }
      }
    },
    nadjiStatus: function() {
      if(this.manifestation.status){
        return "AKTIVNA";
      }
      else{
        return "NEAKTIVNA";
      }
    },
    racun: function() {
      this.reservation.price = this.reservation.quantity * this.manifestation.cenaKarte;
      if (this.reservation.cardType == "FANPIT") {
        this.reservation.price = 2 *  this.reservation.price;
      } else if(this.reservation.cardType == "VIP") {
        this.reservation.price = 4 * this.reservation.price;
      }
      this.reservation.price = this.reservation.price * (1 - this.typeUser.popust / 100);
    },
    kupiKarte : function(event){
    let id = this.$route.params.id;
      axios
      .post('/reserveTicket',{},{params:{korisnickoIme:this.kIme, manifestacija:id,tipKarte:this.reservation.cardType, kolicina:this.reservation.quantity}})
      .then((response) => {
        alert("Uspesno set rezervisali kartu/e! ");
        this.nadjiManifestaciju();  
        
      })
      .catch((err) => {
        console.log(err);
      });

    },
    srednjaOcena: function(id) {
			
			let srednjaVrednost = 0;
			let brojOcena = 0;
			for(let i = 0; i < this.comments.length; i++) {
				if(id == this.comments[i].manifestacija) {
					srednjaVrednost = srednjaVrednost + this.comments[i].ocena;
					brojOcena++;
				}
			}
			if(srednjaVrednost == 0) {
				srednjaVrednost = "Nije ocenjena";
			}
			else {
				srednjaVrednost = (srednjaVrednost/brojOcena).toFixed(2);
			}
			return srednjaVrednost;
		},
    nadjiKarte: function(id) {
      axios.get("/manifestationTickets?id=" + id)
      .then(response3 => {
        this.tickets = response3.data;
        this.ostaviKomentar();
      });
    },
    nadjiKomentare: function(id) {
      axios.get("/commentsForManifestation?id=" + id)
        .then(response2 => {
          this.comments = response2.data;
          this.nadjiKarte(id);
        });
    },
    nadjiLokaciju: function(id) {
      axios.get("/manifestationLocation?id=" + this.manifestation.lokacija)
      .then(response1 => {
        this.location = response1.data;
        this.nadjiKomentare(id);
      });
    },
    nadjiManifestaciju: function() {
      console.log(this.user);
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
  mounted: function() {
    this.kIme = window.localStorage.getItem('kIme');
    this.role = window.localStorage.getItem('role');
    if(this.role == 'kupac'){
      axios.get('/userType',  {params:{korisnickoIme:this.kIme}})
		      .then(response => {
            this.typeUser = response.data;                    
            });
    }
    this.nadjiManifestaciju();      
    },
  beforeUpdate: function() {
    this.map = new ol.Map({
      target: 'map',
      layers: [
        new ol.layer.Tile({
        source: new ol.source.OSM()
        })
      ],
      view: new ol.View({
        center: ol.proj.fromLonLat([this.location.geografskaDuzina,this.location.geografskaSirina]),
        zoom: 18
      })
    });
    var markers = new ol.layer.Vector({
      source: new ol.source.Vector(),
      style: new ol.style.Style({
        image: new ol.style.Icon({
          anchor: [0.5, 1],
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
    <div class="card" style="margin-bottom:1em;">
      <div class="card-header"  style="background-color:#1fb579; color:white">Pregled Manifestacije</div>
      <div id="myModal" class="modal fade" role="dialog">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title">Rezervacija karti</h4>
          </div>
          <div class="modal-body">
          <p class="lead my-3"> Izaberite tip karte</p>
          <select v-model="reservation.cardType" class=" form-control jk sel"">
            <option selected value="REGULAR">REGULAR</option>
            <option value="VIP">VIP</option>
            <option value="FANPIT">FAN PIT</option>
          </select>
          <p class="lead my-3">Kolicina:</p>
          <input @change="racun"  type="number" name="quantity" v-model="reservation.quantity" id="quantity" placeholder="Kolicina" />
          <p class="lead my-3">Cena :</p>
          <input :disabled="true" type="text" name="price" v-model="reservation.price" id="price" />
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Zatvori</button>
            <button :disabled="reservation.quantity > manifestation.slobodnaMesta ? true : false" type="button"  data-dismiss="modal" class="btn btn-success" @click="kupiKarte">Kupi</button>
          </div>
        </div>
    </div>
    </div>
      </div>
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
              <li v-if="role == 'kupac' && nadjiStatus() == 'AKTIVNA'" class="list-group-item"><button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal" :id = "manifestation.id">Rezervisi</button></li>
            </ul> 
          </div>
          <div class="list-group-item" style="margin-left:4em;">Lokacija: {{ location.adresa.drzava}}, {{location.adresa.mesto}}, {{ location.adresa.ulica }} {{location.adresa.broj}}
	          <div id="map" class="map" width="330em" height="350em">
	           
	          </div>
          </div>
        </div>
      </div> 
    </div>
    <div class="card" style="margin-bottom:2em;">
      <div class="card-header">Komentari:</div>
      <div class="card-body">
        <div v-if="role!='prodavac' && role!='admin'" v-for="comment in comments" class="comment-widgets">
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
      <div v-if="role=='prodavac' || role=='admin'" v-for="comment in comments" class="comment-widgets">
        <div class="d-flex flex-row comment-row m-t-0">
          <div class="p-2"><img src="./images/user.jpg" alt="user" width="50" class="rounded-circle"></div>
          <div class="comment-text w-100">
              <h6 class="font-medium">{{ comment.kupac }}</h6> <span class="m-b-15 d-block">{{ comment.tekst }} </span>
              <div class="comment-footer">
                <span class="text-muted float-right">Ocena: {{ comment.ocena }}</span></div>
                <div v-if="comment.odobrena == 1"><span class="m-b-15 d-block" style="color: green;">Odobren</span></div>
                <div v-if="comment.odobrena == 0"><span class="m-b-15 d-block" style="color: red;">Odbijen</span></div>
                <hr>
              </div>
             
          </div>
        </div>
      </div>
      <div v-if="canComment"> 
        <p class="text-muted d-flex flex-row comment-row m-t-0">Unesite komentar i ocenu:</p>
        <textarea style="margin-left:0.2em; width: 30em;" v-model="contentComment" class="form-control d-flex flex-row comment-row m-t-0 textarea"></textarea>
        <select style="margin-top:1em; margin-bottom:0.5em; margin-left:0.2em; width: 30em;"  v-model="commentGrade" class="form-control">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
        </select>
        <button type="submit" class="btn" style="background-color:#1fb579; color:white; margin-left:0.2em;" v-on:click="komentarisi">Dodaj komentar</button> 
        <p v-if="errorMessage" class="text-muted">{{ errorMessage }}</p>
      </div>
    </div>
  </div>
  `
});