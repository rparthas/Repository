class Money {

    get amount() {
        return this._amount;
    }

    set amount(value) {
        this._amount = value;
        console.assert(value, "undefined value");
    }
}

const money = new Money();
money.amount = 12;
console.log(money.amount);
money._amount = 14;
console.log(money.amount);