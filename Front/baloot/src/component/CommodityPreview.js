
import './CommodityPreview.css';
import axios from "axios";
import { Link, useLocation ,useNavigate} from "react-router-dom";
import { useEffect, useInsertionEffect, useState } from "react";

export default function CommodityPreview(props) {
    const { id, imgUrl, name, inStock, price } = props;
    const userId = localStorage.getItem('userId');
    const [numOfCartStorage,setStorage]= useState(0);
    const navigate = useNavigate();

    const [error, setError] = useState('');
    const handleAddToBuylist = async () => {
        try {
            setError('');
            const response = await axios.post('/user/buylist/'+ id);
            setStorage(numOfCartStorage+1);
            localStorage.setItem('numOfCartStorage',numOfCartStorage);

        } catch (e) {
            console.log(e);
            if (e.response.data.message === "inStock") {
                setError("Out of Stock")
            }
            if (e.response.data.message === "price") {
                setError("Your invetory is not enough")
            }
            if (e.response.data.message === "login") {
                setError("Please login first")
            }
            else {
                setError(" login first")
            }

        }
    }
    const handleLinkToUser =  async () => {
        navigate('/commodities/' + id);
    }
    return (
        <div class="  product">
            <span class="title">{name}</span>
            <span class="stock">{inStock + " left in stock"}</span>

            <img className="image"onClick={ handleLinkToUser} src={imgUrl} />
            <div class="actions">
                <span class="price">
                    {price + "$"}
                </span>
                <button className="add-to-cart" onClick={handleAddToBuylist} type="button" >
                    Add to cart
                </button>
            </div>

            {error &&
                <div className="text-center text-danger">
                    <span className="lead text-danger">{error}</span>
                </div>
            }
        </div>
    );
}