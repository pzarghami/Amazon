
import logo from '../../images/toggle.png'
import axios from "axios";
import { useEffect, useInsertionEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import CommodityPreview from '../../component/CommodityPreview';
import '../css/commodities.css'
import '../css/root.css'
import '../css/footer.css'
import userEvent from '@testing-library/user-event';


export default function Commodities() {
    const [commodities, setCommodities] = useState(null);

    const [commoditiesFetch, setFetchCommodities] = useState(null);

    const [filterBy, setFilterBy] = useState();
    const [searchValue, setSearchValue] = useState("");

    const location = useLocation();


    useEffect(() => {
        async function fetchData() {
            try {
                const response = await axios.get("commodities");
                console.log(response);
                const commoditiesList = response.data;

                console.log(commoditiesList);
                setFetchCommodities(commoditiesList);
                setCommodities(commoditiesList);
                console.log(commodities);
            } catch (e) {
                console.log(e);
            }
        }
        fetchData();

    }, []);
    return (
        <>
            <div class="page-container">
                <div class="sort-container">
                    <span class="available-sort-container">Available commodities</span>
                    <img class="toggle-sort-container" src={logo} alt="toggle" />
                    <span class="sort-by-sort-container">sort by:</span>
                    <span class="sorted-by-sort-container">name</span>
                    <div class="category-sort-container">
                        <span class="align-middle">price</span>
                    </div>
                </div>
                <div class="products-container">
                    <div className="row p-3">
                        {/* {commodities && 
                        commodities[0].name} */}
                        {commodities &&
                            Object.keys(commodities).map((item) => (
                                <div className="col-3 mb-3" key={item.id}>
                                    <Link to={"/commodities/" + commodities[item].id}>
                                        <CommodityPreview
                                            image={commodities[item].image}
                                            name={commodities[item].name}
                                            inStock={commodities[item].inStock}
                                            price={commodities[item].price}
                                        />
                                    </Link>
                                </div>

                            ))}
                    </div>

                </div>

            </div>
        </>
    )
}

