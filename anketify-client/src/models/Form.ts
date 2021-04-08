import { FormUUID } from "./FormUUID";
import { Question } from "./Question";

export interface Form {
  id?: number;
  formUUID?: FormUUID;
  name: string;
  isPublic: boolean;
  isClosed: boolean;
  questions: Question[];
}

export type FormUserAnswers = {
  [key: string]: number;
};
