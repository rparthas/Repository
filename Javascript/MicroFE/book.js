class BookDetails extends React.Component {
    state = {
        bookDetails: {
            'book1': 23.30,
            'book2': 34.50,
            'book3': 44.50
        }
    };

    render() {
        const {
            book
        } = this.props;
        if (book) {
            const price = this.state.bookDetails[book];
            return `${book} costs ${price}`;
        }
        return null;
    }
}

ReactDOM.render( < BookDetails book = "book1" /> , document.getElementById('book'));

document.addEventListener("bookSelected", function (event) {
    ReactDOM.render(<BookDetails book = {event.detail.book}/> , document.getElementById('book'));
});