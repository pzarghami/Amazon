
import logo from '../../images/toggle.png'
import axios from "axios";
import { useEffect, useInsertionEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import CommodityPreview from '../../component/CommodityPreview';
import '../css/commodities.css'
import '../css/root.css'
import '../css/footer.css'

const nameCompare = (a, b) => {
    if (a.name > b.name) return 1;
    else if (a.name === b.name) return 0;
    else return -1;
};

const priceCompare = (a, b) => {
    if (a.price > b.price) return 1;
    else if (a.price === b.price) return 0;
    else return -1;
};
export default function Commodities() {
    const [commodities, setCommodities] = useState(null);

    const [commoditiesFetch, setFetchCommodities] = useState(null);

    const [filterBy, setFilterBy] = useState();
    const [searchValue, setSearchValue] = useState("");
    const [activeSorting, setSortingActive] = useState(0);

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

    useEffect(() => {
        let compareFunction;
        if (activeSorting) {

            compareFunction = nameCompare;
        } else {

            compareFunction = priceCompare;
        }
        if (commodities !== null) {
            commodities.sort(compareFunction);
            setCommodities(commodities.slice());
        }

    }, [activeSorting]);

    return (
        <>
            <div class="page-container">
                <div class="sort-container">
                    <span class="available-sort-container">Available commodities</span>
                    <img class="toggle-sort-container" src={logo} alt="toggle" />
                    <span class="sort-by-sort-container">sort by:</span>
                    {activeSorting ?
                        <>
                            <button className="sort-name-selectedButton ">name</button>
                            <button onClick={() => setSortingActive(0)} class="category-sort-container-notselectedButton align-middle">price</button>
                        </>
                        :
                        <>
                            <button onClick={() => setSortingActive(1)} className=' sort-name-notselectedButton '>name</button>
                            <button class="category-sort-container-selectedButton align-middle">price</button>
                        </>

                    }
                </div>
                <div class="products-container">
                    <div className="row p-3">
                        {commodities &&
                            Object.keys(commodities).map((item) => (
                                <div className="col-3 mb-3" key={item.id}>

                                    <CommodityPreview
                                        id={commodities[item].id}
                                        image={commodities[item].image}
                                        name={commodities[item].name}
                                        inStock={commodities[item].inStock}
                                        price={commodities[item].price}
                                    />

                                </div>

                            ))}
                    </div>

                </div>

            </div>
        </>
    )
}

