import {
  Grid,
  Typography,
  Checkbox,
  FormControlLabel,
  Button,
} from "@material-ui/core";
import { useState, useEffect } from "react";
import { Form, FormUserAnswers } from "../../../models/Form";
import QuestionsList from "./QuestionsList";
import profileService from "../../../services/ProfileService";

interface Props {
  form: Form;
  userAnswers?: FormUserAnswers;
}

const FormViewCreator = ({ form, userAnswers }: Props) => {
  const [isFormPublic, setIsFormPublic] = useState<boolean>(false); // see what's the deal with that
  const [isFormClosed, setIsFormClosed] = useState<boolean>(false);

  const handleFormStateUpdate = () => {
    if (form.id !== undefined) {
      profileService
        .updateForm(form.id, {
          isClosed: isFormClosed,
          isPublic: isFormPublic,
        })
        .then((success) => console.log(success))
        .catch((error) => console.log(error));
    }
  };

  useEffect(() => {
    if (form.isPublic !== undefined) {
      setIsFormPublic(form.isPublic);
    }
    if (form.isClosed !== undefined) {
      setIsFormClosed(form.isClosed);
    }
  }, [form]);

  return (
    <Grid container direction="column" spacing={5} alignItems="center">
      <>
        <Grid item lg={12} sm={6} xl={6} xs={12}>
          <Typography>{form.name}</Typography>
        </Grid>
        <Grid item lg={12} sm={6} xl={6} xs={12}>
          <FormControlLabel
            control={
              <Checkbox
                checked={isFormPublic}
                onChange={(event) => setIsFormPublic(event.target.checked)}
                name="isPublic"
                color="primary"
              />
            }
            label="Is form public?"
          />
          <FormControlLabel
            control={
              <Checkbox
                checked={isFormClosed}
                onChange={(event) => setIsFormClosed(event.target.checked)}
                name="isClosed"
                color="primary"
              />
            }
            label="Is form closed?"
          />
        </Grid>
        <Button onClick={handleFormStateUpdate}>Save updates</Button>
        <QuestionsList
          onFinishedAnswering={null}
          formId={form.id}
          questions={form.questions}
          userVotes={userAnswers}
        ></QuestionsList>
      </>
    </Grid>
  );
};
export default FormViewCreator;
