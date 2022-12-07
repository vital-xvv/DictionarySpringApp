import useFetchAxios from "../hooks/useFetchAxios";

const baseUrl = process.env.REACT_APP_URL;
export const useService = () => {
  const {request, process, setProcess, clearError} = useFetchAxios()

  const getWord = (langCode, word) => {
    const payload = {
      word,
      langCode
    }
    return request(`${baseUrl}/api/dictionary/get/word`, 'POST', payload);
  }

  const getTranslation = (data) => {
    return request(`${baseUrl}/dictionary/api/translate`, 'POST', data);
  }

  const isLoggedIn = () => {
    return !!localStorage.getItem('token')
  }

  const login = async (email, password) => {
    try {
      const res = await request(`${baseUrl}/login?username=${email}&password=${password}`, 'POST')
      localStorage.setItem('token', 'Bearer ' + res.access_token)
      localStorage.setItem('username', email);
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
