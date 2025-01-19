export const environment = {
  production: true,
  apiBaseUrl: 'http://localhost:8080/api',  // Change this to your production API URL
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
