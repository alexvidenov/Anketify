import { Link as RouterLink } from "react-router-dom";
import { AppBar, Toolbar, makeStyles, Button } from "@material-ui/core";

const useStyles = makeStyles({
  toolbar: {
    height: 64,
  },
  nav: {
    color: "white",
  },
});

const NavBar = () => {
  const classes = useStyles();

  return (
    <AppBar elevation={0}>
      <Toolbar className={classes.toolbar}>
        <RouterLink to="/publicForms">
          <Button className={classes.nav}>Public questionnairies</Button>
        </RouterLink>
        <RouterLink to="/forms">
          <Button className={classes.nav}>Search for a questionnaire</Button>
        </RouterLink>
        <RouterLink to="/login">
          <Button className={classes.nav}>Login or create an account</Button>
        </RouterLink>
      </Toolbar>
    </AppBar>
  );
};

export default NavBar;
