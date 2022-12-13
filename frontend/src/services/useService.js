import useFetchAxios from "../hooks/useFetchAxios";
import {useNavigate} from "react-router-dom";

const baseUrl = process.env.REACT_APP_URL;
export const useService = () => {
  const {request, process, setProcess, clearError} = useFetchAxios();
  const navigate = useNavigate()

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
    let res;
    res = await request(`${baseUrl}/user/${userName}`);
    if(res === undefined) {
      res = await request(`http://localhost:8080/account/${userName}`);
    }
    const {data} = res
    localStorage.setItem('role', data?.roles[0].name)
    if (data.firstName && data.lastName) {
      localStorage.setItem('displayName', data.firstName + ' ' + data.lastName)
    }
  }

  const login = async (email, password) => {
    try {
      const {data} = await request(`http://localhost:8080/login?username=${email}&password=${password}`, 'POST')
      localStorage.setItem('token', 'Bearer ' + data.access_token);
      localStorage.setItem('username', email);
    } catch (e) {
      alert('Something went wrong, try again :(')
    }
  }

  const register = async (username, firstName, lastName, password) => {
    const payload = {
      id: null,
      username,
      password,
      firstName,
      lastName,
      roles: [],
    }
    try {
      await request(`${baseUrl}/user/register`, 'POST', payload);
    } catch (e) {
      alert('Something went wrong, try again :(')
    }
  }

  const getWords = async (lang) => {
    return await request(`${baseUrl}/dictionary/words/0/20/${lang}`)
  }

  const addWord = async (word) => {
    try {
      await request(`${baseUrl}/dictionary/add/word`, 'POST', word)
      navigate('/admin')
    } catch (e) {
      alert('Error')
    }
  }

  const changeWord = async (word) => {
    try {
      await request(`${baseUrl}/dictionary/change/word`, 'PUT', word)
      navigate('/admin')
    } catch (e) {
      alert('Error')
    }
  }

  const deleteWord = async (word, langCode) => {
    const payload = {
      word,
      langCode,
      wordObject: null
    }
    if (confirm('Delete?')) {
      return await request(`${baseUrl}/dictionary/delete/word`, 'DELETE', payload)
    }
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
    getWords,
    addWord,
    changeWord,
    deleteWord
  }
}

export default useService;
