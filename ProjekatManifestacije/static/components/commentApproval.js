Vue.component("comment-approval", {
  name: "comment-approval",
  data: function() {
    return {
      comments: [],
      manifestations: [],
      approved: -1,
      user: localStorage.getItem('kIme'),
      odbij: true,
      prihvati: true,
    }
  },
  mounted: function() {
    console.log(this.user);
    axios.get("/commentsToApprove?username=" + this.user)
      .then( response => {
        this.comments = response.data;
        this.ucitajManifestacije();
      })
  },
  methods: {
    ucitajManifestacije: function() {
      axios.get("/manifestations")
			.then(response => {
				this.manifestations = response.data;
			});
    },
    odaberiManifestaciju: function(id) {
      for(let i = 0; i < this.manifestations.length; i++) {
        console.log(this.manifestations[i].id + "   "+ id);
        if(this.manifestations[i].id == id) {
          return this.manifestations[i].naziv;
        }
      }
    },
    odobriKomentar: function(event) {
      var id = event.target.id;
      axios.get("/approveComment?id=" + id + "&odobrena=" + 1)
        .then((response => {
          console.log(response.data); 
          alert("Uspesno ste odobrili komentar");
          this.ucitajPodatke();
          
        }))
    },
    odbijKomentar: function(event) {
      var id = event.target.id;
      axios.get("/approveComment?id=" + id + "&odobrena=" + 0)
      .then((response => {
          console.log(response.data);
          alert("Uspesno ste odbili komentar");
          this.ucitajPodatke();
      }))
    },
    ucitajPodatke: function() {
      axios.get("/commentsToApprove?username=" + this.user)
      .then( response => {
        this.comments = response.data;
      })
    }
  },
  template: 
    ` <div class="row justify-content-center">
        <div class="col-md-11">
            <div class="card col-md-11">
                <div class="card-header row justify-content-center"  style="background-color:#1fb579; color:white">Komentari</div>
                <div class="card-body">
                    <table class="table table-bordered col-md-6"> 
                        <tr>
                            <th id="naziv">Kupac</th>
                            <th id="tip">Sadrzaj</th>
                            <th id="cena">Ocena</th>
                            <th id="manifestacija">Manifestacija</th>
                            <th>Odobri </th>
                        </tr>
                        <tr v-for="c in comments">
                            <td> {{c.kupac}} </td>
                            <td> {{c.tekst}}</td>
                            <td> {{c.ocena}}</td>
                            <td> {{odaberiManifestaciju(c.manifestacija)}}
                            <td><button class="btn btn-success" width="10em" @click="odobriKomentar" :id="c.id">Prihvati</button> <button  class="btn btn-danger" width="100em" @click="odbijKomentar" :id="c.id">Odbij</button></td>
   
                            </tr>

                    </table>
          
                </div>
            </div>
        </div>
    </div>
`
    ,
   
});