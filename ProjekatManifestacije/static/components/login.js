Vue.component("login", {
    name: "login",
    data: function () {
      return {
        username: null,
        password:null,
        showErrorMessage: false,
    };
    },
    methods: {
        login(e) {

            axios
              .post('/login',{},{params:{korisnickoIme:this.username,lozinka:this.password}}
              )
	          .then(function(response){
                  
                if(JSON.parse(JSON.stringify(response.data))[0]===" "){
                    alert("Pogresno korisnicko ime ili lozinka");
                    
                }else if(JSON.parse(JSON.stringify(response.data))[0]==="blokiran"){
                    alert("Vas nalog je blokiran");
                    
                }
                else{
                    localStorage.setItem('kIme', JSON.parse(JSON.stringify(response.data))[0]);
                    localStorage.setItem("role", JSON.parse(JSON.stringify(response.data))[1]);

                }
              });
               
          },
  
    },
    template: 
    ` <div class="wrapper fadeInDown">
        <div id="formContent">
            <form @submit="login" action="#/manifestations">
                <input type="text" id="login" class="fadeIn second" name="login" placeholder="Korisnicko ime" v-model="username">
                <input type="password" id="password" class="fadeIn third" name="login" placeholder="Lozinka" v-model="password">
                <input type="submit" value="Prijavi se">
            </form>
        </div>
    </div>   `
    ,
    
  });