import { FormAnswer } from "./FormAnswer";

export interface Question {
  id?: number;
  description: string;
  imageDescription?: string;
  optional: boolean;
  canSelectMoreThanOne: boolean;
  answers: FormAnswer[];
}
