
import '../css/commodityPage.css'
import axios from "axios";
import { useEffect, useState } from "react";
import Popup from 'reactjs-popup';
import star from "../../images/star.png"
import { Link, useNavigate, useParams } from "react-router-dom"
import RateCommodity from '../../component/RateCommodtiy'
import AddToBuyListBox from '../../component/AddToBuyListBox'
import CommentForm from '../../component/CommentForm';
import CommodityPreview from '../../component/CommodityPreview'
import Request from "../../functions/Request";

export default function Commodity(props) {
    const {setNumOfCart}=props;
    const [commodity, setCommodity] = useState(null);
    const { id } = useParams();
    const isLoggedIn = localStorage.getItem('userId');
    const userId = localStorage.getItem('userId');
    const [showMOre, setShowMoreFlage] = useState(false);
    const navigate = useNavigate();
    console.log(commodity);

    useEffect(() => {
        async function fetchData() {
            try {
                const response = await Request.get('commodities/' + id);
                const commodityRes = response.data.content;
                setCommodity(commodityRes);


            } catch (e) {
                if (e.response.status === 404) {
                    
                    navigate('/404');
                }
            }
        }

        fetchData();

    }, [])
    const updateRate = newCommodtiy => {
        commodity.userRate = newCommodtiy.userRate;
        commodity.numOfRate = newCommodtiy.numOfRate;
        commodity.rate = newCommodtiy.rate;

        setCommodity({ ...commodity });
    }

    return (
        <>
            {commodity ?
                <>
                    <div class="container-fluid product-box">

                        <div class="row product-container">

                            <div class="col-6 picture-container  text-primary">
                                <div class="product-img">
                                    <img src={commodity.imgUrl} alt="sample-product" />
                                </div>
                            </div>
                            <div class="col-6 info-container ">
                                <span class="info-header ">{commodity.name}</span>
                                <br />
                                <div class="d-flex align-items-center">
                                    <span class="info-stock">{commodity.inStock} left in stock</span>
                                    <div class="col-sm-3 d-flex align-items-center info-star">
                                        <img class="star-img img-responsive" src={star} alt="star" />
                                        <div class="star-num">
                                            {commodity.rate}
                                        </div>
                                        <div class="star-count">
                                            {'(' + commodity.numOfRate + ')'}
                                        </div>
                                    </div>
                                </div>
                                <span>by </span>
                                <Link className="info-provider" key={commodity.providerId} to={'/provider/' + commodity.providerId}>{commodity.provideName}</Link>
                                <br />
                                <span class="product-category-text">
                                    Category(s)
                                </span><br />
                                {
                                    showMOre || commodity["categories"].length <= 2 ?
                                        commodity["categories"].map((item) => (
                                            <div >
                                                <span class="product-category-text">
                                                    &#x2022; {item}
                                                </span><br />

                                            </div>

                                        ))
                                        :
                                        <>
                                            <div >
                                                <span class="product-category-text">
                                                    &#x2022; {commodity["categories"][0]}
                                                </span><br />

                                            </div>
                                            <div >
                                                <span class="product-category-text">
                                                    &#x2022; {commodity["categories"][1]}
                                                </span><br />

                                            </div>
                                            <span class="product-cat-showmore" onClick={()=> setShowMoreFlage(true)}>Show more...</span>
                                        </>


                                }

                                <div class="container-fluid card-box">
                                    <div class="row add-to-card-box">
                                        <div class="col-6 credit">{commodity.price+'$'}</div>
                                        <div class="col-6 add-to-card">
                                            <Popup trigger={<button >Add to card</button>} popupClass={"popup-content"}>
                                                <AddToBuyListBox commodity={commodity} setCommodity={setCommodity} setNumOfCart={setNumOfCart} />
                                            </Popup>

                                        </div>
                                    </div>
                                </div>
                                <div class="container-fluid ">
                                    <br />
                                    <div class="row ">
                                        <div class="col-9 rating">

                                            <span>Rate now</span><br />
                                            <RateCommodity updateRate={updateRate} userRate={commodity.userRate} commodityId={commodity.id} />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <CommentForm commodity={commodity} setCommodity={setCommodity} />
                    <br />
                    <div class="recomm-text">You also might like...</div>
                    <div className="row p-5">
                        {commodity.suggestionCommodity &&
                            commodity.suggestionCommodity.map((recCommodity) => (
                                <CommodityPreview
                                    id={recCommodity.id}
                                    imgUrl={recCommodity.imgUrl}
                                    name={recCommodity.name}
                                    inStock={recCommodity.inStock}
                                    price={recCommodity.price}
                                    setNumOfCart={setNumOfCart}
                                />
                            ))}
                    </div>
                </>
                :
                <div class="text-center mt-5">
                    <div class="spinner-border text-danger" role="status">
                        <span class="sr-only">Loading...</span>
                    </div>
                </div>
            }
        </>
    )
}