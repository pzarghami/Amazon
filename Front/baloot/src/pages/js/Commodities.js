
import toggleOff from '../../images/toggle.png'
import toggleOn from '../../images/toggle-on.png'
import axios from "axios";
import { useEffect, useInsertionEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import CommodityPreview from '../../component/CommodityPreview';
import '../css/commodities.css'
import '../css/root.css'
import '../css/footer.css'
import SearchBarHeader from '../../component/SearchBarHeader';

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
export default function Commodities(props) {
    const { setNumOfCart } = props;
    const [commodities, setCommodities] = useState(null);

    const [commoditiesFetch, setFetchCommodities] = useState(null);
    const [activeSorting, setSortingActive] = useState(0);
    const [availableCommodities, setAvailableCommodities] = useState(0);

    const location = useLocation();

    const filterAvailableCommodities = (item) => {
        if (item.inStock != 0) {

            return true;
        }
        return false;
    }
    const getFilterFunc = (filterMode) => {
        if (filterMode == "inStock") return filterAvailableCommodities;

    };
    useEffect(() => {
        async function fetchFilter() {
    
          if (location.search) {
            const searchText = location.search;
          const params = new URLSearchParams(searchText);
    
          const filter = params.get("filterBy");
          const value = params.get("searchValue");
    
          setCommodities(null);
    
          try {
            const response = await axios.get("commodities?filterBy="+filter+"&filterValue="+value);
            const commoditiesList = response.data.content;
            setCommodities(commoditiesList);
            setFetchCommodities(commoditiesList);
          } catch (e) {
            console.log(e);
          }
        }
      }
      fetchFilter();
      }, [location.search]);

    useEffect(() => {
        async function fetchData() {
            try {
                const response = await axios.get("commodities");
                const commoditiesList = response.data.content;
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
                                        imgUrl={commodities[item].imgUrl}
                                        name={commodities[item].name}
                                        inStock={commodities[item].inStock}
                                        price={commodities[item].price}
                                        setNumOfCart={setNumOfCart}
                                    />

                                </div>

                            ))}
                    </div>

                </div>

            </div>
        </>
    )
}

