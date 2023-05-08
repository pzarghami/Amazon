
import './CommodityPreview.css';
import axios from "axios";
import { Link, useLocation ,useNavigate} from "react-router-dom";
import { useEffect, useInsertionEffect, useState } from "react";

export default function CommodityPreview(props) {
    const { id, image, name, inStock, price } = props;
    const userId = localStorage.getItem('userId');
    const navigate = useNavigate();

    const [error, setError] = useState('');
    const handleAddToBuylist = async () => {
        try {
            setError('');
            const data = { commodityId: id }
            const response = await axios.post('/users/' + userId + '/watchlist/', data);
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
        navigate('/users/' + id);
    }
    return (
        <div class=" ml-0 product">
            <span class="title">{name}</span>
            <span class="stock">{inStock + " left in stock"}</span>

            <img onClick={ handleLinkToUser} src={image} />
            <div class="actions">
                <span class="price">
                    {price + "$"}
                </span>
                <div class="add-to-cart">
                    <span onClick={handleAddToBuylist} type="button" className="btn ">Add to cart</span>
                </div>
            </div>

            {error &&
                <div className="text-center text-danger">
                    <span className="lead text-danger">{error}</span>
                </div>
            }
        </div>
    );
}