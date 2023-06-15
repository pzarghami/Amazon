import axios from 'axios';
import { useEffect, useState } from 'react';
import './RateCommodity.css'
import RateStar from './RateStar';
import Request from "../functions/Request";


export default function RateCommodity(props) {

  const {userRate, commodityId, updateRate} = props;
  
  const [stars, setStars] = useState(Array(10).fill(false));

  const handleRate = async rate => {
    try {


      const response = await Request.post('/commodities/' + commodityId + '/rate/'+rate);


      if(response.data.status){
        updateRate(response.data.content);
        handleHover(rate)
      }

    }catch(e) {
      console.log(e);
    }
  }

  useEffect(() => {
    if(userRate) {

      handleHover(userRate);
    }
  }, [])

  const handleHover = position => {
    stars.map((star, index) => { 
      if(index + 1 <= userRate){
        stars[index] = true;
      }
      else if(index + 1 <= position){
        stars[index] = true;
      }
      else{
        stars[index] = false;
      }
    })
    setStars(stars.slice());
  }

  return (
    <>
      <div className="rate-stars-container">
        {
          stars.map((star, index) => (
            <RateStar
              key={index}
              starActive={star}
              onClickHandler={
                function (rate) {
                  return function () {
                    handleRate(rate);
                  }
                }(index + 1)
              }

              onHoverHandler={
                function (rate) {
                  return function () {
                    handleHover(rate);
                  }
                }(index + 1)
              }
            />
          ))
        }
      </div>
    </>
  )
}