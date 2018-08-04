import React from 'react';
import Context from './context';
import FontChooserContext from './FontChooserContext';
import { DropdownButton, MenuItem } from 'react-bootstrap';
import { colors } from './Constants';

export default class ColorChooserContext extends React.Component{
    state =  { color :colors[0] };

    render() {
        const colorChooser ='Color-Chooser';   
        return (
            <div>
                <DropdownButton
                    bsStyle='success'
                    title={this.state.color}
                    key={colorChooser}
                    id={`dropdown-basic-${colorChooser}`}
                    onSelect={color => this.setState({color})}
                >
                    {colors.map(color => 
                        <MenuItem eventKey={color}>{color}</MenuItem>
                    )}
                </DropdownButton>
                <Context.ColorContext.Provider value={this.state.color}>
                    <FontChooserContext/>
                </Context.ColorContext.Provider>
            </div>
        );
    }
    

}