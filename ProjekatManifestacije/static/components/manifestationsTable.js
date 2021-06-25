Vue.component("manifestationsTable", {
    name: "manifestationsTable",
    data: function () {
      return {
          manifestations:[],
          sortNaziv:"",
          sortCena:"",
          sortTip:"",
          sortStatus:"",
    };
    },
    mounted: function(){
        this.refresh();					
    },
    methods: {

        onPrihvati(event){
            choosenId = event.target.id;
              axios
                .post('/manifestationsActivation',{}, {params:{id:choosenId, activation:"p"}})
                .then((response) => {
                  alert("Uspesno prihavcena manifestacija! ");
                  this.manifestations = [];
                  this.refresh();
                  
                })
                .catch((err) => {
                  console.log(err);
                });
            
        },
        onOdbij(event){
            choosenId = event.target.id;
            axios
            .post('/manifestationsActivation',{}, {params:{id:choosenId, activation:"o"}})
            .then((response) => {
              alert("Manifestacija je odbijena! ");
              this.manifestations = [];
              this.refresh();
              
            })
            .catch((err) => {
              console.log(err);
            });
        },
        refresh(){
            axios.get('/manifestations')
		.then(response => {
            this.manifestations = response.data;
         
        });
        },
        sort(event){
            id = event.target.id;
            
            if(id === "naziv"){
                if(this.sortNaziv === "")
                    this.sortNaziv="ascending"
                else if(this.sortNaziv==="ascending")
                    this.sortNaziv="descending"
                else
                    this.sortNaziv="ascending"
                var temp;
                for(var i=0;i<this.manifestations.length;i++){
                    for(var j=0;j<this.manifestations.length-1;j++){
                        if(this.sortNaziv ==="ascending"){
                            if(this.manifestations[j].naziv < this.manifestations[j+1].naziv){
                                temp = this.manifestations[j];
                                this.manifestations[j] = this.manifestations[j+1];
                                this.manifestations[j+1] = temp;  
                            }
                        }
                        else{
                            if(this.manifestations[j].naziv > this.manifestations[j+1].naziv){
                                temp = this.manifestations[j];
                                this.manifestations[j] = this.manifestations[j+1];
                                this.manifestations[j+1] = temp;
                            }
                        }
                    }
                }
            }
            else if(id === "tip"){
                if(this.sortTip === "")
                    this.sortTip = "ascending"
                else if(this.sortTip === "ascending")
                    this.sortTip = "descending"
                else
                    this.sortTip = "ascending"
                var temp;
                for(var i=0;i<this.manifestations.length;i++){
                    for(var j=0;j<this.manifestations.length-1;j++){
                        if(this.sortTip ==="ascending"){
                            
                            if(this.manifestations[j].tipManifestacije < this.manifestations[j+1].tipManifestacije){
                                temp = this.manifestations[j];
                                this.manifestations[j] = this.manifestations[j+1];
                                this.manifestations[j+1] = temp;
                            }
                            
                        }
                        else{
                            if(this.manifestations[j].tipManifestacije > this.manifestations[j+1].tipManifestacije){
                                temp = this.manifestations[j];
                                this.manifestations[j] = this.manifestations[j+1];
                                this.manifestations[j+1] = temp;
                            }
                        }
                    }
                }
            }
            else if(id === "status"){
                if(this.sortStatus === "")
                    this.sortStatus = "ascending"
                else if(this.sortStatus === "ascending")
                    this.sortStatus = "descending"
                else
                    this.sortStatus = "ascending"
                var temp;
                for(var i=0;i<this.manifestations.length;i++){
                    for(var j=0;j<this.manifestations.length-1;j++){
                        if(this.sortStatus ==="ascending"){
                            
                            if(this.manifestations[j].status < this.manifestations[j+1].status){
                                temp = this.manifestations[j];
                                this.manifestations[j] = this.manifestations[j+1];
                                this.manifestations[j+1] = temp;
                            }
                        }
                        else{
                            if(this.manifestations[j].status > this.manifestations[j+1].status){
                                temp = this.manifestations[j];
                                this.manifestations[j] = this.manifestations[j+1];
                                this.manifestations[j+1] = temp;
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
                for(var i=0;i<this.manifestations.length;i++){
                    for(var j=0;j<this.manifestations.length-1;j++){
                        if(this.sortCena === "ascending"){
                            
                            if(parseInt(this.manifestations[j].cenaKarte) < parseInt(this.manifestations[j+1].cenaKarte)){
                                temp = this.manifestations[j];
                                this.manifestations[j] = this.manifestations[j+1];
                                this.manifestations[j+1] = temp;
                               
                            }
                            
                        }
                        else{
                            if(parseInt(this.manifestations[j].cenaKarte) > parseInt(this.manifestations[j+1].cenaKarte)){
                                temp = this.manifestations[j];
                                this.manifestations[j] = this.manifestations[j+1];
                                this.manifestations[j+1] = temp;
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
                <div class="card-header row justify-content-center"  style="background-color:#1fb579; color:white">Manifestacije</div>
                <div class="card-body">
                    <table class="table table-bordered col-md-6"> 
                        <tr>
                            <th @click="sort" v-bind:class="sortNaziv" id="naziv">Naziv manifestacije </th>
                            <th @click="sort" v-bind:class="sortTip" id="tip">Tip manifestacije</th>
                            <th @click="sort" v-bind:class="sortCena" id="cena">REGULAR Cena</th>
                            <th>Datum </th>
                            <th @click="sort" v-bind:class="sortStatus" id="status">Status</th>
                        </tr>
                        <tr v-for="m in manifestations">
                            <td> {{m.naziv}} </td>
                            <td> {{m.tipManifestacije}}</td>
                            <td> {{m.cenaKarte}}</td>
                            <td> {{m.datum}} </td>
                            <td v-if="m.aktivnost == true && m.obrisana == false">Prihvacena</td>
                            <td v-if="m.obrisana != false && m.aktivnost != true"> Odbijena</td>
                            <td v-if="m.obrisana == false && m.aktivnost == false"> Ceka odgovor</td>
                            <td><button v-if="m.aktivnost == false && m.obrisana == false" class="btn btn-success" @click="onPrihvati" :id="m.id">Prihvati</button> <button v-if="m.aktivnost == false && m.obrisana ==false" class="btn btn-danger" @click="onOdbij" :id="m.id">Odbij</button></td>
   
                            </tr>

                    </table>
          
                </div>
            </div>
        </div>
    </div>
`
    ,
    
  });