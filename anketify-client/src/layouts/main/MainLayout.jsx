import { useState } from "react";
import { Outlet } from "react-router-dom";
import useMainLayoutStyles from "../../theme/mainLayoutStyle";
import NavDrawer from "./nav/Drawer";
import NavBar from "./nav/NavBar";

const MainLayout = () => {
  const classes = useMainLayoutStyles();
  const [isMobileNavOpen, setMobileNavOpen] = useState(false);

  return (
    <div className={classes.root}>
      <NavBar onMobileNavOpen={() => setMobileNavOpen(true)} />
      <NavDrawer
        onMobileClose={() => setMobileNavOpen(false)}
        openMobile={isMobileNavOpen}
      />
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

export default MainLayout;
