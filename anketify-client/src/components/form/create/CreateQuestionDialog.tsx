import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
  Button,
  TextField,
  Checkbox,
  makeStyles,
} from "@material-ui/core";
import { useState } from "react";
import { Formik, Form, Field, FieldArray } from "formik";
import { Question } from "../../../models/Question";

interface Props {
  handleQuestion: (question: Question) => void;
}

const useStyles = makeStyles((_) => ({
  buttonPadding: {
    margin: 30,
  },
}));

const CreateQuestionDialog = ({ handleQuestion }: Props) => {
  const [dialogOpen, setDialogOpen] = useState(false);

  const classes = useStyles();

  const handleOpenDialog = () => {
    setDialogOpen(true);
  };

  const handleCloseDialog = () => {
    setDialogOpen(false);
  };

  return (
    <>
      <Button
        className={classes.buttonPadding}
        variant="outlined"
        color="primary"
        onClick={handleOpenDialog}
      >
        Add a question!
      </Button>
      <Dialog
        open={dialogOpen}
        onClose={handleCloseDialog}
        aria-labelledby="form-dialog-title"
      >
        <DialogTitle id="form-dialog-title">Create a question</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Enter your question description and answers
          </DialogContentText>
          <Formik
            initialValues={{
              description: "",
              optional: false,
              canSelectMoreThanOne: false,
              answers: [],
            }}
            onSubmit={(values) => {
              values.answers.reverse();
              handleQuestion(values);
            }}
            render={({ values }) => (
              <Form>
                <Field id="description" name="description">
                  {({ field }: any) => (
                    <TextField placeholder="Enter description" {...field} />
                  )}
                </Field>

                <label>
                  <Field type="checkbox" name="optional">
                    {({ field }: any) => (
                      <Checkbox checked={values.optional} {...field} />
                    )}
                  </Field>
                  Is question optional?
                </label>

                <label>
                  <Field type="checkbox" name="canSelectMoreThanOne">
                    {({ field }: any) => (
                      <Checkbox
                        checked={values.canSelectMoreThanOne}
                        {...field}
                      />
                    )}
                  </Field>
                  More than one answer?
                </label>

                <FieldArray
                  name="answers"
                  render={(arrayHelpers) => (
                    <div>
                      {values.answers && values.answers.length > 0 ? (
                        values.answers.map((answer, index) => (
                          <div key={index}>
                            <Field type="text" name={`answers.${index}`}>
                              {({ field }: any) => <TextField {...field} />}
                            </Field>
                            <Button onClick={() => arrayHelpers.remove(index)}>
                              -
                            </Button>
                            <Button
                              type="button"
                              onClick={() => arrayHelpers.insert(index, "")}
                            >
                              +
                            </Button>
                          </div>
                        ))
                      ) : (
                        <Button
                          type="button"
                          onClick={() => arrayHelpers.push("")}
                        >
                          Add an answer
                        </Button>
                      )}
                      <DialogActions>
                        <Button onClick={handleCloseDialog} color="primary">
                          Cancel
                        </Button>
                        <Button
                          type="submit"
                          onClick={() => {
                            handleCloseDialog();
                          }}
                          color="primary"
                        >
                          Create
                        </Button>
                      </DialogActions>
                    </div>
                  )}
                />
              </Form>
            )}
          />
        </DialogContent>
      </Dialog>
    </>
  );
};

export default CreateQuestionDialog;
