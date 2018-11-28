class Books extends HTMLElement {
    constructor() {
        super();
        var shadow = this.attachShadow({mode: 'open'});
        this._wrapper = document.createElement('div');
        this._wrapper.innerHTML = "<ul><li>Book1</li><li>Book2</li></ul>";
        shadow.appendChild(this._wrapper);
    }
}
customElements.define('book-list', Books);