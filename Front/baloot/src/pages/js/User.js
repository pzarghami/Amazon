import React, { useState, useEffect } from "react";
import axios from "axios";
import { useSearchParams } from "react-router-dom";
import person from "../../images/person.png";
import mail from "../../images/mail.png";
import date from "../../images/date.png";
import location from "../../images/location.png";
import buylist from "../../images/buylist.png";
import history from "../../images/history.png";
import "../css/user.css";
import AddCreditPopup from "../../component/AddCreditPopup";
import PayForm from "../../component/PayForm"

export default function User() {
    const [showPopup, setShowPopup] = useState(false);
    const [showPopupPayingForm, setShowPopupPayingForm] = useState(false);
    const [creditValue, setCreditValue] = useState(null);
    const [user, setUser] = useState(null);

    const isLoggedIn = localStorage.getItem('userLoggedIn');
    const userId = localStorage.getItem('userId');
    const handleAddAmount = (event) => {
        event.preventDefault();
        console.log("handleamount");
        setShowPopup(true);
        console.log(showPopup)
    };

    const handleCreditSubmit = (event) => {
        event.preventDefault();
        setCreditValue(creditValue);
        setShowPopup(false);
    };

    const handleCreditChange = (event) => {
        setCreditValue(event.target.value);
    };

    useEffect(() => {
        async function fetchData() {
            try {
                const data = { username: userId }
                const response = await axios.get("user", data);
                const userTemp = response.data.content;
                setUser(userTemp);
                console.log(user);
            } catch (e) {
                console.log(e);
            }
        }
        fetchData();

    }, []);
    useEffect(() => {
        async function fetchData() {
            try {
                const data = { amount: creditValue }
                const response = await axios.get("addCredit", data);

            } catch (e) {
                console.log(e);
            }
        }
        fetchData();

    }, [creditValue]);
    return (
        <>
            {user &&
                <div className="page-container">
                    <div className="info">
                        <div className="information">
                            <img src={person} alt="person" />
                            <span>{user.username}</span>
                            <br />
                            <img src={mail} alt="mail" />
                            <span>{user.email}</span>
                            <br />
                            <img src={date} alt="date" />
                            <span>{user.birthDate}</span>
                            <br />
                            <img src={location} alt="location" />
                            <span>{user.address}</span>
                        </div>
                        <form id="credit" className="credit" onSubmit={handleAddAmount}>
                            <span>${user.credit}</span>
                            <button className="add-credit" type="submit">
                                Add More Credit
                            </button>
                        </form>
                        {showPopup && (
                            <AddCreditPopup creditValue={creditValue} setShowPopup={setShowPopup} handleCreditSubmit={handleCreditSubmit} handleCreditChange={handleCreditChange} />
                        )}
                    </div>
                    <div class="cart-box">
                        <img src={buylist} alt="buylist" />
                        <span>Cart</span>
                    </div>
                    <div class="topic-box">
                        <div class="image">
                            <span>Image</span>
                        </div>
                        <div class="name">
                            <span>Name</span>
                        </div>
                        <div class="cate">
                            <span>Categories</span>
                        </div>
                        <div class="price">
                            <span>price</span>
                        </div>
                        <div class="provider">
                            <span>Provider ID</span>
                        </div>
                        <div class="rating">
                            <span>Rating</span>
                        </div>
                        <div class="stock">
                            <span>In Stock</span>
                        </div>
                        <div class="in-cart">
                            <span>In Cart</span>
                        </div>

                    </div>

                    {user["buyList"].map(item => (
                        <div class="buylist">
                            <img src="../assets/images/s21.png" alt="s21" />
                            <div class="name">
                                <span>galaxy S21</span>
                            </div>
                            <div class="cate">
                                <span>Technology,Phone</span>
                            </div>
                            <div class="price">
                                <span>$21000000</span>
                            </div>
                            <div class="provider">
                                <span>1234</span>
                            </div>
                            <div class="rating">
                                <span>8.3</span>
                            </div>
                            <div class="in-stock">
                                <span>17</span>
                            </div>
                            <div class="in-cart">
                                <span> - </span>
                                <span> 1 </span>
                                <span> + </span>
                            </div>
                        </div>
                    ))}

                    <button class="pay" onClick={e => setShowPopupPayingForm(true)}>
                        <span>Pay now!</span>
                    </button>
                    {showPopupPayingForm &&
                        <PayForm setShowPopupPayingForm={setShowPopupPayingForm} user={user} />
                    }
                    <div class="history-box">
                        <img src={history} alt="history" />
                        <span>History</span>
                    </div>
                    <div class="topic-box">
                        <div class="image">
                            <span>Image</span>
                        </div>
                        <div class="name">
                            <span>Name</span>
                        </div>
                        <div class="cate">
                            <span>Categories</span>
                        </div>
                        <div class="price">
                            <span>price</span>
                        </div>
                        <div class="provider">
                            <span>Provider ID</span>
                        </div>
                        <div class="rating">
                            <span>Rating</span>
                        </div>
                        <div class="stock">
                            <span>In Stock</span>
                        </div>
                        <div class="quantity">
                            <span>quantity</span>
                        </div>
                    </div>
                    <div class="history">
                        <img src="../assets/images/s21.png" alt="s21" />
                        <div class="name">
                            <span>Mom's
                            </span>
                            <br />
                            <span>spaghetti</span>
                        </div>
                        <div class="cate">
                            <span>food</span>
                        </div>
                        <div class="price">
                            <span>$60000</span>
                        </div>
                        <div class="provider">
                            <span>313</span>
                        </div>
                        <div class="rating">
                            <span>10</span>
                        </div>
                        <div class="in-stock">
                            <span>0</span>
                        </div>
                        <div class="quantity">
                            <span>3</span>
                        </div>
                    </div>
                    <div class="history2">
                        <img src="../assets/images/mic.png" alt="mic" />
                        <div class="name">
                            <span>Dre's
                            </span>
                            <br />
                            <span>Microphone</span>
                        </div>
                        <div class="cate">
                            <span>Technology</span>
                        </div>
                        <div class="price">
                            <span>$4200000</span>
                        </div>
                        <div class="provider">
                            <span>4321</span>
                        </div>
                        <div class="rating">
                            <span>8.5</span>
                        </div>
                        <div class="in-stock">
                            <span>22</span>
                        </div>
                        <div class="quantity">
                            <span>1</span>
                        </div>
                    </div>

                </div>
            }
        </>
    )
}