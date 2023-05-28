import React, { useState, useEffect } from "react";
import axios from "axios";

import "./PayForm.css";

export default function PayForm(props) {
    const { setShowPopupPayingForm, user, setUser } = props;
    console.log(user);

    const [discount, setDiscount] = useState(null);
    const [totalCost, setTotalCost] = useState(0);
    const [discountValue, setDiscountValue] = useState(0);
    const [error, setError] = useState(false);

    async function handleClose() {
        try {
            const data = { totalCost: totalCost - discountValue }
            const response = await axios.delete('discount');
            setShowPopupPayingForm(false);
        }
        catch (e) {
            setError(true);
        }
    }
    const handleDiscountChange = (event) => {
        setDiscount(event.target.value);
    };

    async function handlePay() {
        try {
            const response = await axios.post('pay');
            const tempUser=response.data.content;
            console.log(response);
            if (response.data.status) {
                setUser(tempUser);
                console.log("After setUser", user);
                console.log(setUser);

            }
            setShowPopupPayingForm(false);
        }
        catch (e) {
            console.log(e);
            setError(true);
        }

    }
    const handleDiscount = async event => {
        event.preventDefault();
        try {
            const data = { discountCode: discount }
            const response = await axios.post("discount", data);
            if (response.data.status) {
                setDiscountValue(response.data.content);
            }

        } catch (e) {

        }

    };
    useEffect(() => {
        async function fetchData() {
            const response = await axios.get('price');
            setTotalCost(response.data.content);

        }
        fetchData();

    }, []);


    return (

        <div className="popup">

            <div className="popup-content">
                <span className="close" onClick={handleClose}>
                    &times;
                </span>
                <div>Your Cart</div>
                <div className="buylist-list">
                    {user["buyList"].map((item) => (
                        <>
                            <div className="buylist-item" >
                                <span class="item-in-buylist">
                                    &#x2022; {item.name + "*" + item.quantity}
                                </span>
                                <span class="price-item-in-buylist">
                                    {(item.price * item.quantity) + '$'}
                                </span>



                            </div>
                        </>))}
                </div>
                <form className="discount-form" onSubmit={handleDiscount}>
                    <label htmlFor="discount-input">Enter your discount:</label>
                    <input
                        id="discount-input"
                        type="text"
                        placeholder="Discount"
                        aria-label="Discount"
                        value={discount}
                        onChange={handleDiscountChange}
                    ></input>
                    <div className="button-container">
                        <button type="submit">Submit</button>
                    </div>
                </form>
                {discountValue ?
                    <>
                        <div className="total-cost">{totalCost}</div>
                        <div className="total-cost-with-discount">{discountValue}</div>
                    </> :
                    <div className="total-cost-with-discount">{totalCost}</div>

                }
                {error &&
                    <div>Error</div>
                }

                <div className="button-container">
                    <button className="pay-button" onClick={handlePay}>Buy!</button>
                    <button className="close-button" onClick={handleClose}>Close</button>
                </div>

            </div>
        </div>




    )
}