import { Link } from 'react-router-dom';
import './CommodityPreview.css';

export default function CommodityPreview(props) {
    const { image, name, inStock, price } = props;

    return (
        <div class=" ml-0 product">
            <span class="title">{name}</span>
            <span class="stock">{inStock + " left in stock"}</span>
            <img src={image} />
            <div class="actions">
                <span class="price">
                    {price + "$"}
                </span>
                <div class="add-to-cart">
                    <button type="button" className="btn btn-outline-brown">Add to cart</button>
                </div>
            </div>

        </div>
    );
}