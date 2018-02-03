import React, { Component } from 'react';
import { connect } from 'react-redux';

class BookList extends Component {

    renderItems(){
        return this.props.books.map( book => {
            <li key={book.name} className="list-group-item">{book.name}</li>
        });
    }

    render() {
        return (
            <ul class="list-group col-sm-4">
             {this.renderItems()}
            </ul>
        );
    }
}

function mapStateToProps(state){
    return({
        books: state.books
    });
}

export default connect(mapStateToProps)(BookList);