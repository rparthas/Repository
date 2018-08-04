import React from 'react';
import { DropdownButton, MenuItem } from 'react-bootstrap';
import { fonts } from './Constants';
import DisplayContext from './DisplayContext';

export default class FontChooserContext extends  React.Component{

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
            <DisplayContext font={this.state.font}/>
        </div>
    );
    }
}