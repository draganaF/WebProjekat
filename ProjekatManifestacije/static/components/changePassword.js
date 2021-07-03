Vue.component("changePassword", {
    name: "changePassword",
    data: function () {
      return {
        password:null,
        confirmationPassword:null,
        newPassword:null,
        showErrorMessage: false,
        kIme: null,
        backup: {},

    };
    },

    mounted: function(){
        this.kIme = window.localStorage.getItem('kIme');
        axios.get('/profile?korisnickoIme='+this.kIme)
		.then(response => {
            this.backup.password = response.data.lozinka;
            this.backup.email = response.data.korisnickoIme;
            this.backup.name = response.data.ime;
            this.backup.lastName = response.data.prezime;
            this.backup.date = response.data.datumRodjenja;
            this.backup.gender = response.data.pol;
        });
							
    },
    methods: {
        formSubmit:function(e){
            e.preventDefault();
            if ( this.password != this.backup.password
              ) {
                alert("Pogresna stara lozinka!");
                return;
              }else if(this.newPassword != this.confirmationPassword)
              {
                alert("Lozinke se ne poklapaju!");
                return;
              }
              
              else{
                axios
                .post('/changePassword',{lozinka: this.newPassword
                }, {params:{korisnickoIme:this.kIme}})
                .then((response) => {
                  alert("Uspesno ste izmenili lozinku! ");
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
                    <div class="card-header"  style="background-color:#1fb579; color:white">Promena lozinke</div>
                        <div class="card-body">
          <form name="myform" @submit="formSubmit">
          <div class="form-group row">
          <label
            for="password"
            class="col-md-4 col-form-label text-md-right"
            >Stara lozinka</label>
          <div class="col-md-6">
            <input
              type="password"
              id="password"
              class="form-control"
              name="password"
              v-model="password"
              />
          </div>
          </div>
          <div class="form-group row">
            <label
              for="newPass"
              class="col-md-4 col-form-label text-md-right"
              >Nova lozinka</label>
            <div class="col-md-6">
              <input
                type="password"
                id="newPass"
                class="form-control"
                name="newPass"
                v-model="newPassword"
                />
            </div>
          </div>
          <div class="form-group row">
            <label
              for="confPass"
              class="col-md-4 col-form-label text-md-right"
              >Ponovljena lozinka</label>
            <div class="col-md-6">
              <input
                type="password"
                id="confPass"
                class="form-control"
                name="confPass"
                v-model="confirmationPassword"/>
            </div>
          </div>
          <div class="buttons col-md-1 offset-md-4">
              <button class="btn" style="margin: 1px; background-color:#1fb579; color:white" >
                Sacuvaj promene
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>

  
   `
    ,
    
  });