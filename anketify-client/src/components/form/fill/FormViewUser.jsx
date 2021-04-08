import { Grid, Typography } from "@material-ui/core";
import QuestionsList from "../read/QuestionsList";
import formService from "../../../services/FormService";
import { useLocation, useNavigate } from "react-router";

const FormViewUser = () => {
  const { state } = useLocation();

  const navigate = useNavigate();

  const form = state.form;

  const uuid = state.uuid;

  const handleUserAnswers = (answers) => {
    console.log("Answers: " + JSON.stringify(answers));
    formService
      .submitForm(uuid, answers)
      .then((success) => {
        if (success === true) {
          navigate("/profile/forms");
        } else {
          // stuff
        }
      })
      .catch((error) => {
        console.log("Error:" + error);
      });
  };

  return (
    <Grid container direction="column" spacing={5} alignItems="center">
      <>
        <Grid item lg={12} sm={6} xl={6} xs={12}>
          <Typography variant="h3">{form.name}</Typography>
        </Grid>
        <QuestionsList
          onFinishedAnswering={handleUserAnswers}
          formId={form.id}
          questions={form.questions}
        ></QuestionsList>
      </>
    </Grid>
  );
};

export default FormViewUser;
