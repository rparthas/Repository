import React from 'react';

export default class SearchBar extends React.Component {

    constructor(props) {
        super(props);
        this.state = { term: '' };
        this.onStateChange = this.onStateChange.bind(this);
    }

    onStateChange(event) {
        this.setState({ term: event.target.value });
    }
    onSubmit(event) {
        event.preventDefault();
    }

    render() {
        return (<form onSubmit={(event) => this.onSubmit(event)} className="input-group">
            <input
                placeholder="Get a five day forecast in cities"
                className="form-control"
                value={this.state.term}
                onChange={this.onStateChange}
            />
            <span className="input-group-btn">
                <button type="submit" className="btn btn-secondary">
                    Search
            </button>
            </span>
        </form>);
    }
}