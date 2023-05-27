import axios from "axios";
import { useState } from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus, faMinus } from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';
import './AddToBuyListBox.css'


export default function AddToBuyListBox(props) {
  const { commodity, setCommodity, setNumOfCart } = props;
  var [quantity, setQuantity] = useState(commodity.quantity);
  const navigate = useNavigate();

  const removeFromBuylist = async () => {
    try {

      const response = await axios.delete('/user/buylist/' + commodity.id);
      if (response.data.status) {
        setCommodity(response.data.content);
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

      const response = await axios.post('/user/buylist/' + commodity.id);
      if (response.data.status) {
        setCommodity(response.data.content);
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
