import { Container, Box, Typography } from "@material-ui/core";
import CreateFormContent from "./CreateFormContent";
import Page from "../../base/Page";
import useDefaultStyles from "../../base/baseStyle";

const CreateForm = () => {
  const classes = useDefaultStyles();

  return (
    <Page className={classes.root} title="Create form">
      <Container maxWidth={false}>
        <Box display="flex" justifyContent="center" marginBottom={4}>
          <Typography variant="h3">Create a new form</Typography>
        </Box>
        <CreateFormContent />
      </Container>
    </Page>
  );
};

export default CreateForm;
