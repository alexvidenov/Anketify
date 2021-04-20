import axios from "axios";
import { Form } from "../models/Form";
import { UserAnswer } from "../models/UserAnswer";

class FormService {
  private BASE_URL = "http://localhost:8081/forms";

  public getFormUser = async (uuid: number): Promise<Form> => {
    return new Promise((resolve, reject) => {
      axios
        .get(`${this.BASE_URL}/${uuid}`)
        .then((response) => {
          const form = response.data.form;
          resolve(form);
        })
        .catch((error) => {
          reject(`Someting went wrong with getting the form: ${error}`);
        });
    });
  };

  public submitForm = async (
    uuid: string,
    answers: UserAnswer[]
  ): Promise<boolean> => {
    return new Promise((resolve, reject) => {
      axios
        .post(`${this.BASE_URL}/${uuid}`, answers)
        .then((response) => {
          const success = response.data;
          success ? resolve(success) : reject("Form could not be submitted");
        })
        .catch((error) => {
          reject(`Someting went wrong with submitting the form: ${error}`);
        });
    });
  };

  public getPublicForms = async (): Promise<Form[]> => {
    return new Promise((resolve, reject) => {
      axios
        .get(`${this.BASE_URL}/public`)
        .then((response) => {
          console.log("Forms" + response.data);
          const forms = response.data;
          resolve(forms);
        })
        .catch((error) => {
          reject(`Someting went wrong with getting public forms: ${error}`);
        });
    });
  };
}

export default new FormService();
