import { Container, Box, TextField, Button } from "@material-ui/core";
import useDefaultStyles from "../../base/baseStyle";
import Page from "../../base/Page";
import { useNavigate } from "react-router";
import formService from "../../../services/FormService";
import { useState } from "react";

const FormSearch = () => {
  const classes = useDefaultStyles();

  const navigate = useNavigate();

  const [uuid, setUUID] = useState();

  const handleURLSubmit = () => {
    formService.getFormUser(uuid).then((form) => {
      console.log(`FORM IS ${form}`);
      navigate(`/forms/${form.id}`, {
        state: {
          form: form,
          uuid: uuid,
        },
      });
    });
  };

  return (
    <Page className={classes.root} title="Create form">
      <Container maxWidth={false}>
        <Box display="flex" justifyContent="center" marginBottom={4}>
          <TextField
            onChange={(event) => {
              setUUID(event.target.value);
            }}
          >
            Enter your form url
          </TextField>
          <Button onClick={handleURLSubmit}>Search</Button>
        </Box>
      </Container>
    </Page>
  );
};

export default FormSearch;
