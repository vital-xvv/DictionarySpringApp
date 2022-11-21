import useFetchAxios from "../hooks/useFetchAxios";

const baseUrl = process.env.REACT_APP_URL;
export const useService = () => {
  const {request, process, setProcess, clearError} = useFetchAxios()

  const getWord = (fromCode, word) => {
    return request(`${baseUrl}/${fromCode}/${word}`);
  }

  const getTranslation = (data) => {
    return request(`${baseUrl}/translate`, 'POST', data);
  }

  const isLoggedIn = () => {
    return !!localStorage.getItem('token')
  }

  const login = async (email, password) => {
    try {
      const res = await request(`${baseUrl}/login?username=${email}&password=${password}`, 'POST')
      localStorage.setItem('token', res)
    } catch (e) {
      alert('Something went wrong, try again :(')
    }
  }

  /*const register = (/!*email, password*!/) => {
    //TODO: some logic with API
  }*/

  return {request, process, setProcess, clearError, isLoggedIn, getTranslation, getWord, login}
}

export default useService;
