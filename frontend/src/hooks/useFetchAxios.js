import {useCallback, useState} from "react";
import axios from "axios";

const useFetchAxios = () => {
  const [process, setProcess] = useState('idle');


  const request = useCallback(async (url, method = 'GET', body = null) => {
    setProcess('loading');
    const resp = await axios({
      method: method,
      url: url,
      data: body
    }).catch(function (error) {
      if (error.response) {
        console.log(error.response.data);
        console.log(error.response.status);
        console.log(error.response.headers);
        setProcess('error')
      }
    });
    setProcess('idle')
    return resp.data;
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