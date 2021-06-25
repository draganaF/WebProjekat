const Registration = {template: '<registration-page></registration-page>'}
const Login = {template: '<login></login>'}
const HomePage = {template: '<home-page></home-page>'}
const Manifestations = {template: '<manifestations></manifestations>'}
const Profile = {template: '<profile></profile>'}
const ChangePassword = {template: '<change-password></change-password>'}
const RegistrationSeller = {template: '<registration-seller-page></registration-seller-page>'}
const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/reg', component: Registration},
	    { path: '/login', component: Login},
	    { path: '/home', component: HomePage},
			{ path: '/manifestation', component: Manifestations},
	    { path: '/profile', component: Profile},
	    { path: '/changePassword', component:ChangePassword},
	    { path: '/registrationSeller', component:RegistrationSeller},
	      
	  ]
});

var app = new Vue({
	router,
	el:'#registrationPage'
	
});
