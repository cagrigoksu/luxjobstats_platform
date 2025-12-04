import { createContext, useContext, useEffect, useState } from "react";
import { getAccessToken, clearTokens } from "../api/client";
import { decodeJwt } from "../utils/jwt";

type AuthState = {
  isAuthenticated: boolean;
  userEmail: string | null;
  signOut: () => void;
};

const AuthContext = createContext<AuthState | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(!!getAccessToken());
  const [userEmail, setUserEmail] = useState<string | null>(null);

    // Extract email from JWT
  const hydrateUser = () => {
    
    const token = getAccessToken();
    if (!token) {
      setIsAuthenticated(false);
      setUserEmail(null);
      return;
    }
    const decoded = decodeJwt(token);
    setIsAuthenticated(true);
    setUserEmail(decoded?.email?.[0] ?? null);
  };

  //! listen for login/logout events from setTokens() > client.ts
  useEffect(() => {
    hydrateUser();
    window.addEventListener("auth-changed", hydrateUser);
    return () => window.removeEventListener("auth-changed", hydrateUser);
  }, []);

  const signOut = () => {
    clearTokens();
    window.dispatchEvent(new Event("auth-changed"));
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, userEmail, signOut }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth must be inside AuthProvider");
  return ctx;
}
