export const decodeToken = (token) => {
    try {
      const payload = token.split('.')[1];
      const decoded = JSON.parse(atob(payload));
      console.log('Decoded Token:', decoded); 
      return decoded;
    } catch (error) {
      console.error('Failed to decode token:', error);
      return null;
    }
  };
  
  export const getUserRole = () => {
    const token = localStorage.getItem('token');
    console.log('Token from storage:', token); 
    if (token) {
      const decoded = decodeToken(token);
      console.log('Role from decoded token:', decoded?.role); 
      return decoded?.role || 'ROLE_USER';
    }
    return 'ROLE_USER';
  };