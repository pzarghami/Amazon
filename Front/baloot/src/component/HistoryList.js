import React, { useState, useEffect } from "react";
import {  useNavigate } from "react-router-dom";
import history from "../images/history.png";
import "./HistoryList.css";


export default function HistoryList(props) {
    const { user, setUser } = props;
    const [historyList, setHistoryList] = useState(user.userPurchasedList);
    const navigate = useNavigate();
    const handleLinkToUser =  id => {
        navigate('/commodities/' + id);
    }
    return (
        <>
            <div class="history-box">
                <img src={history} alt="history" />
                <span>History</span>
            </div>
            <table>
                <thead>
                    <tr class="topic-box">
                        <th class="image" ><span>Image</span></th>
                        <th class="name"><span>Name</span></th>
                        <th class="cate"><span>Categories</span></th>
                        <th class="price"><span>Price</span></th>
                        <th class="provider"><span>Provider</span></th>
                        <th class="rating"><span>Rating</span></th>
                        <th class="stock"><span>In Stock</span></th>
                        <th class="quantity"><span>Quantity</span></th>
                    </tr>
                </thead>
                <tbody>
                    {historyList.length ?
                        historyList.map(item => (

                            <tr class="history">
                                <td class="image" ><img src={item.imgUrl} onClick={() => handleLinkToUser(item.id)}/></td>
                                <td class="name"><span>{item.name}</span></td>
                                <td class="cate"><span>
                                    {item["categories"].map(item => (
                                        <>{item}
                                            <br />
                                        </>
                                    ))}</span></td>
                                <td class="price"><span>{'$' + item.price}</span></td>
                                <td class="provider"><span>{item.provideName}</span></td>
                                <td class="rating"><span>{item.rate}</span></td>
                                <td class="in-stock"><span>{item.inStock}</span></td>
                                <td class="quantity"><span>{item.quantity}</span></td>

                            </tr>
                        )) :
                        <div className="empty-box">Your history is empty</div>
                    }

                </tbody>
            </table>

        </>
    )
}