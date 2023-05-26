import React, { useState,useEffect } from "react";
import axios from "axios";

import "./PayForm.css";

export default function PayForm(props) {
    const { setShowPopupPayingForm, user } = props;
    

    const { discount, setDiscount } = useState(user.discount);
    const { totalCost, setTotalCost } = useState(0);
    const { discountValue, setDiscountValue } = useState(0);
    const { error, setError } = useState(false);

    const handleDiscount = (event) => {
        event.preventDefault();
        setDiscount(discount);

    };

    const handleDiscountChange = (event) => {
        setDiscount(event.target.value);
    };

    async function handlePay() {
        try {
            const data = { totalCost: totalCost - discountValue }
            const response = await axios.get('buy/', data);
            setShowPopupPayingForm(false);
        }
        catch (e) {
            setError(true);
        }

    }
    useEffect(() => {
        async function fetchData() {
            const data = {  discount: discount }
            const response = await axios.get('discount/', data);
            const discountTemp = response.data.content;
            setDiscountValue(discountTemp);
        }
        fetchData();

    }, [discount]);

    return (

        <div className="popup">
            <div className="popup-content">
                <span className="close" onClick={() => setShowPopupPayingForm(false)}>
                    &times;
                </span>
                <div className="buylist-list">
                    {user["buyList"].map((item) => (
                        <>

                            <div >
                                <span class="item-in-buylist">
                                    &#x2022; {item}
                                </span><br />

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
                        <button type="submit">Confirm</button>
                    </div>
                </form>
                {discountValue &&
                    <div className="total-cost">{totalCost}</div>
                }
                {error &&
                    <div>Error</div> 
                }
                        <div className="total-cost-with-discount">{discountValue}</div>
                        <button className="pay-button" onClick={handlePay}></button>

                    </div>
        </div>




            )
}