import { Outlet } from "react-router-dom";
import NavBar from "./NavBar";
import useRootLayoutStyles from "../../theme/rootLayoutStyle";

const RootLayout = () => {
  const classes = useRootLayoutStyles();

  return (
    <div className={classes.root}>
      <NavBar />
      <div className={classes.wrapper}>
        <div className={classes.contentContainer}>
          <div className={classes.content}>
            <Outlet />
          </div>
        </div>
      </div>
    </div>
  );
};

export default RootLayout;
