
import toggleOff from '../../images/toggle.png'
import toggleOn from '../../images/toggle-on.png'
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
    const [availableCommodities, setAvailableCommodities] = useState(0);

    const location = useLocation();

    const filterName = (item) => {
        return item.name.includes(searchValue);
    };

    const filterCat = (item) => {
        for (var cat in item.categories) {
            if (item.categories[cat].includes(searchValue)) {
                return true;
            }
        }
        return false;
    };

    const filterProvider = (item) => {
        return item.providerId.includes(searchValue);
    };
    const filterAvailableCommodities = (item) => {
        if (item.inStock != 0){

            return true;
        }
        return false;
    }
    const getFilterFunc = (filterMode) => {
        if (filterMode === "name") return filterName;

        if (filterMode === "category") return filterCat;

        if (filterMode == "inStock") return filterAvailableCommodities;

    };

    useEffect(() => {
        if (location.search) {
            const searchText = location.search;
            const params = new URLSearchParams(searchText);

            const filter = params.get("filterBy");
            const value = params.get("searchValue");

            setFilterBy(filter);
            setSearchValue(value);
        }
    }, [location.search]);

    useEffect(() => {

        if (!commoditiesFetch || !commodities) return;
        let newComm = commoditiesFetch.slice();
        newComm = newComm.filter(getFilterFunc(filterBy));

        setCommodities(newComm);
    }, [filterBy, searchValue]);


    useEffect(() => {
        async function fetchData() {
            try {
                const response = await axios.get("commodities");
                const commoditiesList = response.data;


                setFetchCommodities(commoditiesList);
                setCommodities(commoditiesList);
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
    useEffect(() => {
        if (!commoditiesFetch || !commodities) return;
        let newComm = commoditiesFetch.slice();
        newComm = newComm.filter(getFilterFunc("inStock"));
        setCommodities(newComm);

    }, [availableCommodities]);

    return (
        <>
            <div class="page-container">
                <div class="sort-container">
                    <span class="available-sort-container">Available commodities</span>
                    <img class="toggle-sort-container" onClick={() => setAvailableCommodities(~availableCommodities)}
                        src={availableCommodities ? toggleOn : toggleOff}
                        alt="toggle" />
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

