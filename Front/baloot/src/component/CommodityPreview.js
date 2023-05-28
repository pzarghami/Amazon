
import './CommodityPreview.css';
import axios from "axios";
import { Link, useLocation, useNavigate ,useParams} from "react-router-dom";
import { useEffect, useInsertionEffect, useState } from "react";

export default function CommodityPreview(props) {
    const { id, imgUrl, name, inStock, price, setNumOfCart } = props;
    const userId = localStorage.getItem('userId');
    const { commodityId } = useParams();
    const [popupBuylist, setPopupBuylist] = useState(false);
    const navigate = useNavigate();
    const location = useLocation();

    const [error, setError] = useState('');
    const handleAddToBuylist = async () => {
        try {
            setError('');
            const response = await axios.post('/user/buylist/' + id);
            console.log(id);

            if (response.data.status) {
                console.log(response);
                setNumOfCart();
                setPopupBuylist(true);

            }


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
    const handleLinkToUser = async () => {
        console.log("hi");
        navigate('/commodities/' + id);
    }
    return (
        <div class="  product">
            <span class="title">{name}</span>
            <span class="stock">{inStock + " left in stock"}</span>
            <Link to={"/commodities/" + id}>
                <img className="image" src={imgUrl} />
            </Link>
            <div class="actions">
                <span class="price">
                    {price + "$"}
                </span>
                <button className="add-to-cart" onClick={handleAddToBuylist} type="button" >
                    Add to cart
                </button>
            </div>
            {popupBuylist &&
                <div className="popup">
                    <div className="popup-content">
                        <span className="close" onClick={() => setPopupBuylist(false)}>
                            &times;
                        </span>
                        <div className="button-container">
                            Succesfully Added.
                        </div>
                    </div>
                </div>}
            {error &&
                <div className="text-center text-danger">
                    <span className="button-container">{error}</span>
                </div>
            }
        </div>
    );
}