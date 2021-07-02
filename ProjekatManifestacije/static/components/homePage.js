Vue.component("homePage", {
    name: "homePage",
    data: function () {
      return {
        prijavljen:false,
        username:"",
        role:""
    };
    },
    methods: {
        login() {
          },

          mounted: function(){
              this.username = window.localStorage.getItem('kIme');
              this.role = window.localStorage.getItem('role');
              
            },
  
    },
    template: ` 
    <div>
    </div>
      
      `
    ,
    
  });