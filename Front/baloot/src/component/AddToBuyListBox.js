import axios from "axios";
import { useState } from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus, faMinus } from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';
import './AddToBuyListBox.css'


export default function AddToBuyListBox(props) {
  const { commodity } = props;
  var [quantity, setQuantity] = useState(0);
  const navigate = useNavigate();


  const addToBuylist = async () => {
    try {
      //when backend is ready
      // const data = { quantity }
      // const userId = localStorage.getItem('userId');

      // const response = await axios.post('/users/' + userId + '/buylist/' + commodity.id);
      // if (response.data.status)
      //   navigate('/commodities/' + commodity.id);


    } catch (e) {

    }
  }
  return (
    <>
      <div className='quantity-box'>
        <span className="minus" onClick={() => {
          if (quantity == 0)
            setQuantity(quantity);
          else
            setQuantity(quantity--)
        }}>
          -
        </span>
        <span className="number"> {quantity} </span>
        <span className="plus" onClick={() => setQuantity(quantity++)}>
          +
        </span>
      </div>
      <button onClick={addToBuylist}>Sumbit</button>
    </>

  )
}
