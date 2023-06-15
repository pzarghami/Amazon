import { Navigate, Outlet, useLocation } from "react-router-dom";
import { isUserLoggedIn } from "../functions/isUserLoggedIn";


export default function ProtectedPages() {

  const location = useLocation();

  var isLoggedIn = isUserLoggedIn();

  if (!isLoggedIn) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return (<Outlet/>);

}