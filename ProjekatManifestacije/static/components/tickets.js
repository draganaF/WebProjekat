Vue.component("tickets", {
    name: "tickets",
    data: function () {
      return {
        tickets:[],
        manifestations:[],
        ticketsWithManifestations:[],
        pretragaManifestacija:"",
        status:"",
        tip:"",
        sortManifestacija:"",
        sortCena:"",
        sortDatum:"",
        kIme:"",
        role:"",
        pretragaDatumOd:"",
        pretragaDatumDo:"",
        pretragaCenaOd:"",
        pretragaCenaDo:"",
    };
    },
    mounted: function(){
        this.kIme = window.localStorage.getItem('kIme');
        this.role = window.localStorage.getItem('role');
        console.log(this.role);
        this.refresh();					
    },
    methods: {

        onDelete(event){
            var id = event.target.id;
            for(var i =0;i<this.tickets.length;i++){
                if(this.tickets[i].id == id){
                    axios
                .post('/deleteTicket',{}, {params:{id:id}})
                .then((response) => {
                  alert("Uspesno obrisana karta! ");
                  this.tickets = [];
                  this.manifestations = [];
                  this.ticketsWithManifestations = [];
                  this.refresh();
                  
                })
                .catch((err) => {
                  console.log(err);
                });
                }
            }
        },
        refresh(){
            if(this.role == 'admin'){
                axios.get('/tickets')
		        .then(response => {
                    this.tickets = response.data;
                    this.getManifestations();
                    
            });
        }else if(this.role == 'prodavac'){
            axios.get('/reservedTickets',  {params:{korisnickoIme:this.kIme}})
		        .then(response => {
                    this.tickets = response.data;
                    this.getManifestations();
                    
            });
        }
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
                
                    axios.get('/searchTickets', {params:{role:this.role,korisnickoIme:this.kIme,pretragaManifestacija:this.pretragaManifestacija, pretragaCenaOd:this.pretragaCenaOd, pretragaCenaDo:this.pretragaCenaDo, pretragaDatumOd:this.pretragaDatumOd, pretragaDatumDo:this.pretragaDatumDo, status:this.status, tip:this.tip}})
                    .then(response => {
                        this.tickets = response.data;
                        this.getManifestations();
                });
            }

        },
        sort(event){
            id = event.target.id;
            if(id === "manifestacija"){
                if(this.sortManifestacija === "")
                    this.sortManifestacija="ascending"
                else if(this.sortManifestacija==="ascending")
                    this.sortManifestacija="descending"
                else
                    this.sortManifestacija="ascending"
                var temp;
                for(var i=0;i<this.ticketsWithManifestations.length;i++){
                    for(var j=0;j<this.ticketsWithManifestations.length-1;j++){
                        if(this.sortManifestacija ==="ascending"){
                            if(this.ticketsWithManifestations[j].manifestation.naziv  < this.ticketsWithManifestations[j+1].manifestation.naziv ){
                                temp = this.ticketsWithManifestations[j];
                                this.ticketsWithManifestations[j] = this.ticketsWithManifestations[j+1];
                                this.ticketsWithManifestations[j+1] = temp;  
                            }
                        }
                        else{
                            if(this.ticketsWithManifestations[j].manifestation.naziv > this.ticketsWithManifestations[j+1].manifestation.naziv ){
                                temp = this.ticketsWithManifestations[j];
                                this.ticketsWithManifestations[j] = this.ticketsWithManifestations[j+1];
                                this.ticketsWithManifestations[j+1] = temp;
                            }
                        }
                    }
                }
            }
            else if(id === "datum"){
                if(this.sortDatum === "")
                    this.sortDatum = "ascending"
                else if(this.sortDatum === "ascending")
                    this.sortDatum = "descending"
                else
                    this.sortDatum = "ascending"
                var temp;
                for(var i=0;i<this.ticketsWithManifestations.length;i++){
                    for(var j=0;j<this.ticketsWithManifestations.length-1;j++){
                        if(this.sortDatum ==="ascending"){
                            
                            if( Date.parse(this.ticketsWithManifestations[j].manifestation.datum )< Date.parse(this.ticketsWithManifestations[j+1].manifestation.datum)){
                                temp = this.ticketsWithManifestations[j];
                                this.ticketsWithManifestations[j] = this.ticketsWithManifestations[j+1];
                                this.ticketsWithManifestations[j+1] = temp;
                            }
                            
                        }
                        else{
                            if(Date.parse(this.ticketsWithManifestations[j].manifestation.datum) > Date.parse(this.ticketsWithManifestations[j+1].manifestation.datum)){
                                temp = this.ticketsWithManifestations[j];
                                this.ticketsWithManifestations[j] = this.ticketsWithManifestations[j+1];
                                this.ticketsWithManifestations[j+1] = temp;
                            }
                        }
                    }
                }
            }
            else {
                if(this.sortCena === "")
                    this.sortCena = "ascending"
                else if(this.sortCena === "ascending")
                    this.sortCena = "descending"
                else
                    this.sortCena = "ascending"
                var temp;
                for(var i=0;i<this.ticketsWithManifestations.length;i++){
                    for(var j=0;j<this.ticketsWithManifestations.length-1;j++){
                        if(this.sortCena === "ascending"){
                            
                            if(parseInt(this.ticketsWithManifestations[j].cena) < parseInt(this.ticketsWithManifestations[j+1].cena)){
                                temp = this.ticketsWithManifestations[j];
                                this.ticketsWithManifestations[j] = this.ticketsWithManifestations[j+1];
                                this.ticketsWithManifestations[j+1] = temp;
                               
                            }
                            
                        }
                        else{
                            if(parseInt(this.ticketsWithManifestations[j].cena) > parseInt(this.ticketsWithManifestations[j+1].cena)){
                                temp = this.ticketsWithManifestations[j];
                                this.ticketsWithManifestations[j] = this.ticketsWithManifestations[j+1];
                                this.ticketsWithManifestations[j+1] = temp;
                            }
                        }
                    }
                }
            }
            
            
        }
    },
    template: 
    ` <div class="row justify-content-center">
        <div class="col-md-11">
            <div class="card col-md-11">
                <div class="card-header row justify-content-center"  style="background-color:#1fb579; color:white">Karte</div>
                <div class="card-body">
                <div class="row rone">
                            <input  class="form-control jk" type="text"  v-model="pretragaManifestacija" placeholder="Pretraga po manifestaciji" style="margin: 0.3em; width: 12em;"></input>
                            <input  class="form-control jk" type="date"  v-model="pretragaDatumOd" placeholder="Datum od" style="margin: 0.3em; width: 12em;"></input>
                            <input class="form-control jk" type="date"  v-model="pretragaDatumDo" placeholder="Datum do" style="margin: 0.3em; width: 12em;"></input>
                            <input  class="form-control jk" type="text"  v-model="pretragaCenaOd" placeholder="Cena od" style="margin: 0.3em; width: 12em;"></input>
                            <input  class="form-control jk" type="text"  v-model="pretragaCenaDo" placeholder="Cena do" style="margin: 0.3em; width: 12em;"></input>
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
                    <table class="table table-bordered col-md-6"> 
                        <tr>
                            <th @click="sort" v-bind:class="sortManifestacija" id="manifestacija">Naziv manifestacije </th>
                            <th @click="sort" v-bind:class="sortCena" id="cena">Cena</th>
                            <th @click="sort" v-bind:class="sortDatum" id="datum">Datum</th>
                            <th>Tip karte </th>
                            <th>Status karte </th>
                        </tr>
                        <tr v-for="t in ticketsWithManifestations">
                            <td>{{t.manifestation.naziv}} </td>
                            <td> {{t.cena}} </td>
                            <td> {{t.manifestation.datum}}</td>
                            <td> {{t.tipKarte}} </td>
                            <td v-if="t.status == false"> Otkazana </td>
                            <td v-if="t.status == true"> Rezervisana </td>
                            <td v-if="role =='admin' && t.status == false"> <button v-on:click="onDelete" :id="t.id" class="btn btn-danger" > Obrisi</button></td>
                        </tr>

                    </table>
          
                </div>
            </div>
        </div>
    </div>
`,
    
  });