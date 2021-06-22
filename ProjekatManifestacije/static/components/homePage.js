Vue.component("homePage", {
    name: "homePage",
    data: function () {
      return {
        username: null,
        password:null,
        showErrorMessage: false,
    };
    },
    methods: {
        login() {
          },
  
    },
    template: ` 
    <div>
        <ul>
            <li><a class="active" href="tryit.asp-filename=trycss_navbar_horizontal_black.html#home">Pocetna stranica</a></li>
            <li><a href="tryit.asp-filename=trycss_navbar_horizontal_black.html#news">News</a></li>
            <li><a href="tryit.asp-filename=trycss_navbar_horizontal_black.html#contact">Contact</a></li>
            <li><a href="tryit.asp-filename=trycss_navbar_horizontal_black.html#about">About</a></li>
        </ul>
    </div>
    
  
      
      `
    ,
    
  });