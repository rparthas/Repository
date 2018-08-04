import React from 'react';
import { DropdownButton, MenuItem } from 'react-bootstrap';
import Display from './Display';
import { fonts } from './Constants';

export default class FontChooser extends  React.Component{

    state ={font:fonts[0]};

    render(){
        const fontChooser ='font-chooser';
        return (
        <div>
            <DropdownButton
                bsStyle='success'
                title={this.state.font}
                key={fontChooser}
                id={`dropdown-basic-${fontChooser}`}
                onSelect={font => this.setState({font})}
            >
                {fonts.map(font => 
                    <MenuItem eventKey={font}>{font}</MenuItem>
                )}
            </DropdownButton>
            <Display color={this.props.color} font={this.state.font}/>
        </div>
    );
    }
}