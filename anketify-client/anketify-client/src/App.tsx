import { ThemeProvider } from "@material-ui/styles";
import { useContext } from "react";
import { useRoutes } from "react-router";
import { AuthContext } from "./providers/AuthProvider";
import routes from "./router/routes";
import GlobalStyles from "./theme/globalStyle";
import theme from "./theme/theme";

const App = () => {
  const { authenticated } = useContext(AuthContext);

  const routing = useRoutes(routes(authenticated));

  return (
    <ThemeProvider theme={theme}>
      <GlobalStyles />
      {routing}
    </ThemeProvider>
  );
};

export default App;
