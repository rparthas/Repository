import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';

const props ={
    redux:true,
    context:false
}

ReactDOM.render(<App { ...props }/>, document.getElementById('root'));
registerServiceWorker();
