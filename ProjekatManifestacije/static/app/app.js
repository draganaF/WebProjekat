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

const CommentApproval = {template: '<comment-approval></comment-approval>'}
const AddManifestation = {template: '<add-manifestation></add-manifestation>'}

const SellerTableUsers = {template:'<sellerTableUsers></sellerTableUsers>'}
const TicketUser = {template:'<ticketUser></ticketUser>'}
const Tickets = {template:'<tickets></tickets>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/reg', component: Registration},
	    { path: '/login', component: Login},
	    { path: '/', component: HomePage, redirect:'/manifestations'},
		  { path: '/manifestations', component: Manifestations},
	    { path: '/profile', component: Profile},
	    { path: '/changePassword', component:ChangePassword},
	    { path: '/registrationSeller', component:RegistrationSeller},
	    { path: '/adminTableUsers', component: AdminTableUsers},   
	    { path: '/adminSuspiciousUsers', component: AdminTableSuspiciousUsers},
	    { path: '/manifestationsAdmin', component: ManifestationsTable},
			{ path: '/manifestation/:id', component: Manifestation},
			{ path: '/approveComments', component: CommentApproval},
			{ path: '/addManifestation/:id', component: AddManifestation},
			{ path: '/sellerTable', component: SellerTableUsers},
			{ path: '/ticketUser', component: TicketUser},
			{ path: '/tickets', component: Tickets}
			
	  ]
});

var app = new Vue({
	router,
	el:'#registrationPage'
	
});
