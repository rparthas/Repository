use std::fs::File;
use std::io::prelude::*;
use std::net::TcpListener;
use std::net::TcpStream;
use std::str;

fn main() {
    let listener = TcpListener::bind("127.0.0.1:8080").unwrap();

    for stream in listener.incoming() {
        match stream {
            Ok(stream) => {
                handle_connection(stream);
                println!("Connection established!");
            }
            Err(_e) => { println!("Connection Failed!"); }
        }
    }
}

fn handle_connection(mut stream: TcpStream) {
    let mut buffer = [0; 512];
    stream.read(&mut buffer).unwrap();
    let index = b"GET / HTTP/1.1\r\n";
    let search = b"GET /?search=";

    println!("{:?}",str::from_utf8(&buffer));


    let (status_line, filename) = if buffer.starts_with(index) {
        ("HTTP/1.1 200 OK\r\n\r\n", "index.html")
    } else if buffer.starts_with(search){
        ("HTTP/1.1 200 OK\r\n\r\n", "search.html")
    } else {
        ("HTTP/1.1 404 NOT FOUND\r\n\r\n", "404.html")
    };


    let mut file = File::open(filename).unwrap();

    let mut contents = String::new();
    file.read_to_string(&mut contents).unwrap();

    let response = format!("{}\r\n\r\n{}", status_line, contents);

    stream.write(response.as_bytes()).unwrap();
    stream.flush().unwrap();
}