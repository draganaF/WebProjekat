Vue.component("homePage", {
  name: "homePage",
  data: function () {
    return {


    };
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
                >Korisnicko ime</label
              >
              <div class="col-md-6">
                <input
                  type="text"
                  id="email"
                  class="form-control"
                  name="email"
                  v-model="email"
                 
                />
              </div>
            </div>

            <div class="form-group row">
              <label
                for="password"
                class="col-md-4 col-form-label text-md-right"
                >Lozinka</label
              >
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
                for="password_confirmation"
                class="col-md-4 col-form-label text-md-right"
                >Ponovljena lozinka</label
              >
              <div class="col-md-6">
                <input
                  type="password"
                  id="password_confirmation"
                  class="form-control"
                  name="password_confirmation"
                  v-model="password-confirmation"
                 
                />
              </div>
            </div>
            <div class="form-group row">
              <label
                for="name"
                class="col-md-4 col-form-label text-md-right"
                >Ime</label
              >
              <div class="col-md-6">
                <input
                  type="text"
                  id="name"
                  class="form-control"
                  name="name"
                  v-model="name"
                  
                />
              </div>
            </div>
            <div class="form-group row">
              <label
                for="lastName"
                class="col-md-4 col-form-label text-md-right"
                >Prezime</label
              >
              <div class="col-md-6">
                <input
                  type="text"
                  id="lastName"
                  class="form-control"
                  name="lastName"
                  
                />
              </div>
            </div>
            <div class="form-group row">
              <label
                for="date"
                class="col-md-4 col-form-label text-md-right"
                >Datum rodjenja</label
              >
              <div class="col-md-6">
               
              </div>
            </div>
            <div class="form-group row">
              <label
                for="pol"
                class="col-md-4 col-form-label text-md-right"
                >Pol</label
              >
              
              <div class="form-check">
                <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios1" value="option1">
                          <label class="form-check-label" for="exampleRadios1">
                            Muski
                          </label>
                          </div>
              <label></label>
              <div class="form-check">
                  <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios2" value="option2">
                  <label class="form-check-label" for="exampleRadios2">
                  Zenski
                </label>
            </div>
            </div>
            <div class="buttons col-md-1 offset-md-4">
              <button class="btn" style="margin: 1px; background-color:#1fb579; color:white" >
                Prijava
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
    `
  ,
  methods: {

  },
});