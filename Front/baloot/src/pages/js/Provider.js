import axios from 'axios';
import '../css/Provider.css'
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom"
import CommodityPreview from '../../component/CommodityPreview';
import Request from '../../functions/Request';

export default function Provider() {

    const { providerId } = useParams();
    const [provider, setProvider] = useState();
    const navigate = useNavigate();

    useEffect(() => {
        async function fetchData() {
            try {

                const response = await Request.get('/providers/' + providerId);
                const providerRes = response.data.content;
                setProvider(providerRes);

            } catch (e) {
                if (e.response.status === 404) {
                    console.log(providerId);
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
                        <img className='img-provider' src={provider.image} alt='provider img' />
                        <div className='date-provider'>{"Since "+provider.registryDate}</div>
                        <div className='name-provider'>{provider.name}</div>
                    </div>
                    {provider.commoditiesList &&
                        <>
                            <div class="recomm-text">All provided commodities</div>
                            <div className="row p-3">
                                {
                                    provider.commoditiesList.map((commodity) => (
                                        <div className="col-3 mb-3" key={commodity.id}>
                                            <CommodityPreview
                                                id={commodity.id}
                                                imgUrl={commodity.imgUrl}
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