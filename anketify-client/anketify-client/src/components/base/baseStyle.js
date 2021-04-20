import { makeStyles } from "@material-ui/core";

const useDefaultStyles = makeStyles((theme) => ({
  root: {
    backgroundColor: "white",
    minHeight: "100%",
    paddingBottom: theme.spacing(3),
    paddingTop: theme.spacing(3),
  },
}));

export default useDefaultStyles;
