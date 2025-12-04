import { Link, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function Header() {
  const { pathname } = useLocation();
  const { isAuthenticated, userEmail, signOut } = useAuth();
  const nav = useNavigate();

  const handleLogout = () => {
    signOut();
    nav("/signin");
  };

  return (
    <header className="app-header">
      <div className="brand">LuxJobStats</div>

      <nav className="nav">
        {!isAuthenticated && pathname !== "/signin" && (
          <Link className="btn-link" to="/signin">
            Sign in
          </Link>
        )}

        {!isAuthenticated && pathname !== "/signup" && (
          <Link className="btn" to="/signup">
            Sign up
          </Link>
        )}

        {isAuthenticated && (
          <>
            <span style={{ color: "#bbb", marginRight: 10 }}>
              {userEmail}
            </span>

            <button className="btn" onClick={handleLogout}>
              Sign out
            </button>
          </>
        )}
      </nav>
    </header>
  );
}
