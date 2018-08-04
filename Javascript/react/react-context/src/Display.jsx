import React from 'react';
import { fontCalculator } from './Constants';



const display = ({color,font}) =>{
    return (
        <h2 style ={{
            fontSize : fontCalculator(font),
            color
        }}>
            Display Text
        </h2>
    );
} 

export default display;