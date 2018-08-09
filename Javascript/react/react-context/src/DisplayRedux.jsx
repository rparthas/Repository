import React from 'react';
import { AppContext } from './context';
import { fontCalculator } from './Constants';

const display = ({font}) =>{
    return (
        <AppContext.Consumer>
        {
           ({state}) => 
                <h2 style ={{
                            fontSize : fontCalculator(state.font),
                            color:state.color
                        }}>
                            Display Text
                </h2>
        }
        </AppContext.Consumer>
    );
} 

export default display;