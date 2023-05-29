import { Outlet } from "react-router-dom";
import Header from "./Header";


export default function Layout(props) {
  const {numOfCart}=props;

  return (
    <>
    <Header numOfCart={numOfCart}/>
    <Outlet/>
    </>
  )
}