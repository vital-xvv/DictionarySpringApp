import useFetchAxios from "../hooks/useFetchAxios";

const baseUrl = process.env.REACT_APP_URL;
export const useService = () => {
  const {request, process, setProcess, clearError} = useFetchAxios()

  const getWord = (langCode, word) => {
    const payload = {
      word, langCode
    }
    return request(`${baseUrl}/dictionary/get/word`, 'POST', payload);
  }

  const getTranslation = (data) => {
    return request(`${baseUrl}/dictionary/translate`, 'POST', data);
  }

  const isLoggedIn = () => {
    return !!localStorage.getItem('token')
  }

  const getCurrentUser = async () => {
    const userName = localStorage.getItem('username')
    const res = await request(`${baseUrl}/user/${userName}`);
    localStorage.setItem('role', res.roles[0].name)
    if(res.firstName && res.secondName) {
      localStorage.setItem('username', res.firstName + res.secondName)
    }
  }

  const login = async (email, password) => {
    try {
      const res = await request(`http://localhost:8080/login?username=${email}&password=${password}`, 'POST')
      localStorage.setItem('token', 'Bearer ' + res.access_token);
      localStorage.setItem('username', email);
    } catch (e) {
      alert('Something went wrong, try again :(')
    }
  }

  const register = async (email, password) => {
    const payload = {
      id: null,
      firstName: null,
      secondName: null,
      roles: [{
        id: 2,
        name: "ROLE_USER"
      }],
      email,
      password
    }
    try {
      await request(`${baseUrl}/user/user/save`, 'POST', payload);
    } catch (e) {
      alert('Something went wrong, try again :(')
    }
  }

  const getWords = async (lang) => {
    return await request(`${baseUrl}/dictionary/words/0/20/${lang}`)
  }

  return {
    request,
    process,
    setProcess,
    clearError,
    isLoggedIn,
    getTranslation,
    getWord,
    login,
    getCurrentUser,
    register,
    getWords
  }
}

export default useService;
