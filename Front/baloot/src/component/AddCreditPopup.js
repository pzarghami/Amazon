import React, { useState } from "react";

import "./AddCreditPopup.css";

export default function AddCreditPopup(props) {
    const {creditValue,setShowPopup, handleCreditSubmit, handleCreditChange}=props;

    return (

        <div className="popup">
            <div className="popup-content">
                <span className="close" onClick={() => setShowPopup(false)}>
                    &times;
                </span>
                <form className="credit-form" onSubmit={handleCreditSubmit}>
                    <label htmlFor="credit-input">Enter credit amount:</label>
                    <input
                        id="credit-input"
                        type="number"
                        placeholder="$Amount"
                        aria-label="$Amount"
                        value={creditValue}
                        onChange={handleCreditChange}
                    ></input>
                    <div className="button-container">
                        <button type="submit">Confirm</button>
                        <button onClick={() => setShowPopup(false)}>Cancel</button>
                    </div>
                </form>
            </div>
        </div>




    )
}