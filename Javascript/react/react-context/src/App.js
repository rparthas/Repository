import React, { Component } from 'react';
import ColorChooser from './ColorChooser';
import ColorChooserContext from './ColorChooserContext';
import { AppContext } from './context';
import ColorChooserRedux from './ColorChooserRedux';
import FontChooserRedux from './FontChooserRedux';
import DisplayRedux from './DisplayRedux';
import { colors, fonts} from './Constants';
import './App.css';


class App extends Component {

  state ={
    color:colors[0],
    font: fonts[0]
  }

  render() {
    return (
      <div className="App">
        { this.renderApp() }
      </div>
    );
  }

  renderApp(){
    let app = <ColorChooser/>;
    if(this.props.context){
    app = (<ColorChooserContext/>);
    }else if(this.props.redux){
      app = (<AppContext.Provider value={{
        state: this.state,
        actions:{
          updateColor:(color) => this.setState({color}),
          updateFont:(font) => this.setState({font})
        }
      }}>
          <ColorChooserRedux/>
          <FontChooserRedux/>
          <DisplayRedux/>
    </AppContext.Provider>);
    }
    return app;
  }
}

export default App;
