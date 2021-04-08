import {
  TextField,
  Checkbox,
  Grid,
  Button,
  makeStyles,
} from "@material-ui/core";
import { useState } from "react";
import { Question } from "../../../models/Question";
import { Formik, Field, Form as FormikForm } from "formik";
import CreateQuestionDialog from "./CreateQuestionDialog";
import QuestionsList from "../read/QuestionsList";
import { Form } from "../../../models/Form";
import formService from "../../../services/ProfileService";

const useStyles = makeStyles(() => ({
  buttonPadding: {
    marginLeft: 30,
  },
}));

const CreateFormContent = () => {
  const classes = useStyles();

  const [questions, setQuestions] = useState<Question[]>([]);

  const handleNewQuestion = (question: Question) => {
    setQuestions([...questions, question]);
  };

  const handleForm = (form: Form) => {
    formService
      .createForm(form)
      .then((url) => {
        alert(`Your URL is ${url}`);
        // show dialog wiht the URL
      })
      .catch((error) => console.log(error)); // again, show dialog
  };

  return (
    <Grid container direction="column" spacing={5} alignItems="center">
      <>
        <Grid item lg={12} sm={6} xl={6} xs={12}>
          <CreateQuestionDialog handleQuestion={handleNewQuestion} />
        </Grid>
        <Grid item lg={12} sm={6} xl={6} xs={12}>
          <Formik
            initialValues={{
              name: "",
              isPublic: false,
              isClosed: false,
              questions: questions,
            }}
            onSubmit={(values) => {
              values.questions = questions;
              handleForm(values);
            }}
            render={({ values }) => (
              <FormikForm>
                <Field id="name" name="name">
                  {({ field }: any) => (
                    <TextField placeholder="Enter your form name" {...field} />
                  )}
                </Field>

                <label>
                  <Field type="checkbox" name="isPublic">
                    {({ field }: any) => (
                      <Checkbox checked={values.isPublic} {...field} />
                    )}
                  </Field>
                  Is public?
                </label>

                <label>
                  <Field type="checkbox" name="isClosed">
                    {({ field }: any) => (
                      <Checkbox checked={values.isClosed} {...field} />
                    )}
                  </Field>
                  Is closed?
                </label>

                <Button
                  className={classes.buttonPadding}
                  type="submit"
                  color="primary"
                  variant="outlined"
                >
                  Finalize your form!
                </Button>
              </FormikForm>
            )}
          />
        </Grid>
        <QuestionsList
          onFinishedAnswering={null}
          questions={questions}
        ></QuestionsList>
      </>
    </Grid>
  );
};

export default CreateFormContent;
