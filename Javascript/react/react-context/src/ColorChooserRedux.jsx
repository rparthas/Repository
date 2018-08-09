import React from 'react';
import { AppContext } from './context';
import { DropdownButton, MenuItem } from 'react-bootstrap';
import { colors } from './Constants';

export default class ColorChooser extends React.Component{
   
    render() {
        const colorChooser ='Color-Chooser';
        return (
            <AppContext.Consumer>
                {
                    (store) => 
                        <div>
                            <DropdownButton
                                bsStyle='success'
                                title={store.state.color}
                                key={colorChooser}
                                id={`dropdown-basic-${colorChooser}`}
                                onSelect={color => store.actions.updateColor(color)}
                            >
                            {colors.map(color => 
                                <MenuItem eventKey={color}>{color}</MenuItem>
                            )}
                            </DropdownButton>
                        </div>
                    }
                
            </AppContext.Consumer>
        );
    }
}