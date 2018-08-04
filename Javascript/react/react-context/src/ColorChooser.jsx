import React from 'react';
import FontChooser from './FontChooser';
import { DropdownButton, MenuItem } from 'react-bootstrap';
import { colors } from './Constants';

export default class ColorChooser extends React.Component{
    state =  {color :'Red'};

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
                <FontChooser color={this.state.color}/>
            </div>
        );
    }
    

}