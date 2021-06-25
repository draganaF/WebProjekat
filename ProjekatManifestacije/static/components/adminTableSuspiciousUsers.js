Vue.component("adminTableSuspiciousUsers", {
    name: "adminTableSuspiciousUsers",
    data: function () {
      return {
        users:[],
        pretraga:"",
        tip:"",
        sortIme:"",
        sortPrezime:"",
        sortKIme:"",
        sortBodovi:"",
    };
    },
    mounted: function(){
        this.refresh();					
    },
    methods: {

        onDelete(event){
            username = event.target.id;
            for(var i =0;i<this.users.length;i++){
                if(this.users[i].username == username && this.users[i].role =="administrator"){
                    alert("Ne mozete da obrisete administratora!");
                }else if(this.users[i].username == username){
                    axios
                .post('/delete',{}, {params:{korisnickoIme:username}})
                .then((response) => {
                  alert("Uspesno obrisan korisnik! ");
                  this.users = [];
                  this.refresh();
                  
                })
                .catch((err) => {
                  console.log(err);
                });
                }
            }
        },
        refresh(){
        axios.get('/suspiciousUsers')
		.then(response => {
           
            for(var i =0;i< response.data.length;i++){
                var user = {};
                user.name = response.data[i].ime;
                user.lastName = response.data[i].prezime;
                user.username = response.data[i].korisnickoIme;
                user.sumnjiv = response.data[i].sumnjiv;
                user.blokiran = response.data[i].blokiran;
                user.points = response.data[i].brojSakupljenihBodova;
                user.type= response.data[i].tipKupca.tip;
                this.users.push(user);
            }
         
        });
        },
        search(){
            this.users = [];
            if(this.tip == "" && this.pretraga ==""){
                this.refresh();
            }else if(this.tip != "" ){
                axios.get('/searchSuspiciousK', {params:{pretraga:this.pretraga, tip:this.tip}})
                .then(response => {
                for(var i =0;i< response.data.length;i++){
                    var user = {};
                    user.name = response.data[i].ime;
                    user.lastName = response.data[i].prezime;
                    user.username = response.data[i].korisnickoIme;
                    user.role = "kupac";
                    user.points = response.data[i].brojSakupljenihBodova;
                    user.type= response.data[i].tipKupca.tip;
                    user.sumnjiv = response.data[i].sumnjiv;
                    user.blokiran = response.data[i].blokiran;
                    this.users.push(user);
                }
             
            });
            }else{
            axios.get('/searchSuspiciousK', {params:{pretraga:this.pretraga, tip:this.tip}})
            .then(response => {
                for(var i =0;i< response.data.length;i++){
                    var user = {};
                    user.name = response.data[i].ime;
                    user.lastName = response.data[i].prezime;
                    user.username = response.data[i].korisnickoIme;
                    user.role = "kupac";
                    user.points = response.data[i].brojSakupljenihBodova;
                    user.type= response.data[i].tipKupca.tip;
                    user.sumnjiv = response.data[i].sumnjiv;
                    user.blokiran = response.data[i].blokiran;
                    this.users.push(user);
                }
             
            });
        }
        },
        sort(event){
            id = event.target.id;
            
            if(id === "kIme"){
                if(this.sortKIme === "")
                    this.sortKIme="ascending"
                else if(this.sortKIme==="ascending")
                    this.sortKIme="descending"
                else
                    this.sortKIme="ascending"
                var temp;
                for(var i=0;i<this.users.length;i++){
                    for(var j=0;j<this.users.length-1;j++){
                        if(this.sortKIme ==="ascending"){
                            if(this.users[j].username < this.users[j+1].username){
                                temp = this.users[j];
                                this.users[j] = this.users[j+1];
                                this.users[j+1] = temp;  
                            }
                        }
                        else{
                            if(this.users[j].username > this.users[j+1].username){
                                temp = this.users[j];
                                this.users[j] = this.users[j+1];
                                this.users[j+1] = temp;
                            }
                        }
                    }
                }
            }
            else if(id === "ime"){
                if(this.sortIme === "")
                    this.sortIme = "ascending"
                else if(this.sortIme === "ascending")
                    this.sortIme = "descending"
                else
                    this.sortIme = "ascending"
                var temp;
                for(var i=0;i<this.users.length;i++){
                    for(var j=0;j<this.users.length-1;j++){
                        if(this.sort_name ==="ascending"){
                            
                            if(this.users[j].name < this.users[j+1].name){
                                temp = this.users[j];
                                this.users[j] = this.users[j+1];
                                this.users[j+1] = temp;
                            }
                            
                        }
                        else{
                            if(this.users[j].name > this.users[j+1].name){
                                temp = this.users[j];
                                this.users[j] = this.users[j+1];
                                this.users[j+1] = temp;
                            }
                        }
                    }
                }
            }
            else if(id === "prezime"){
                if(this.sortPrezime === "")
                    this.sortPrezime = "ascending"
                else if(this.sortPrezime === "ascending")
                    this.sortPrezime = "descending"
                else
                    this.sortPrezime = "ascending"
                var temp;
                for(var i=0;i<this.users.length;i++){
                    for(var j=0;j<this.users.length-1;j++){
                        if(this.sortPrezime ==="ascending"){
                            
                            if(this.users[j].lastName < this.users[j+1].lastName){
                                temp = this.users[j];
                                this.users[j] = this.users[j+1];
                                this.users[j+1] = temp;
                            }
                        }
                        else{
                            if(this.users[j].lastName > this.users[j+1].lastName){
                                temp = this.users[j];
                                this.users[j] = this.users[j+1];
                                this.users[j+1] = temp;
                            }
                        }
                    }
                }
            }
            else {
                if(this.sortBodovi === "")
                    this.sortBodovi = "ascending"
                else if(this.sortBodovi === "ascending")
                    this.sortBodovi = "descending"
                else
                    this.sortBodovi = "ascending"
                var temp;
                for(var i=0;i<this.users.length;i++){
                    for(var j=0;j<this.users.length-1;j++){
                        if(this.sortBodovi === "ascending"){
                            
                            if(parseInt(this.users[j].points) < parseInt(this.users[j+1].points)){
                                temp = this.users[j];
                                this.users[j] = this.users[j+1];
                                this.users[j+1] = temp;
                               
                            }
                            
                        }
                        else{
                            if(parseInt(this.users[j].points) > parseInt(this.users[j+1].points)){
                                temp = this.users[j];
                                this.users[j] = this.users[j+1];
                                this.users[j+1] = temp;
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
                <div class="card-header row justify-content-center"  style="background-color:#1fb579; color:white">Sumnjivi korisnici</div>
                <div class="card-body">
                <div class="row">
                    <div class="col-4">
                        <form @submit="search">
                            <input type="text"  v-model="pretraga" placeholder="Pretraga"></input>
                            <button class="s" style=" background-color: #56baed;
                            border: none;
                            color: white;
                            text-decoration: none;
                            padding: 15px 10px;
                            -webkit-box-shadow: 0 10px 30px 0 rgba(95,186,233,0.4);
                            box-shadow: 0 10px 30px 0 rgba(95,186,233,0.4);
                            -webkit-border-radius: 5px 5px 5px 5px;
                            border-radius: 5px 5px 5px 5px;"
                            ><i class="fa fa-search"></i></button>
                        </form>
                    </div>
                    <div class="col-4">
                        <select v-model="tip" class="sel" @change="search">
                        <option value="" selected>Svi</option>
                        <option value="OBICAN" >Obican</option>
                        <option value="SREBRNI">Srebrni</option>
                        <option value="BRONZANI" >Bronzani</option>
                        <option value="ZLATNI" >Zlatni</option>
                        </select>
                    </div>
                </div>
                    <table class="table table-bordered col-md-6"> 
                        <tr>
                            <th @click="sort" v-bind:class="sortIme" id="ime">Ime </th>
                            <th @click="sort" v-bind:class="sortPrezime" id="prezime">Prezime</th>
                            <th @click="sort" v-bind:class="sortKIme" id="kIme">Korisnicko ime</th>
                            <th>Tip kupca </th>
                            <th @click="sort" v-bind:class="sortBodovi" id="bodovi">Bodovi </th>
                            <th> </th>
                            <th> </th>
                        </tr>
                        <tr v-for="user in users">
                            <td>{{user.name}} </td>
                            <td> {{user.lastName}}</td>
                            <td> {{user.username}}</td>
                            <td> {{user.type}} </td>
                            <td> {{user.points}} </td>
                            <td> <button @click="onDelete" :id="user.username" class="btn btn-danger" > Obrisi</button></td>
                            <td v-if="user.blokiran == false"> <button  :id="user.username" class="btn btn-primary" > Blokiraj</button></td>
                            <td v-if="user.blokiran == true"> <button :disabled=true  :id="user.username" class="btn btn-primary" > Blokiraj</button></td>
                        </tr>

                    </table>
          
                </div>
            </div>
        </div>
    </div>
`
    ,
    
  });