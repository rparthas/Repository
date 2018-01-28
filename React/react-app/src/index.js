import React from 'react';
import ReactDOM from 'react-dom';
import './css/style.css'

class Note extends React.Component {
    constructor() {
        super();
        this.state = {
            editing: false,
            edited: false,
        };
        this.edit = this.edit.bind(this);
        this.save = this.save.bind(this);
    }

    edit() {

        this.setState({editing: true, edited: false});
    }

    save() {
        this.setState({editing: false, edited: true});

    }

    renderNormal() {

        return (<div className='note'>
                <h1>{this.props.children}</h1>
                <button onClick={this.edit}>Edit</button>
                <button>Delete</button>
            </div>
        );
    }

    renderEdited() {

        return (<div className='note'>
                <h1>{this.refs.comment.value}</h1>
                <button onClick={this.edit}>Edit</button>
                <button>Delete</button>
            </div>
        );
    }

    renderEdit() {

        return (<div className='note'>
                <textarea ref="comment" defaultValue={this.props.children}></textarea>
                <button onClick={this.save}>Save</button>
            </div>
        );
    }

    render() {
        console.log(this.state.editing);
        if (this.state.editing) {
            return this.renderEdit();
        }
        if (this.state.edited) {
            return this.renderEdited();
        }
        return this.renderNormal();
    }
}

ReactDOM.render(<div>
        <Note>text</Note>
        <Note>text1</Note>
        <Note>text2</Note>
    </div>
    , document.getElementById('root'));

