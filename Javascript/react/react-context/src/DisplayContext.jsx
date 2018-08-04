import React from 'react';
import Context from './context';
import { fontCalculator } from './Constants';


const display = ({font}) =>{
    return (
        <Context.ColorContext.Consumer>
        {
           (color) => 
                <h2 style ={{
                            fontSize : fontCalculator(font),
                            color
                        }}>
                            Display Text
                </h2>
        }
        </Context.ColorContext.Consumer>
    );
} 

export default display;