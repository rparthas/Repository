customElements.define('book-recommender', class extends HTMLElement {
    constructor() {
        super();

        const template = document.getElementById('book-recommender');
        const templateContent = template.content;

        this
            .attachShadow({mode: 'open'})
            .appendChild(templateContent.cloneNode(true));
    }
});