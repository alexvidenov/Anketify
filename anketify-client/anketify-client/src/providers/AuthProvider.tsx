import { useState, createContext, useEffect } from "react";

type AuthContextProps = {
  token: string | null;
  authenticated: boolean;
  setToken: (token: string) => void;
};

export const AuthContext = createContext<Partial<AuthContextProps>>({});

export const AuthProvider = ({ children }: any) => {
  const [token, setToken] = useState(null as string | null);

  useEffect(() => {
    if (localStorage.getItem("token") !== undefined) {
      setToken(localStorage.getItem("token"));
    }
  }, []);

  return (
    <AuthContext.Provider
      value={{
        token: token,
        authenticated: token !== null,
        setToken: setToken,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
