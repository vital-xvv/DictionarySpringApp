import {useCallback, useState} from "react";
import axios from "axios";

const useFetchAxios = () => {
  const [process, setProcess] = useState('idle');

  const defaultHeaders = {Authorization: localStorage.getItem('token')}
  const request = useCallback(async (url, method = 'GET', body = null, headers = defaultHeaders) => {
    setProcess('loading');
    const resp = await axios({
      method,
      url,
      data: body,
      headers
    }).catch(function (error) {
      if (error.response) {
        console.log(error.response.data);
        console.log(error.response.status);
        console.log(error.response.headers);
        setProcess('error')
      }
    });
    setProcess('idle')
    return resp;
  }, [])

  const clearError = useCallback(
      () => {
        setProcess('loading')
      },
      [],
  );

  return {request, process, setProcess, clearError};
}
export default useFetchAxios;