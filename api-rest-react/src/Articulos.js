import React, { Component } from 'react';

const Articulos = ({ articulos }) => {

  return (
    <div className="container flex">
      {articulos.map((item, index) => {
        return (
          <ul class="list img-list">
            <li className="list-group-item">
              <a target="blank" href={item.permalink} class="inner">
                <div class="li-img">
                  <img src={item.thumbnail} />
                </div>
                <div class="li-text">
                  <h3 class="li-head">{item.title}</h3>
                </div>
              </a>
            </li>
          </ul>
        )
      })}
    </div>
  )


};

export default Articulos;