Vue.component("home-page", {
	data: function () {
		    return {
		      products: null
		    }
	},
	template: ` 
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css" integrity="sha256-mmgLkCYLUQbXn0B1SRqzHar6dCnv9oZFPEC1g1cwlkk=" crossorigin="anonymous" />
<div class="container">
    <div class="row align-items-center event-block no-gutters margin-40px-bottom" v-for="m in manifestacije">
        <div class="col-lg-5 col-sm-12">
            <div class="position-relative">
                <img src={{m.slika}} alt="">
                <div class="events-date">
                    <div class="font-size28">{{m.datum.getDayOfMounth()}}</div>
                    <div class="font-size14">{{m.datum.getMonth().getDisplayName(TextStyle.SHORT,Locale.ENGLISH)}}</div>
                </div>
            </div>
        </div>
        <div class="col-lg-7 col-sm-12">
            <div class="padding-60px-lr md-padding-50px-lr sm-padding-30px-all xs-padding-25px-all">
                <h5 class="margin-15px-bottom md-margin-10px-bottom font-size22 md-font-size20 xs-font-size18 font-weight-500">{{m.naziv}}</h5>
                <ul class="event-time margin-10px-bottom md-margin-5px-bottom">
                    <li><i class="far fa-clock margin-10px-right"></i>{{datum.toLocalTime()}}</li>
                    <li><i class="fas fa-map-marker-alt" margin-5px-right"></i> Location : {{m.lokacija.adresa}}</li>
                    <li><i class="fas fa-dollar-sign" margin-10px-right"></i> Price : {{m.cenaKarte}}</li>
                    <li><i class="fas fa-theater-masks" margin-10px-right"></i> Type : {{m.tipManifestacije}}</li>
                </ul>
            </div>
        </div>
    </div>	  
`
	, 
	methods : {
		/*addToCart : function (product) {
			axios
			.post('rest/proizvodi/add', {"id":''+product.id, "count":parseInt(product.count)})
			.then(response => (toast('Product ' + product.name + " added to the Shopping Cart")))
		}*/
	},
	mounted () {
        axios
          .get('rest/manifestacije/')
          .then(response => (this.manifestacije = response.data))
    },
});