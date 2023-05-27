import React, { useState, useEffect } from "react";

import history from "../images/history.png";
import "./HistoryList.css";


export default function HistoryList(props) {
    const { user, setUser } = props;
    const [historyList, setHistoryList] = useState(user.userPurchasedList);


    return (
        <>
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
            {historyList.length ?
                historyList.map(item => (
                    <div class="history">
                        <img src={item.imgUrl} alt="s21" />
                        <div class="name">
                            <span>{item.name}
                            </span>
                        </div>
                        {/* <div class="cate">
                        {item["categories"].map(item => (
                            <span>{item},</span>
                        ))}
                    </div> */}
                        <div class="price">
                            <span>{item.price}</span>
                        </div>
                        <div class="provider">
                            <span>{item.provider}</span>
                        </div>
                        <div class="rating">
                            <span>{item.rate}</span>
                        </div>
                        <div class="in-stock">
                            <span>{item.inStock}</span>
                        </div>
                        <div class="quantity">
                            <span>{item.quantity}</span>
                        </div>
                    </div>))

                :
                <div className="empty-box">Your history is empty</div>

            }

        </>
    )
}