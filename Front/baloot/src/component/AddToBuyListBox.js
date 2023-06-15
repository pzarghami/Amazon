import axios from "axios";
import { useState } from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus, faMinus } from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';
import './AddToBuyListBox.css'
import { isUserLoggedIn } from "../functions/isUserLoggedIn";
import { getUserId } from "../functions/getUserId";
import Request from "../functions/Request";


export default function AddToBuyListBox(props) {
  const { commodity, setCommodity, setNumOfCart } = props;
  
  var [quantity, setQuantity] = useState(commodity.quantity);
  const navigate = useNavigate();

  const isLoggedIn = isUserLoggedIn();
  const userId = getUserId();
  const removeFromBuylist = async () => {
    try {

      const response = await Request.delete('/user/buylist/' + commodity.id);
      if (response.data.status) {
        setCommodity(response.data.content);
        setNumOfCart();
        if (quantity == 0)
          setQuantity(quantity);
        else
          setQuantity(quantity - 1)

      }

    } catch (e) {

    }
  }
  const addToBuylist = async () => {
    try {

      const response = await Request.post('/user/buylist/' + commodity.id);
      if (response.data.status) {
        setCommodity(response.data.content);
        setNumOfCart();
        console.log(response);
        console.log("quantity");
        setQuantity(quantity + 1);
      }


    } catch (e) {

    }
  }
  return (
    <>
      <div className='quantity-box'>
        <span className="minus" onClick={removeFromBuylist}>
          -
        </span>
        <span className="number"> {quantity} </span>
        <span className="plus" onClick={addToBuylist}>
          +
        </span>
      </div>
    </>

  )
}
