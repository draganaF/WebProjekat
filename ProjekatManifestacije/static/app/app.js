const Registration = {template: '<registration-page></registration-page>'}
const Login = {template: '<login></login>'}
const HomePage = {template: '<home-page></home-page>'}
const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/reg', component: Registration},
	    { path: '/login', component: Login},
	     { path: '/home', component: HomePage},
	    
	    
	  ]
});

var app = new Vue({
	router,
	el:'#registrationPage'
	
});
