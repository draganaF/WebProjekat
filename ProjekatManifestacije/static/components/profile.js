Vue.component("profile", {
    name: "profile",
    data: function () {
      return {
        email: null,
        password:null,
        name: null,
        lastName:null,
        date:null,
        gender:null,
        pol:null,
        showErrorMessage: false,
        kIme: null,
        d:true,
        backup: {},

    };
    },

    mounted: function(){
        this.kIme = window.localStorage.getItem('kIme');
        axios.get('/profile?korisnickoIme='+this.kIme)
		.then(response => {
            this.email = response.data.korisnickoIme;
            this.name = response.data.ime;
            this.lastName = response.data.prezime;
            if(response.data.pol == "MUSKI"){
                this.gender = "muski";
            }else{
                this.gender = "zenski";
            }
            this.date = response.data.datumRodjenja;

        });
							
    },
    methods: {

        onEdit:function(){
            this.d = false;
            this.backup.name = this.name;
            this.backup.lastName = this.lastName;
            this.backup.gender = this.gender;
        },

        onCancel: function(){
            this.d = true;
            this.name = this.backup.name;
            this.lastName = this.backup.lastName;
            this.gender = this.backup.gender;
        },

        formSubmit:function(e){
            e.preventDefault();
            if ( this.name == this.backup.name &&
                this.lastName == this.backup.lastName &&
                this.gender == this.backup.gender
              ) {
                alert("Niste nista izmenili!");
                return;
              }else{
                axios
                .post('/updateInfo',{ime: this.name, 
                    prezime: this.lastName,
                    korisnickoIme : this.email,
                    lozinka: this.password,
                    pol : this.gender,
                    datumRodjenja : this.date
                    }, {params:{korisnickoIme:this.email}})
                .then((response) => {
                  alert("Uspesno ste izmenili podatke! ");
                  this.backup = {};
                })
                .catch((err) => {
                  console.log(err);
                });
              }

        }
  
    },
    template: 
    `  <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header"  style="background-color:#1fb579; color:white">Profil</div>
                        <div class="card-body">
          <form name="myform" @submit="formSubmit">
          <div class="form-group row">
          <label
            for="email"
            class="col-md-4 col-form-label text-md-right"
            >Korisnicko ime</label>
          <div class="col-md-6">
            <input
              type="text"
              id="email"
              class="form-control"
              name="email"
              v-model="email"
              disabled=true
              />
          </div>
          </div>
          <div class="form-group row">
            <label
              for="name"
              class="col-md-4 col-form-label text-md-right"
              >Ime</label>
            <div class="col-md-6">
              <input
                type="text"
                id="name"
                class="form-control"
                name="name"
                v-model="name"
                v-bind:disabled="d"/>
            </div>
          </div>
          <div class="form-group row">
            <label
              for="lastName"
              class="col-md-4 col-form-label text-md-right"
              >Prezime</label>
            <div class="col-md-6">
              <input
                type="text"
                id="lastName"
                class="form-control"
                name="lastName"
                v-model="lastName"
                v-bind:disabled="d"/>
            </div>
          </div>
          <div class="form-group row">
          <label
            for="date"
            class="col-md-4 col-form-label text-md-right"
            >Datum rodjenja</label>
            <div class="col-md-6">
            <input 
            v-model="date" 
            class="form-control form-control-user"
            type="text" 
            name="date"
            disabled=true>
            </div>
            </div>
        <div class="form-group row">
            <label
              for="pol"
              class="col-md-4 col-form-label text-md-right"
              >Pol</label>
            <div class="form-check">
              <input class="form-check-input" type="radio" v-model="gender" value="muski" v-bind:disabled="d">
                        <label class="form-check-label" >
                          Muski
                        </label>
                        </div>
            <label></label>
            <div class="form-check offset-md-1">
                <input class="form-check-input" type="radio" v-model="gender" value="zenski"  v-bind:disabled="d">
                <label class="form-check-label">
                Zenski
              </label>
          </div>
          </div>
          <div class="set-buttons">
          <div>
              <button @click="onEdit" class="btn btn-primary" type="button">Izmeni</button>
              <button @click="onCancel" class="btn btn-danger" type="button">Odustani</button>
          </div>
          <button class="btn btn-success" type="submit">Sacuvaj</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>

  
   `
    ,
    
  });