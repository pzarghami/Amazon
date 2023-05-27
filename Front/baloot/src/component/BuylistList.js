import React, { useState, useEffect } from "react";
import './BuylistList.css'
import {  useNavigate } from "react-router-dom";
import AddToBuyListBox from "./AddToBuyListBox";
import buylist from "../images/buylist.png";

export default function BuylistList(props) {
    const { user, setUser, setNumOfCart } = props;
    const [userBuylist, setUserBuylist] = useState(user["buyList"])

    const navigate = useNavigate();


    const deleteByIndex = index => {
        setUserBuylist(oldValues => {
            return oldValues.filter((_, i) => i !== index)
        })
        user["buyList"] = userBuylist;
    }
    const updateBuylistHandeling = editedCommodity => {

        const commodityIndex = user["buyList"].findIndex(x => x.id === editedCommodity.id);
        if (editedCommodity.quantity == 0)
            deleteByIndex(commodityIndex);
        else
            user["buyList"][commodityIndex] = editedCommodity;
        setUser({ ...user });
    }
    const handleLinkToUser =  id => {
        navigate('/commodities/' + id);
    }
    return (
        <>
            <div class="cart-box">
                <img src={buylist} alt="buylist" />
                <span>Cart</span>
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
                        <th class="in-cart"><span>In Cart</span></th>
                    </tr>
                </thead>
                <tbody>
                    {userBuylist.length ?
                        userBuylist.map(item => (

                            <tr class="buylist">
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
                                <td><AddToBuyListBox commodity={item} setCommodity={updateBuylistHandeling} /></td>

                            </tr>
                        )) :
                        <div className="empty-box">Your cart is empty</div>
                    }

                </tbody>
            </table>

        </>
    )
}