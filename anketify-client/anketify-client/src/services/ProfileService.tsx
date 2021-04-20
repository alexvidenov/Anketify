import axios from "axios";
import { Form, FormUserAnswers } from "../models/Form";
import { UpdateForm } from "../models/requests/updateForm";
import { FormURLResponse } from "../models/responses/FormURLResponse";
import authHeader from "../utils/AuthHeader";

class ProfileService {
  private BASE_URL = "http://localhost:8081/profile";

  public getForms = async (): Promise<Form[]> => {
    return new Promise((resolve, reject) => {
      axios
        .get(`${this.BASE_URL}/forms`, {
          headers: authHeader(),
        })
        .then((response) => {
          const forms = response.data;
          console.log("FORMS BROOO: " + JSON.stringify(forms));
          resolve(forms);
        })
        .catch((error) => {
          reject(`Something went wrong with fetching forms: ${error}`);
        });
    });
  };

  public createForm = async (form: Form): Promise<FormURLResponse> => {
    return new Promise((resolve, reject) => {
      axios
        .post(`${this.BASE_URL}/createForm`, form, {
          headers: authHeader(),
        })
        .then((response) => {
          const url = response.data.formUrl;
          resolve(url);
        })
        .catch((error) => {
          reject(`Something went wron with creating form: ${error}`);
        });
    });
  };

  public updateForm = async (
    formId: number,
    updateForm: UpdateForm
  ): Promise<boolean> => {
    return new Promise((resolve, reject) => {
      axios
        .patch(`${this.BASE_URL}/form/${formId}`, updateForm, {
          headers: authHeader(),
        })
        .then((response) => {
          const success = response.data;
          success ? resolve(success) : reject("Updating wasn't successful");
        })
        .catch((error) => {
          reject(`Something went wron with updating form: ${error}`);
        });
    });
  };

  public getFormResults = async (formId?: number): Promise<FormUserAnswers> => {
    return new Promise((resolve, reject) => {
      axios
        .get(`${this.BASE_URL}/form/${formId}/results`, {
          headers: authHeader(),
        })
        .then((response) => {
          const form = response.data.aggregatedUserAnswers;
          resolve(form);
        })
        .catch((error) =>
          reject(`Something went wron with getting form results: ${error}`)
        );
    });
  };
}

export default new ProfileService();
