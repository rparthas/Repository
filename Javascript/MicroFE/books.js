var app = new Vue({
    el: 'books',
    data: {
        books: ["book1", "book2", "book3"]
    },
    template: `<li v-for="book in books" v-on:click="select($event)">
    {{book}}
    </li>`,
    methods: {
        select: function (event) {
            var event = new CustomEvent("bookSelected", {
                detail: {
                    book: event.currentTarget.innerHTML.trim()
                }
            });
            document.dispatchEvent(event);
        }
    }
});