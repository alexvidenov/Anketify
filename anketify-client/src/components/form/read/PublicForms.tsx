import { useEffect, useState } from "react";
import { Form } from "../../../models/Form";
import {
  Card,
  Grid,
  List,
  ListItem,
  ListItemText,
  makeStyles,
} from "@material-ui/core";
import formService from "../../../services/FormService";
import { useNavigate } from "react-router";

const useStyles = makeStyles((theme) => ({
  rowList: {
    display: "flex",
    flexDirection: "row",
    padding: 30,
  },
}));

const PublicForms = () => {
  const classes = useStyles();

  const navigate = useNavigate();

  const [forms, setForms] = useState<Form[]>();

  const handleSelectForm = (index: number) => {
    if (forms !== undefined) {
      const form = forms[index];
      console.log("DAMN UUID IS " + form.formUUID?.uuid);
      navigate(`/forms/${form.id}`, {
        state: {
          form: form,
          uuid: form.formUUID?.uuid,
        },
      });
    }
  };

  useEffect(() => {
    formService
      .getPublicForms()
      .then((forms) => {
        setForms(forms);
      })
      .catch((error) => console.log("Error: " + error));
  }, []);

  return (
    <Grid container direction="column" spacing={5} alignItems="center">
      <List className={classes.rowList}>
        {forms !== undefined &&
          forms.map((form, index) => (
            <Card raised>
              <ListItem key={index.toString()}>
                <ListItemText
                  primary={form.name}
                  onClick={() => {
                    handleSelectForm(index);
                  }}
                />
              </ListItem>
            </Card>
          ))}
      </List>
    </Grid>
  );
};

export default PublicForms;
