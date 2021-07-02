Vue.component("header-page", {
    data: function () {
      return {
          activeUser:false,
          role:"",
          username:"",
      };
    },
    methods: {

        logout: function (event) {
            event.preventDefault();
              this.activeUser = false;
              localStorage.removeItem("role");
              localStorage.removeItem("kIme");
              router.replace({ path: `/manifestations` })
			
          },
    },
    mounted: function () {
        this.username = window.localStorage.getItem('kIme');
	    this.role = window.localStorage.getItem('role');
        console.log(this.role);
        if(this.role =="admin" || this.role == "prodavac" || this.role=="kupac"){
            this.activeUser = true;
        }
    },
    template: ` 
    <nav style="min-height:10vh; background-color:#1fb579" class="container-fluid navbar navbar-expand-md mb-4">
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav me-auto mb-2 mb-md-0">
          <li class="nav-item active">
            <a class="btn btn-outline-light" href="/#/manifestations">Pocetna stranica</a>
          </li>
          <li v-if="activeUser == true && role =='admin'" class="nav-item active">
            <a class="btn btn-outline-light" href="/#/registrationSeller">Registracija prodavca</a>
          </li>
          <li v-if="activeUser == true && role =='admin' " class="nav-item active">
            <a class="btn btn-outline-light" href="/#/adminTableUsers">Pregled korisnika</a>
          </li>
          <li v-if="activeUser == true && role =='admin'" class="nav-item active">
            <a class="btn btn-outline-light" href="/#/adminSuspiciousUsers">Pregled sumnjivih</a>
          </li>
          <li v-if="activeUser == true && (role =='admin' |  role =='prodavac')"  class="nav-item active">
            <a class="btn btn-outline-light" href="/#/manifestationsAdmin">Pregled manifestacija</a>
          </li>
          <li v-if="activeUser == true && (role =='admin' | role =='prodavac')"  class="nav-item active">
            <a class="btn btn-outline-light" href="/#/tickets">Karte</a>
          </li>
          <li v-if="activeUser == true && role =='prodavac' " class="nav-item active">
            <a class="btn btn-outline-light" href="/#/sellerTable">Pregled korisnika</a>
          </li>
          <li v-if="activeUser == true && role =='prodavac' " class="nav-item active">
            <a class="btn btn-outline-light" href="/#/approveComments">Odobravanje komentara</a>
        </li>
        <li v-if="activeUser == true && role =='kupac'"  class="nav-item active">
            <a class="btn btn-outline-light" href="/#/ticketUser">Karte</a>
          </li>
        </ul>
        <ul class="nav navbar-nav ml-auto" style="left: auto !important;
        right: 0px;">
            <li v-if="activeUser == true" class="nav-item active">
                <a class="btn btn-outline-light" href="/#/profile">Profil</a>
          </li>
          <li v-if="activeUser == true" class="nav-item active">
                <a class="btn btn-outline-light" href="/#/changePassword">Promena lozinke</a>
          </li>
            <li v-if="activeUser!=true" class="nav-item active right">
                <a class="btn btn-outline-light" href="/#/reg">Registracija</a>
            </li>
            <li v-if="activeUser != true" class="nav-item active">
                <a class="btn btn-outline-light" href="/#/login">Prijava</a>
            </li>
            <li v-if="activeUser == true"  class="nav-item active">
                <button @click="logout" type="button" class="btn btn-outline-light">Odjava</button>
            </li>
        </ul>
        
      </div>
  </nav>
    `,
  });
  