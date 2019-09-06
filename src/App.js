import React, { Component } from 'react';
import Busqueda from './Busqueda';



class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      sites: [],
    }
  }

  render() {
    return (
      <Busqueda />
    );
  }
}

export default App;