import { useContext } from "react";
import { Link as RouterLink, useNavigate } from "react-router-dom";
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
import { AuthContext } from "../../providers/AuthProvider";
import authService from "../../services/Auth";

const useStyles = makeStyles((theme) => ({
  root: {
    backgroundColor: theme.palette.background.paper,
    height: "100%",
    paddingBottom: theme.spacing(3),
    paddingTop: theme.spacing(3),
  },
}));

const validationSchema = yup.object({
  username: yup.string("Enter your username").required("Username is required"),
  password: yup.string("Enter your password").required("Password is required"),
});

const Login = () => {
  const classes = useStyles();

  const { setToken, loadingAuthState } = useContext(AuthContext);

  const navigate = useNavigate();

  const formik = useFormik({
    initialValues: {
      username: "",
      password: "",
    },
    validationSchema,
    onSubmit: (values) => {
      authService
        .login(values.username, values.password)
        .then((token) => {
          localStorage.setItem("token", token);
          setToken(token);
        })
        .catch((error) => {
          console.log("Fix that");
        });
    },
  });

  if (loadingAuthState) {
    return (
      <div>
        <h1>Loading...</h1>
      </div>
    );
  }

  return (
    <Box
      className={classes.root}
      display="flex"
      flexDirection="column"
      height="100%"
      justifyContent="center"
    >
      <Container maxWidth="sm">
        <div>
          <form onSubmit={formik.handleSubmit}>
            <Box mb={3}>
              <Typography color="textPrimary" variant="h2">
                Sign in
              </Typography>
            </Box>
            <TextField
              id="username"
              fullWidth
              label="Username"
              margin="normal"
              name="username"
              onBlur={formik.handleBlur}
              type="username"
              variant="outlined"
              value={formik.values.username}
              onChange={formik.handleChange}
              error={formik.touched.username && Boolean(formik.errors.username)}
              helperText={formik.touched.username && formik.errors.username}
            />
            <TextField
              id="password"
              fullWidth
              label="Password"
              margin="normal"
              name="password"
              onBlur={formik.handleBlur}
              type="password"
              variant="outlined"
              value={formik.values.password}
              onChange={formik.handleChange}
              error={formik.touched.password && Boolean(formik.errors.password)}
              helperText={formik.touched.password && formik.errors.password}
            />
            <Box my={2}>
              <Button
                id="login"
                fullWidth
                color="primary"
                disabled={formik.isSubmitting}
                size="large"
                type="submit"
                variant="contained"
              >
                Sign in
              </Button>
            </Box>
            <Typography color="textSecondary" variant="body1">
              Don&apos;t have an account?
              <Button
                id="navigateToRegister"
                onClick={() => {
                  navigate("/register", {
                    state: {
                      wtf: "BAL SUM GO",
                    },
                  });
                }}
              >
                Sign up
              </Button>
            </Typography>
          </form>
        </div>
      </Container>
    </Box>
  );
};

export default Login;
