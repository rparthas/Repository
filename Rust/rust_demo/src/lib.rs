#[cfg(test)]
mod test {
    use std::net::TcpStream;
    use std::io::prelude::*;
    use std::time::Duration;

    #[test]
    fn conn_established() {
        let stream = TcpStream::connect("127.0.0.1:8080");
        assert!(stream.is_ok(), "Connection not established. check if server is running at port 80");
    }

    #[test]
    fn check_response() {

        let mut stream = TcpStream::connect("127.0.0.1:8080").unwrap();
        stream.set_read_timeout(Some(Duration::from_secs(5))).unwrap();
        stream.write(b"GET / HTTP/1.1\r\n").unwrap();
        let mut result = String::new();
        stream.read_to_string(& mut result).unwrap();
        assert!(result.contains("Hi from Rust"));
    }

    #[test]
    fn check_error() {

        let mut stream = TcpStream::connect("127.0.0.1:8080").unwrap();
        stream.set_read_timeout(Some(Duration::from_secs(5))).unwrap();
        stream.write(b"POST / HTTP/1.1\r\n").unwrap();
        let mut result = String::new();
        stream.read_to_string(& mut result).unwrap();
        println!("{}",result);
        assert!(result.contains("Oops!"));
    }
}