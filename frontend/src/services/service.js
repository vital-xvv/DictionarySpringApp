import axios from 'axios';

export default class Service {
  constructor() {
    this.baseUrl = process.env.REACT_APP_URL;
  }

  async fetchAxios(url, method = 'GET', body = null) {
    const resp = await axios({
      method: method,
      url: url,
      data: body
    }).catch(function (error) {
      if (error.response) {
        console.log(error.response.data);
        console.log(error.response.status);
        console.log(error.response.headers);
      }
    });
    return resp.data;
  }

  getWord(fromCode, word) {
    return this.fetchAxios(`${this.baseUrl}/${fromCode}/${word}`);
  }

  getTranslation(data) {
    return this.fetchAxios(`${this.baseUrl}/translate`, 'POST', data);
  }

  isLoggedIn() {
    return !!localStorage.getItem('token')
  }


  login(/*email, password*/) {
    //TODO: some logic with API
  }

  register(/*email, password*/) {
    //TODO: some logic with API
  }
}