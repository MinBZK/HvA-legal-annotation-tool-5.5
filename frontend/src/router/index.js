// Composables
import {createRouter, createWebHistory} from 'vue-router'
import {store} from "@/store/app";

const routes = [
  {
    path: '/',
    component: () => import('@/layouts/default/Default.vue'),
    children: [
      {
        path: '',
        name: 'Home',
        // route level code-splitting
        // this generates a separate chunk (Home-[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/Home.vue'),
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

router.beforeEach((to, from, next) => {
  let loggedIn = store().user.loggedIn;
  let authorisationLevel = store().user.permissions

  if (to.path === '/login' && loggedIn) {
    next(from.path);
  }

  let accessiblePages = ['/login'];
  let authRequired = !accessiblePages.includes(to.path);

  if (authRequired && !loggedIn) {
    next('/login');
  } else {
    next();
  }
});

export default router
