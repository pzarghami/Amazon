import axios from "axios";
import { useState } from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus, faMinus } from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';
import './AddToBuyListBox.css'


export default function AddToBuyListBox(props) {
  const { commodity,setCommodity } = props;
  var [quantity, setQuantity] = useState(0);
  const navigate = useNavigate();

const removeFromBuylist= async () => {
  try {
    if (quantity == 0)
    setQuantity(quantity);
  else
    setQuantity(quantity-1)
    const response = await axios.delete('/user/buylist/'+ commodity.id);

    console.log(response);

  } catch (e) {

  }
}
  const addToBuylist = async () => {
    try {
      setQuantity(quantity+1);
      const response = await axios.post('/user/buylist/'+ commodity.id);
      console.log(response);

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
