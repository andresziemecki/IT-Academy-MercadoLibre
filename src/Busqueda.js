import React, { Component } from 'react';
import './App.css';
import Articulos from './Articulos';

class Busqueda extends Component {

  constructor(props) {
    super(props);
    this.state = {
      items: [],
      value: '',
      sites: [],
      siteId: '',
      listaVacia: false,
    }
    this.verificar=this.verificar.bind(this)
  }

  render() {
    return (
      <div className="container flex">
        <select onChange={(e) => this.setState({ siteId: e.target.value })} className="select">
          <option value='' selected>Selecciona un pais</option>
          {this.state.sites.map((item, index) => {
            return (
              <option key={index} value={item.id}>{item.name}</option>
            )
          })}
        </select>
        <div className="input-group md-form form-sm form-1 pl-0">
          <input className="form-control my-0 py-1 input-search" onKeyDown={this.verificar} type="text" placeholder="Escribi el articulo y presiona enter" aria-label="Search" onChange={(event) => this.setState({ value: event.target.value })} name="busqueda" ></input>
        </div>
        { this.state.items.length == 0 ? null : <Articulos articulos={this.state.items} /> }
        { this.state.listaVacia ? <p>No se encontraron resultados</p> : null }
      </div>
    );
  }
  
  componentDidMount = () => {
    fetch('https://api.mercadolibre.com/sites')
      .then(response => {
        return response.json();
      })
      .then(responseJson => {
        this.setState({
          sites: responseJson,
        });
      }).catch(console.log);
  }

  verificar(e){
    if(e.key=='Enter'){
    this.setState({ listaVacia: true });
      fetch('https://api.mercadolibre.com/sites/' + this.state.siteId + '/search?q=' + this.state.value)
        .then(response => {
          return response.json();
        })
        .then(responseJson => {
          var listaVacia = responseJson.results.length == 0 ? true : false;
          this.setState({
            items: responseJson.results,
            listaVacia: listaVacia,
          });
        }).catch(e => {
          console.log(e)
        });
      }
  }
}

export default Busqueda;