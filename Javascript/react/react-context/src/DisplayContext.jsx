import React from 'react';
import { ColorContext } from './context';
import { fontCalculator } from './Constants';


const display = ({font}) =>{
    return (
        <ColorContext.Consumer>
        {
           (color) => 
                <h2 style ={{
                            fontSize : fontCalculator(font),
                            color
                        }}>
                            Display Text
                </h2>
        }
        </ColorContext.Consumer>
    );
} 

export default display;