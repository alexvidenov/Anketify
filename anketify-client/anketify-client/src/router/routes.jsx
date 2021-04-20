import { Navigate } from "react-router-dom";
import Login from "../components/auth/Login";
import Register from "../components/auth/Register";
import NotFound from "../errors/NotFoundRoute";
import RootLayout from "../layouts/root/RootLayout";
import MainLayout from "../layouts/main/MainLayout";
import CreateForm from "../components/form/create";
import GetForms from "../components/form/read";
import FormViewUser from "../components/form/fill/FormViewUser";
import FormSearch from "../components/form/fill";
import PublicForms from "../components/form/read/PublicForms";

const routes = (authenticated) => [
  {
    path: "profile",
    element: authenticated ? <MainLayout /> : <Navigate to="/login" />,
    children: [
      { path: "forms", element: <GetForms /> },
      { path: "createForm", element: <CreateForm /> },
      { path: "publicForms", element: <PublicForms /> },
      { path: "searchForms", element: <FormSearch /> },
    ],
  },
  {
    path: "forms",
    element: <RootLayout />,
    children: [
      { path: "/", element: <FormSearch /> },
      { path: "/:id", element: <FormViewUser /> },
    ],
  },
  {
    path: "/",
    element: !authenticated ? <RootLayout /> : <Navigate to="/profile/forms" />,
    children: [
      { path: "login", element: <Login /> },
      { path: "register", element: <Register /> },
      { path: "publicForms", element: <PublicForms /> },
      { path: "404", element: <NotFound /> },
      { path: "/", element: <Navigate to="/login" /> },
      { path: "*", element: <Navigate to="/404" /> },
    ],
  },
];

export default routes;
