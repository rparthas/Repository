extern crate rand;

use rand::Rng;
use std::cmp::Ordering;
use std::io;

fn main() {
    let secret_number = rand::thread_rng().gen_range(1, 101);
    println!("Guess the number!");

    println!("Please input your guess.");

    loop {
        let mut guess = String::new();

        io::stdin().read_line(&mut guess).expect("Failed to process input");
        let guess: i32 = match guess.trim().parse(){
            Ok(num) => num,
            Err(_) => {
                println!("Please enter a valid number");
                continue;
            }
        };

        println!("You guessed: {}", guess);

        match guess.cmp(&secret_number) {
            Ordering::Less => println!("Increase it mate"),
            Ordering::Greater => println!("Lower it chum"),
            Ordering::Equal => {
                println!("You win");
                break;
            }
        }
    }
}
