import { useEffect, useState } from "react";

import { Icon } from '@iconify/react';


export default function RateStar(props) {
  const { onClickHandler, onHoverHandler, starActive } = props;

   return (
    <div
      onClick={onClickHandler}
      className="rate-star"
      onMouseEnter={onHoverHandler}
    >
      <Icon
        icon="codicon:star-full"
        style={{ color: (starActive ? "orange" : "grey") }}
        className="star-icon "
      />
    </div>
  )
}