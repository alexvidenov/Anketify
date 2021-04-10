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

const useStyles = makeStyles((_) => ({
  buttonPadding: {
    margin: 30,
  },
}));

const CreateQuestionDialog = ({ handleQuestion }) => {
  const [dialogOpen, setDialogOpen] = useState(false);

  const [imageData, setImageData] = useState("");

  const classes = useStyles();

  const handleOpenDialog = () => {
    setDialogOpen(true);
  };

  const handleCloseDialog = () => {
    setDialogOpen(false);
  };

  const fileToBase64 = (file) =>
    new Promise((resolve, _) => {
      const reader = new FileReader();
      reader.onload = () => {
        resolve(reader.result);
      };
      reader.readAsDataURL(file);
    });

  const handlePickedImage = (event) => {
    const file = event.target.files[0] || null;
    if (!file) {
      setImageData("");
      return;
    }
    fileToBase64(file).then((base64Image) => {
      console.log(typeof dataUri);
      console.log("Image" + base64Image);
      setImageData(base64Image);
    });
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
              imageDescription: "",
              optional: false,
              canSelectMoreThanOne: false,
              answers: [],
            }}
            onSubmit={(values) => {
              values.answers.reverse();
              values.imageDescription = imageData;
              handleQuestion(values);
            }}
            render={({ values }) => (
              <Form>
                <Field id="description" name="description">
                  {({ field }) => (
                    <TextField placeholder="Enter description" {...field} />
                  )}
                </Field>

                <div>
                  {imageData !== "" && (
                    <img width="300" height="300" src={imageData} alt="" />
                  )}
                  <input type="file" onChange={handlePickedImage} />
                </div>

                <label>
                  <Field type="checkbox" name="optional">
                    {({ field }) => (
                      <Checkbox checked={values.optional} {...field} />
                    )}
                  </Field>
                  Is question optional?
                </label>

                <label>
                  <Field type="checkbox" name="canSelectMoreThanOne">
                    {({ field }) => (
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
                        values.answers.map((_, index) => (
                          <div key={index}>
                            <Field type="text" name={`answers.${index}`}>
                              {({ field }) => <TextField {...field} />}
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
