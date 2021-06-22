const Registration = {template: '<registration-page></registration-page>'}
const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/reg', component: Registration}
	  ]
});

var app = new Vue({
	router,
	el:'#registrationPage'
	
});
