import axios from "axios";

class Auth {
  private BASE_URL = "http://localhost:8081/auth";

  public register = async (
    username: string,
    password: string
  ): Promise<String> => {
    const response = await axios.post(`${this.BASE_URL}/register`, {
      username: username,
      password: password,
    });
    return new Promise((resolve, reject) => {
      const token = response.data.token;
      if (token !== undefined) {
        resolve(token);
      } else reject("No token found ");
    });
  };

  public login = async (
    username: string,
    password: string
  ): Promise<string> => {
    const response = await axios.post(`${this.BASE_URL}/login`, {
      username: username,
      password: password,
    });
    return new Promise((resolve, reject) => {
      const token = response.data.token;
      if (token !== undefined) {
        resolve(token);
      } else {
        reject("Auth failed");
      }
    });
  };
}

export default new Auth();
