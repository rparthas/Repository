var app = new Vue({
    el: '#root',
    data: {
        books: ["book1", "book2", "book3"]
    },
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