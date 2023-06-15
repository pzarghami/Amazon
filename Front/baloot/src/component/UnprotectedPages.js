import { Navigate, Outlet, useLocation } from "react-router-dom";
import { isUserLoggedIn } from "../functions/isUserLoggedIn";


export default function UnrotectedPages() {

  const location = useLocation();

  var isLoggedIn = isUserLoggedIn();

  if (isLoggedIn) {
    return <Navigate to="/commodities" state={{ from: location }} replace />;
  }

  return (<Outlet/>);

}