import React, { useState, useEffect } from "react";
import './BuylistList.css'
import AddToBuyListBox from "./AddToBuyListBox";

export default function BuylistList(props) {
    const {user,setUser}=props;
    const [userBuylist,setUserBuylist]=useState(user["buyList"])
    console.log(user);

    const deleteByIndex = index => {
        setUserBuylist(oldValues => {
            return oldValues.filter((_, i) => i !== index)
        })
        user["buyList"]=userBuylist;
    }
    const updateBuylistHandeling = editedCommodity => {

        const commodityIndex = user["buyList"].findIndex(x => x.id === editedCommodity.id);
        if (editedCommodity.quantity == 0)
            deleteByIndex(commodityIndex);
        else
            user["buyList"][commodityIndex] = editedCommodity;
        setUser({ ...user });
    }

    return (
        <>
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

            {userBuylist.map(item => (

                <div class="buylist">
                    <img src={item.imgUrl} alt="s21" />
                    <div class="name">
                        <span>{item.name}</span>
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
                    <AddToBuyListBox commodity={item} setCommodity={updateBuylistHandeling} />
                </div>
            ))}




        </>
    )
}