use std::ops::Add;

fn add<T: Add>(a: T, b: T) -> T::Output {
    b + a
}

fn main() {
    let a = 2.3;
    let b = 5.3;
    println!("{}", add(a,b));
}