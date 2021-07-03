Vue.component("ticketUser", {
    name: "ticketUser",
    data: function () {
      return {
        tickets:[],
        manifestations:[],
        ticketsWithManifestations:[],
        pretragaManifestacija:"",
        status:"",
        tip:"",
        sortVal:"",
        sortTip:"",
        kIme:"",
        role:"",
        pretragaDatumOd:"",
        pretragaDatumDo:"",
        pretragaCenaOd:"",
        pretragaCenaDo:"",
        filter: false,
    };
    },
    mounted: function(){
        this.kIme = window.localStorage.getItem('kIme');
        this.role = window.localStorage.getItem('role');
        this.refresh();					
    },
    methods: {

        otkazi(event){
            var id = event.target.id;
                    axios
                .post('/cancelTicket',{}, {params:{korisnickoIme:this.kIme,id:id}})
                .then((response) => {
                    if(response.data === false){
                        alert("Ne mozete da odustanete! ");
                    }else{
                    alert("Otkazali ste! ");
                    this.tickets = [];
                    this.manifestations = [];
                    this.ticketsWithManifestations = [];
                    this.refresh();
                    }
                  
                })
                .catch((err) => {
                    alert("Ne mozete da odustanete! ");
                });
            
        },
        refresh(){
            axios.get('/userTickets',  {params:{korisnickoIme:this.kIme}})
		        .then(response => {
                    this.tickets = response.data;
                    this.getManifestations();
                    
            });
        },
        getManifestations(){
                axios.get('/manifestations',)
                .then(response => {
                    this.manifestations = response.data;
                    this.getAll();
            });
        },
        getAll(){
            for(var i =0;i<this.tickets.length;i++){
                for(var j = 0;j<this.manifestations.length;j++){
                    if(this.tickets[i].manifestacija == this.manifestations[j].id){
                        var ticket = {};
                        ticket = this.tickets[i];
                        var man = {};
                        man = this.manifestations[j]; 
                        ticket.manifestation = man;
                        this.ticketsWithManifestations.push(ticket);
                    }
                }
            }


        },
        search(){
            this.tickets = [];
            this.manifestations = [];
            this.ticketsWithManifestations = [];
            if((this.pretragaCenaOd !="" && this.pretragaCenaDo == "") | (this.pretragaCenaDo !="" && this.pretragaCenaOd == "")){
                alert("Morate uneti cenu u opsegu (od-do) !");
            }else if((this.pretragaDatumOd !="" && this.pretragaDatumDo =="") | (this.pretragaDatumDo !="" && this.pretragaDatumOd == "")){
                alert("Morate uneti datum u ospegu (od-do)!");

            }else{
                
                    if(this.filter == true){
                        axios.get('/searchTickets', {params:{role:this.role,korisnickoIme:this.kIme,pretragaManifestacija:this.pretragaManifestacija, pretragaCenaOd:this.pretragaCenaOd, pretragaCenaDo:this.pretragaCenaDo, pretragaDatumOd:this.pretragaDatumOd, pretragaDatumDo:this.pretragaDatumDo, status:"rezervisana", tip:this.tip}})
                        .then(response => {
                            this.tickets = response.data;
                            this.getManifestations();
                    });
                    }else{
                    axios.get('/searchTickets', {params:{role:this.role,korisnickoIme:this.kIme,pretragaManifestacija:this.pretragaManifestacija, pretragaCenaOd:this.pretragaCenaOd, pretragaCenaDo:this.pretragaCenaDo, pretragaDatumOd:this.pretragaDatumOd, pretragaDatumDo:this.pretragaDatumDo, status:this.status, tip:this.tip}})
                    .then(response => {
                        this.tickets = response.data;
                        this.getManifestations();
                });
            }
            }

        },
        sortIme(){
            this.ticketsWithManifestations.sort( function( a, b ){
              if( this.sortTip == 'rastuce' ){
                return ( ( a.manifestation.naziv  == b.manifestation.naziv  ) ? 0 : ( ( a.manifestation.naziv  > b.manifestation.naziv  ) ? 1 : -1 ) );
              }
          
              if( this.sortTip == 'opadajuce' ){
                return ( ( a.manifestation.naziv == b.manifestation.naziv  ) ? 0 : ( ( a.manifestation.naziv  < b.manifestation.naziv ) ? 1 : -1 ) );
              }
            }.bind(this));
          },
          sortCena(){
            this.ticketsWithManifestations.sort( function( a, b ){
              if( this.sortTip == 'rastuce' ){
                return ( ( a.cena == b.cena ) ? 0 : ( ( a.cena  > b.cena  ) ? 1 : -1 ) );
              }
          
              if( this.sortTip == 'opadajuce' ){
                return ( ( a.cena == b.cena ) ? 0 : ( ( a.cena  < b.cena) ? 1 : -1 ) );
              }
            }.bind(this));
          },
          sortDatum(){
            this.ticketsWithManifestations.sort( function( a, b ){
              if( this.sortTip == 'rastuce' ){
                return ( ( Date.parse(a.manifestation.datum ) == Date.parse(b.manifestation.datum ) ) ? 0 : ( ( Date.parse(a.manifestation.datum )  > Date.parse(b.manifestation.datum )  ) ? 1 : -1 ) );
              }
          
              if( this.sortTip == 'opadajuce' ){
                return ( ( Date.parse(a.manifestation.datum )== Date.parse(b.manifestation.datum )  ) ? 0 : ( ( Date.parse(a.manifestation.datum ) < Date.parse(b.manifestation.datum ) ) ? 1 : -1 ) );
              }
            }.bind(this));
          },
        sort(){
            if(this.sortTip == ""){
                alert("Morate selektovati nacin sortiranja (rastuce/opadajuce)!");
            }
			if(this.sortVal == "manifestacija")
				this.sortIme();
            else if(this.sortVal == "cena")
                this.sortCena();
            else if(this.sortVal == "datum")
                this.sortDatum();
            
        }
    },
    template: 
    ` <div class="row justify-content-center">
        <div class="col-md-11">
            <div class="card col-md-11">
                <div class="card-header row justify-content-center"  style="background-color:#1fb579; color:white">Karte</div>
                <div class="card-body">
                    <div class="row rone">
                        <input  class="form-control jk" type="text"  v-model="pretragaManifestacija" placeholder="Pretraga po nazivu" style="margin: 0.3em; width: 12em;"></input>
                        <input  class="form-control jk" type="date"  v-model="pretragaDatumOd" placeholder="Datum od" style="margin: 0.3em; width: 12em;"></input>
                        <input class="form-control jk" type="date"  v-model="pretragaDatumDo" placeholder="Datum do" style="margin: 0.3em; width: 12em;"></input>
                        <input  class="form-control jk" type="text"  v-model="pretragaCenaOd" placeholder="Cena od" style="margin: 0.3em; width: 12em;"></input>
                        <input  class="form-control jk" type="text"  v-model="pretragaCenaDo" placeholder="Cena do" style="margin: 0.3em; width: 12em;"></input>
                    </div>
                    <div class="row rone">
					    <label style="color:black; margin: 0.5em;">Rezervisane  karte</label>
					    <input class="form-control jk" type="checkbox" v-model="filter" style="height:1em; width:1em; margin: 0.7em;">
				    </div>
                    <div class="row rone">
                        <select v-model="status" class=" form-control jk sel"  style="margin: 0.3em; width: 14.2em;">
                        <option value="" selected>Sve</option>
                        <option value="rezervisana">Rezervisana</option>
                        <option value="odustanak">Otkazana</option>
                        </select>

                        <select v-model="tip" class="form-control jk sel" style="margin: 0.3em; width: 14.2em;">
                        <option value="" selected>Svi</option>
                        <option value="VIP" >VIP</option>
                        <option value="REGULAR">REGULAR</option>
                        <option value="FANPIT" >FAN PIT</option>
                        </select>

                        <button  v-on:click="search" class="s" style=" background-color: #56baed;
                        border: none;
                        color: white;
                        text-decoration: none;
                        padding: 15px 10px;
                        -webkit-box-shadow: 0 10px 30px 0 rgba(95,186,233,0.4);
                        box-shadow: 0 10px 30px 0 rgba(95,186,233,0.4);
                        -webkit-border-radius: 5px 5px 5px 5px;
                        border-radius: 5px 5px 5px 5px;"
                        ><i class="fa fa-search"></i></button>
                    </div>
                    </br>
                    <div class="row rone">
                        <select v-model="sortVal" class=" form-control jk sel"  style="margin: 0.3em; width: 14.2em;">
                        <option value="manifestacija">Naziv manifestacije</option>
                        <option value="datum">Datum odrzavanja</option>
                        <option value="cena">Cena karte</option>
                        </select>

                        <select v-model="sortTip" class="form-control jk sel" style="margin: 0.3em; width: 14.2em;">
                        <option value="rastuce">Rastuce</option>
                        <option value="opadajuce">Opadajuce</option>
                        </select>

                        <button class="btn" style="margin: 1px; background-color:#1fb579; color:white"  @click="sort">Sortiraj</button>
                    </div>
                <div class="row rone">
                <div class="row" id="cards" style="margin-left: 1em; margin-top: 5em;" v-for="t in ticketsWithManifestations">
                    <div class="column" style="margin-bottom:1em;" width="350em">
                        <img class="card__image" :src="t.manifestation.slika" width="330em" height="350em">
                        <div class="card__content">
                            <h5 class="card-title">{{ t.manifestation.naziv }}</h5>
                            <p class="card-text">Datum: {{ t.manifestation.datum }}</p>
                            <p class="card-text">Cena: {{ t.cena }}</p>
                            <p class="card-text">Tip karte: {{t.tipKarte}}</p>
                            <p v-if="t.status" class="card-text">Status: rezervisana</p>
                            <p v-if="!t.status" class="card-text">Status: otkazana</p>
                        </div>
                        <div class="card__info">
                            <div v-if="t.status == true">
                                <button class="btn btn-danger" :id="t.id" @click="otkazi">Otkazi</button>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
                </div>
            </div>
        </div>
    </div>
`,
    
  });