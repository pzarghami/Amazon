export function login(jwt, userId) {
  localStorage.setItem('token', jwt);
  localStorage.setItem('userId', userId);
}