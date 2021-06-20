const HomePage = {template: '<home-page></home-page>'}
const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/home', component: HomePage}
	  ]
});

var app = new Vue({
	router,
	el:'#homePage'
	
});
