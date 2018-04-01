#[derive(Debug)]
struct Rectangle {
    x: i32,
    y: i32,
}

impl Rectangle {
    fn area(&self) -> i32 {
        self.x * self.y
    }
}

#[derive(Debug)]
enum IpAddr {
    v4(u16, u16, u16, u16),
    v6(String),
}

fn build_rectangle(x: i32, y: i32) -> Rectangle {
    return Rectangle {
        x,
        y,
    };
}

enum Coin {
    Penny,
    Nickel,
    Dime,
    Quarter(UsState),
}

#[derive(Debug)] // So we can inspect the state in a minute
enum UsState {
    Alabama,
    Alaska,
}

fn value_in_cents(coin: Coin) -> u32 {
    match coin {
        Coin::Penny => {
            println!("Lucky penny!");
            1
        }
        Coin::Nickel => 5,
        Coin::Dime => 10,
        Coin::Quarter(state) => {
            print!("State quarter from {:?}! ", state);
            25
        },
    }
}

fn main() {
    let rect1 = Rectangle { x: 2, y: 4 };
    println!("{}", rect1.area());
    let rect2 = build_rectangle(3, 5);
    println!("{}", rect2.area());
    let home = IpAddr::v4(127, 0, 0, 1);
    let loopback = IpAddr::v6(String::from("::1"));
    println!("{:#?}", loopback);
    println!("{}",value_in_cents(Coin::Quarter(UsState::Alabama)));
    let some_u8_value = Some(3u8);
    if let Some(3) = some_u8_value {
        println!("three");
    }
}
