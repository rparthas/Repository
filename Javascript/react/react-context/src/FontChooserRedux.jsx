import React from 'react';
import { AppContext } from './context';
import { DropdownButton, MenuItem } from 'react-bootstrap';
import { fonts } from './Constants';

export default class FontChooser extends  React.Component{

    render(){
        const fontChooser ='font-chooser';
        return (
            <AppContext.Consumer>
                {
                    (store) => 
                    <div>
                        <DropdownButton
                            bsStyle='success'
                            title={store.state.font}
                            key={fontChooser}
                            id={`dropdown-basic-${fontChooser}`}
                            onSelect={font => store.actions.updateFont(font)}
                        >
                            {fonts.map(font => 
                                <MenuItem eventKey={font}>{font}</MenuItem>
                            )}
                        </DropdownButton>
                    </div>
                }
               
            </AppContext.Consumer>
    );
    }
}