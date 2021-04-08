import { useState, createContext } from "react";

type AuthContextProps = {
  token: string | null;
  authenticated: boolean;
  setToken: (token: string) => void;
};

export const AuthContext = createContext<Partial<AuthContextProps>>({});

export const AuthProvider = ({ children }: any) => {
  const [token, setToken] = useState(null as string | null);

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
