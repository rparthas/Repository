import React from 'react';
import Context from './context';
import { fontCalculator } from './Constants';

const display = ({font}) =>{
    return (
        <Context.AppContext.Consumer>
        {
           ({state}) => 
                <h2 style ={{
                            fontSize : fontCalculator(state.font),
                            color:state.color
                        }}>
                            Display Text
                </h2>
        }
        </Context.AppContext.Consumer>
    );
} 

export default display;