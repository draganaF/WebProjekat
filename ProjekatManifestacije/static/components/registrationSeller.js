Vue.component("registrationSellerPage", {
    name: "registrationSellerPage",
    data: function () {
      return {
        name: null,
        lastName: null,
        email: null,
        password:null,
        passwordConfirmation: null,
        gender:null,
        date:null,
        showErrorMessage: false,
    };
    },
    methods: {
      formSubmit: function (e) {
        e.preventDefault();
        this.errors = null;
              if(!this.name || !this.lastName || !this.email || !this.password ||
                      !this.passwordConfirmation || !this.date 
                      || !this.gender){
                  this.showErrorMessage = true;
                  alert("Morate uneti sve podatke.")
                  e.preventDefault();
              }else if(this.passwordConfirmation  != this.password){
          this.showErrorMessage = true;
                  alert("Lozinke se moraju poklapati.")
                  e.preventDefault();
        }else{
          axios
          .post('/registrationSeller', {ime: this.name, 
                      prezime: this.lastName,
                      korisnickoIme : this.email,
                      lozinka: this.password,
                      pol : this.gender,
                      datumRodjenja : this.date
                      })
          .then(response => (	alert("Uspesno registrovan prodavac!")));
        }
  
        
      },
  
    },
    template: ` 
      <div class="row justify-content-center">
        <div class="col-md-6">
          <div class="card">
            <div class="card-header"  style="background-color:#1fb579; color:white">Registracija</div>
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
                  v-model="email"/>
              </div>
              </div>
              <div class="form-group row">
              <label
                for="password"
                class="col-md-4 col-form-label text-md-right">Lozinka</label>
              <div class="col-md-6">
                <input
                  type="password"
                  id="password"
                  class="form-control"
                  name="password"
                  v-model="password"/>
              </div>
              </div>
              <div class="form-group row">
                <label
                  for="password_confirmation"
                  class="col-md-4 col-form-label text-md-right"
                  >Ponovljena lozinka</label>
                <div class="col-md-6">
                  <input
                    type="password"
                    id="password_confirmation"
                    class="form-control"
                    name="password_confirmation"
                    v-model="passwordConfirmation"/>
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
                    v-model="name"/>
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
                    v-model="lastName"/>
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
                type="date" 
                name="date">
                </div>
                </div>
            <div class="form-group row">
                <label
                  for="pol"
                  class="col-md-4 col-form-label text-md-right"
                  >Pol</label>
                <div class="form-check">
                  <input class="form-check-input" type="radio" v-model="gender" value="muski">
                            <label class="form-check-label" >
                              Muski
                            </label>
                            </div>
                <label></label>
                <div class="form-check offset-md-1">
                    <input class="form-check-input" type="radio" v-model="gender" value="zenski" >
                    <label class="form-check-label">
                    Zenski
                  </label>
              </div>
              </div>
              <div class="buttons col-md-1 offset-md-4">
                <button class="btn" style="margin: 1px; background-color:#1fb579; color:white" >
                  Registruj se
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