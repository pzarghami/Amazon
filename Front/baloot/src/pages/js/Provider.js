import axios from 'axios';
import '../css/Provider.css'
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom"
import CommodityPreview from '../../component/CommodityPreview';

export default function Provider(props) {

    const { providerId } = useParams();
    const [provider, setProvider] = useState();
    const navigate = useNavigate();


    useEffect(() => {
        async function fetchData() {
            try {
                console.log(providerId);
                const response = await axios.get('providers/' + 1);

                const commodityRes = response.data;
                console.log(commodityRes);

                
                setProvider(commodityRes);
            } catch (e) {
                if (e.response.status === 404) {
                    console.log(providerId);
                    //navigate('/404');
                }
            }
        }

        fetchData();

    }, [])
    return (
        <>
            {provider &&

                <div className='provider-container'>
                    <div className='info-provider-container'>
                        <imp className='img-provider' src={provider.image} alt='provider img' />
                    </div>
                    {provider.commodities &&
                        <>
                            <div class="recomm-text">All provided commodities</div>
                            <div className="row p-3">
                                {
                                    provider.commodities.map((commodity) => (
                                        <div className="col-3 mb-3" key={commodity.id}>
                                            <CommodityPreview
                                                id={commodity.id}
                                                image={commodity.image}
                                                name={commodity.name}
                                                inStock={commodity.inStock}
                                                price={commodity.price}
                                            />
                                        </div>
                                    ))}


                            </div>
                        </>
                    }

                </div>
            }
        </>
    )
}