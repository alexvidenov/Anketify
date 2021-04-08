import { AppBar, Box, Hidden, IconButton, Toolbar } from "@material-ui/core";
import InputIcon from "@material-ui/icons/Input";
import MenuIcon from "@material-ui/icons/Menu";
import { useContext } from "react";
import { useNavigate } from "react-router";
import { AuthContext } from "../../../providers/AuthProvider";

const NavBar = ({ onMobileNavOpen }) => {
  const navigate = useNavigate();

  const { setToken } = useContext(AuthContext);

  return (
    <AppBar>
      <Toolbar>
        <Box flexGrow={1} />
        <Hidden mdDown>
          <IconButton
            color="inherit"
            onClick={() => {
              localStorage.setItem("token", undefined);
              setToken(null);
              navigate("/login");
              console.log("bruh");
            }}
          >
            <InputIcon />
          </IconButton>
        </Hidden>
        <Hidden lgUp>
          <IconButton color="inherit" onClick={onMobileNavOpen}>
            <MenuIcon />
          </IconButton>
        </Hidden>
      </Toolbar>
    </AppBar>
  );
};

export default NavBar;
