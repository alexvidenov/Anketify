import { useEffect, useState } from "react";
import { Container, Box, Typography } from "@material-ui/core";
import useDefaultStyles from "../../base/baseStyle";
import Page from "../../base/Page";
import FormList from "./FormListCreator";
import profileService from "../../../services/ProfileService";

const GetForms = () => {
  const classes = useDefaultStyles();

  const [forms, setForms] = useState();

  useEffect(() => {
    profileService
      .getForms()
      .then((response) => {
        console.log(JSON.stringify(response));
        setForms(response);
      })
      .catch((error) => console.log(error));
  }, []);

  return (
    <Page className={classes.root} title="Create form">
      <Container maxWidth={false}>
        <Box display="flex" justifyContent="center" marginBottom={4}>
          <Typography variant="h3">Your forms</Typography>
        </Box>
        {forms !== undefined && <FormList forms={forms} />}
      </Container>
    </Page>
  );
};

export default GetForms;
