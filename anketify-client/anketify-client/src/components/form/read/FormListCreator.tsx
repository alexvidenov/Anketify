import { Form, FormUserAnswers } from "../../../models/Form";
import {
  Card,
  Grid,
  List,
  ListItem,
  ListItemText,
  makeStyles,
} from "@material-ui/core";
import { useState } from "react";
import FormViewCreator from "./FormViewCreator";
import profileService from "../../../services/ProfileService";

const useStyles = makeStyles((theme) => ({
  formEntry: {
    display: "flex",
    flexDirection: "row",
    padding: 0,
  },
}));

interface Props {
  forms: Form[];
}

const FormList = ({ forms }: Props) => {
  const classes = useStyles();

  const [selectedForm, setSelectedForm] = useState<Form>();

  const [currentAnswers, setCurrentAnswers] = useState<FormUserAnswers>();

  const handleSelectForm = (index: number) => {
    console.log("SELECTED FORM: " + JSON.stringify(forms[index]));
    setSelectedForm(forms[index]);
  };

  const handleViewAnswers = (index: number) => {
    const form = forms[index];
    profileService.getFormResults(form.id).then((answers) => {
      console.log("Current asnwers: " + JSON.stringify(answers));
      setCurrentAnswers(answers);
    });
  };

  return (
    <Grid container direction="column" spacing={5} alignItems="center">
      <List className={classes.formEntry}>
        {forms.map((form, index) => (
          <Card raised>
            <>
              <ListItem key={index.toString()}>
                <ListItemText
                  id={`formEntry_${form.name}`}
                  primary={form.name}
                  onClick={() => {
                    handleSelectForm(index);
                  }}
                />
              </ListItem>
              <ListItem key={index.toString()}>
                <ListItemText
                  id="answersEntry"
                  primary={`See answers for ${form.name}`}
                  onClick={() => {
                    handleViewAnswers(index);
                  }}
                />
              </ListItem>
            </>
          </Card>
        ))}
      </List>
      {selectedForm !== undefined && (
        <FormViewCreator form={selectedForm} userAnswers={currentAnswers} />
      )}
    </Grid>
  );
};

export default FormList;
