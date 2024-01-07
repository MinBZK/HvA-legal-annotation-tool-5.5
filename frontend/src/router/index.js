// Composables
import {createRouter, createWebHistory} from 'vue-router'
import {store} from "@/store/app";

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/components/AppLogin.vue'),
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('@/components/XmlDashboard.vue'),
    meta: {requiresAuth: true} // Add a meta field to indicate authentication requirement
  },
  {
    path: '/:catchAll(.*)',
    redirect: '/dashboard',
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

router.beforeEach((to, from, next) => {
  let loggedIn = store().user.loggedIn;
  let authorisationLevel = store().user.permissions

  // Check local storage for loggedIn status and update the store
  const storedLoggedIn = window.localStorage.getItem("isLoggedIn");

  if (storedLoggedIn !== null && loggedIn !== JSON.parse(storedLoggedIn)) {
    store().user.loggedIn = JSON.parse(storedLoggedIn);
  }

  // Check if the route requires authentication
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);

  if (requiresAuth && !loggedIn) {
    // If authentication is required and the user is not logged in, redirect to the login page
    next('/login');
  } else if (to.path === '/login' && loggedIn) {
    // If trying to access the login page while already logged in, redirect to the dashboard
    next('/dashboard');
  } else {
    next(); // Continue navigation
  }
});

export default router
