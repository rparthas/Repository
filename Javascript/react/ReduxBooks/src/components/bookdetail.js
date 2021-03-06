import React, { Component } from 'react';
import { connect } from 'react-redux';


class BookDetail extends Component {

    render() {

        if (!this.props.book) {
            return (<div>Select a book to get Started</div>);
        }

        return (
            <div>
                <h2> Details for: </h2>
                <h3> Title :{this.props.book.title} </h3>
                Pages :{this.props.book.pages}
            </div>

        );
    }
}

function mapStateToProps(state) {
    return ({
        book: state.activeBook,
    });
}



export default connect(mapStateToProps)(BookDetail);