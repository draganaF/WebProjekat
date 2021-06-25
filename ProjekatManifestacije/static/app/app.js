const Registration = {template: '<registration-page></registration-page>'}
const Login = {template: '<login></login>'}
const HomePage = {template: '<home-page></home-page>'}
const Profile = {template: '<profile></profile>'}
const ChangePassword = {template: '<change-password></change-password>'}
const RegistrationSeller = {template: '<registration-seller-page></registration-seller-page>'}
const AdminTableUsers = {template: '<adminTableUsers></adminTableUsers>'}
const AdminTableSuspiciousUsers = {template: '<adminTableSuspiciousUsers></adminTableSuspiciousUsers>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/reg', component: Registration},
	    { path: '/login', component: Login},
	    { path: '/home', component: HomePage},
	    { path: '/profile', component: Profile},
	    { path: '/changePassword', component:ChangePassword},
	    { path: '/registrationSeller', component:RegistrationSeller},
	    { path: '/adminTableUsers', component: AdminTableUsers},   
	    { path: '/adminSuspiciousUsers', component: AdminTableSuspiciousUsers},
	  ]
});

var app = new Vue({
	router,
	el:'#registrationPage'
	
});
