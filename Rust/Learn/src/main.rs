//use std::net::TcpListener;
mod vars;

fn main() {
    let x = 1;
    let tuple = (1, "ram");
    let (no, name) = tuple;
    println!("value of x is {} {}", x, no);
    println!("value of tuple is {:?}", tuple);
    hello_word(name);

    println!("{name} loves {food}", name = "Ram", food = "ice-cream");
    vars::run()

//    let listener = TcpListener::bind("127.0.0.1:8080").unwrap();
//    for _stream in listener.incoming() {
//        println!("connected");
//    }
}

fn hello_word(name: &str) {
    println!("Hello {}", name);
}
