extern crate tcp;

fn main() {
    use tcp::*;
    client::connect();
    network::connect();
    network::server::connect();
}