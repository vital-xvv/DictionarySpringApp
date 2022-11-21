export const capitalize = (str) => {
  const lower = str.toLowerCase();
  return str.charAt(0).toUpperCase() + lower.slice(1);
}

export const getTokenConfig = () => {
  return {
    headers: {Authorization: localStorage.getItem('token')},
  };
}