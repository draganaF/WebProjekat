const Registration = {template: '<registration-page></registration-page>'}
const Login = {template: '<login></login>'}
const HomePage = {template: '<home-page></home-page>'}
const Manifestations = {template: '<manifestations></manifestations>'}
const Profile = {template: '<profile></profile>'}
const ChangePassword = {template: '<change-password></change-password>'}
const RegistrationSeller = {template: '<registration-seller-page></registration-seller-page>'}
const AdminTableUsers = {template: '<adminTableUsers></adminTableUsers>'}
const AdminTableSuspiciousUsers = {template: '<adminTableSuspiciousUsers></adminTableSuspiciousUsers>'}
const ManifestationsTable = {template: '<manifestationsTable></manifestationsTable>'}
const Manifestation = {template: '<manifestation></manifestation>'}
<<<<<<< HEAD
const CommentApproval = {template: '<comment-approval></comment-approval>'}
const AddManifestation = {template: '<add-manifestation></add-manifestation>'}
=======
const SellerTableUsers = {template:'<sellerTableUsers></sellerTableUsers>'}
const TicketUser = {template:'<ticketUser></ticketUser>'}
>>>>>>> 5dbfbf9ca95c481ac0a41baf6cc2645a7ef20f3f

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/reg', component: Registration},
	    { path: '/login', component: Login},
	    { path: '/home', component: HomePage},
		  { path: '/manifestations', component: Manifestations},
	    { path: '/profile', component: Profile},
	    { path: '/changePassword', component:ChangePassword},
	    { path: '/registrationSeller', component:RegistrationSeller},
	    { path: '/adminTableUsers', component: AdminTableUsers},   
	    { path: '/adminSuspiciousUsers', component: AdminTableSuspiciousUsers},
	    { path: '/manifestationsAdmin', component: ManifestationsTable},
<<<<<<< HEAD
			{ path: '/manifestation/:id', component: Manifestation},
			{ path: '/approveComments', component: CommentApproval},
			{ path: '/addManifestation/:id', component: AddManifestation},
=======
		{ path: '/manifestation/:id', component: Manifestation},
		{ path: '/sellerTable', component: SellerTableUsers},
		{ path: '/ticketUser', component: TicketUser}
>>>>>>> 5dbfbf9ca95c481ac0a41baf6cc2645a7ef20f3f
	  ]
});

var app = new Vue({
	router,
	el:'#registrationPage'
	
});
