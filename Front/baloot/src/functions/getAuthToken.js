export function getAuthToken(token = null) {
  if (token === null) {
    const savedToken = localStorage.getItem('token');
    return { 'Authorization': savedToken, }
  }
  else {
    return { 'Authorization':  token, }
  }
}