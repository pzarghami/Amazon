import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import person from "../../images/person.png";
import mail from "../../images/mail.png";
import date from "../../images/date.png";
import location from "../../images/location.png";

import "../css/user.css";
import AddCreditPopup from "../../component/AddCreditPopup";
import PayForm from "../../component/PayForm"
import BuylistList from "../../component/BuylistList";
import HistoryList from "../../component/HistoryList";

export default function User(props) {

    const {setNumOfCart}=props;
    const [showPopup, setShowPopup] = useState(false);
    const navigate = useNavigate();
    const [showPopupPayingForm, setShowPopupPayingForm] = useState(false);
    const [creditValue, setCreditValue] = useState(null);
    const [user, setUser] = useState(null);

    const isLoggedIn = localStorage.getItem('userLoggedIn');
    const userId = localStorage.getItem('userId');
    const handleAddAmount = (event) => {
        event.preventDefault();
        setShowPopup(true);

    };
    const handleCreditSubmit = async event => {
        event.preventDefault();
        try{
            setCreditValue(creditValue);
            setShowPopup(false);
            const data = { amount: creditValue }
            const response = await axios.post("addCredit", data);
            const amount = parseInt(creditValue);
            setCreditValue(0);
            if(response.data.status){
                setUser(prevUser => ({
                    ...prevUser,
                    credit: prevUser.credit + amount
                  }));
                setCreditValue(0);
            }

        }catch(e){

        }

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

            } catch (e) {
                console.log(e);
            }
        }
        fetchData();

    }, []);
    const handleLogout = async () => {
        try {
          localStorage.removeItem('userLoggedIn');
          localStorage.removeItem('userId');
          const response = await axios.post('/auth/logout');
          if (response.data.status) {
            navigate('/commodities');
          }
        } catch (e) {
          console.log(e);
        }
      }
      console.log(user);
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
                        <button className="logout" onClick={handleLogout}>Logout</button>
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

                    <BuylistList user={user} setUser={setUser} setNumOfCart={setNumOfCart}/>

                    <button class="pay" onClick={e => setShowPopupPayingForm(true)} type="submit">
                        Pay now!
                    </button>
                    {showPopupPayingForm &&
                        <PayForm setShowPopupPayingForm={setShowPopupPayingForm} user={user} setUser={setUser}/>
                    }
                    <HistoryList user={user} setUser={setUser}/>
                </div>
            }
        </>
    )
}