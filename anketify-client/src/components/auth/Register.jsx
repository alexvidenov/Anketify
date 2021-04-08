import { Link as RouterLink, useLocation } from "react-router-dom";
import * as yup from "yup";
import { useFormik } from "formik";
import {
  Box,
  Button,
  Container,
  Link,
  TextField,
  Typography,
  makeStyles,
} from "@material-ui/core";
import authService from "../../services/Auth";
import { useContext } from "react";
import { AuthContext } from "../../providers/AuthProvider";

const useStyles = makeStyles((theme) => ({
  root: {
    backgroundColor: theme.palette.background.dark,
    height: "100%",
    paddingBottom: theme.spacing(3),
    paddingTop: theme.spacing(3),
  },
}));

const validationSchema = yup.object({
  username: yup
    .string("Enter your username")
    .min(3, "Username should be of minimum 3 characters length")
    .required("Email is required"),
  password: yup
    .string("Enter your password")
    .min(6, "Password should be of minimum 6 characters length")
    .required("Password is required"),
});

const Register = () => {
  const classes = useStyles();

  const { state } = useLocation();

  console.log(state);

  const { setToken } = useContext(AuthContext);

  const formik = useFormik({
    initialValues: {
      username: "",
      password: "",
    },
    validationSchema,
    onSubmit: (values) => {
      authService
        .register(values.username, values.password)
        .then((token) => {
          console.log(`Token is ${token}`);
          localStorage.setItem("token", token);
          setToken(token);
        })
        .catch((error) => {
          console.log("FIXME");
        });
    },
  });

  return (
    <Box
      className={classes.root}
      display="flex"
      flexDirection="column"
      height="100%"
      justifyContent="center"
    >
      <Container maxWidth="sm">
        <form onSubmit={formik.handleSubmit}>
          <Box mb={3}>
            <Typography color="textPrimary" variant="h2">
              Create new account
            </Typography>
            <Typography color="textSecondary" gutterBottom variant="body2">
              Enter username and password to create new account
            </Typography>
          </Box>
          <TextField
            fullWidth
            label="Username"
            margin="normal"
            name="username"
            type="username"
            variant="outlined"
            value={formik.values.username}
            onBlur={formik.handleBlur}
            onChange={formik.handleChange}
            error={formik.touched.username && Boolean(formik.errors.username)}
            helperText={formik.touched.username && formik.errors.username}
          />
          <TextField
            fullWidth
            label="Password"
            margin="normal"
            name="password"
            type="password"
            variant="outlined"
            value={formik.values.password}
            onBlur={formik.handleBlur}
            onChange={formik.handleChange}
            error={formik.touched.password && Boolean(formik.errors.password)}
            helperText={formik.touched.password && formik.errors.password}
          />
          <Box my={2}>
            <Button
              color="primary"
              disabled={formik.isSubmitting}
              fullWidth
              size="large"
              type="submit"
              variant="contained"
            >
              Sign up now
            </Button>
          </Box>
          <Typography color="textSecondary" variant="body1">
            Have an account?{" "}
            <Link component={RouterLink} to="/login" variant="h6">
              Sign in
            </Link>
          </Typography>
        </form>
      </Container>
    </Box>
  );
};

export default Register;
