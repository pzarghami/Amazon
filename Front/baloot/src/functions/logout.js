export function logout() {
  localStorage.removeItem('token');
  localStorage.removeItem('userId');
}