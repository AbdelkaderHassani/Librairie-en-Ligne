export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8080/api',
  authApi: {
    login: '/users/login',
    register: '/users/register',
    profile: '/users/profile'
  },
  bookApi: {
    books: '/livres',
    categories: '/categories'
  },
  orderApi: {
    orders: '/commandes',
    cart: '/panier'
  }
};
