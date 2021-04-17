#![allow(dead_code, unused_variables, unused_mut)]

use crossbeam::channel;
use std::thread;
use std::time::Duration;

const STARTING_MISSILES:i32 = 8;
const READY_AMOUNT:i32 = 2;

pub fn ex1(){
    let  (mut missiles , ready) = (STARTING_MISSILES,READY_AMOUNT);
    println!("Firing {} of my {} missiles...", ready, missiles);
    missiles -= ready;
    println!("Missiles left: {}",missiles);
}

pub fn ex2() {
    let width = 4;
    let height = 7;
    let depth = 10;
    let area = area_of(width, height);
    
    println!("Area is {}", area);
    println!("Volume is {}",volume_of(width, height, depth));
}

fn area_of(x: i32, y: i32) -> i32 {
    x*y
}

fn volume_of(x: i32, y: i32, z:i32) -> i32 {
    x*y*z
}
	
pub fn ex3() {
    let coords: (f32, f32) = (6.3, 15.0);

    print_difference( coords.0,coords.1 );   

   
    let coords_arr = [coords.0,coords.1];              
    print_array(coords_arr);       


    let series = [1, 1, 2, 3, 5, 8, 13];
    ding(series[6]);


    let mess = ([3, 2], 3.14, [(false, -3), (true, -100)], 5, "candy");
    on_off(mess.2[1].0);

    print_distance(coords);
}

fn print_difference(x: f32, y: f32) {
    println!("Difference between {} and {} is {}", x, y, (x - y).abs());
}

fn print_array(a: [f32; 2]) {
    println!("The coordinates are ({}, {})", a[0], a[1]);
}

fn ding(x: i32) {
    if x == 13 {
        println!("Ding, you found 13!");
    }
}

fn on_off(val: bool) {
    if val {
        println!("Lights are on!");
    }
}

fn print_distance((x,y):(f32,f32)) {
    println!(
        "Distance to the origin is {}",
        ( x.powf(2.0) + y.powf(2.0) ).sqrt());
}

pub fn ex4() {
    let args: Vec<String> = std::env::args().skip(1).collect();
    for arg in args {
        if arg == "sum" {
            sum();
        }else if arg == "double"{
            double();
        }else{
            count(arg);
        }
    }
}

fn sum() {
    let mut sum = 0;
    for i in 7..=23{
        sum+=i;
    }
    println!("The sum is {}", sum);
}

fn double() {
    let mut count = 0;
    let mut x = 1;
    while x <= 500 {
        x*=2;
        count+=1;
    }
    println!("You can double x {} times until x is larger than 500", count);
}

fn count(arg: String) {
    let mut cnt =0;
    loop {
        if cnt ==8 {
            break;
        }
        cnt+=1;
        print!("{} ", arg);
    }

    println!(); 
}

pub fn ex5() {
    let mut arg: String = std::env::args().nth(1).unwrap_or_else(|| {
        println!("Please supply an argument to this program.");
        std::process::exit(-1);
    });

    inspect(&arg);
    change(&mut arg);
    println!("I have many {}", arg);

    if eat(arg) {
        println!("Might be bananas");
    } else {
        println!("Not bananas");
    }

    println!("1 + 2 = {}, even via references", add(&1, &2));
}

fn inspect(astr: &String){
    if astr.ends_with("s"){
        println!("{} is plural",astr);
    }else{
        println!("{} is singular",astr);
    }
}

fn change(astr: &mut String){
    if ! astr.ends_with("s"){
        astr.push_str("s");
    }
}

fn eat(astr: String)-> bool {
    return astr.starts_with("b") && astr.contains("a");
}

fn add(arg1 :&i32, arg2:&i32) -> i32{
    return (*arg1)+(*arg2);
}

trait Bite{
    fn bite(self: &mut Self);
}

#[derive(Debug)]
struct Grapes{
    amount_left: i32,
}

impl Bite for Grapes{
    fn bite(self: &mut Self){
        self.amount_left -= 1;
    }
}

fn bunny_nibbles<T: Bite>(bite: &mut T){
    for i in 0..4 {
        bite.bite();
    }
}


pub fn ex6() {
    // Once you finish #1 above, this part should work.
    let mut carrot = Carrot { percent_left: 100.0 };
    carrot.bite();
    println!("I take a bite: {:?}", carrot);

    
    let mut grapes = Grapes { amount_left: 100 };
    grapes.bite();
    println!("Eat a grape: {:?}", grapes);

    bunny_nibbles(&mut carrot);
    println!("Bunny nibbles for awhile: {:?}", carrot);
}

#[derive(Debug)] // This enables using the debugging format string "{:?}"
struct Carrot {
    percent_left: f32,
}

impl Bite for Carrot {
    fn bite(self: &mut Self) {
        // Eat 20% of the remaining carrot. It may take awhile to eat it all...
        self.percent_left *= 0.8;
    }
}

enum Shot{
    Bullseye,
    Hit(f64),
    Miss

}

impl Shot {
    // Here is a method for the `Shot` enum you just defined.
    fn points(self) -> i32 {
        return match self{
            Shot::Bullseye => 5,
            Shot::Miss => 0,
            Shot::Hit(x) => if x >= 3.0 { 1 } else { 2 }
        };
    }
}

pub fn ex7() {
    // Simulate shooting a bunch of arrows and gathering their coordinates on the target.
    let arrow_coords: Vec<Coord> = get_arrow_coords(5);
    let mut shots: Vec<Shot> = Vec::new();

    for coord in arrow_coords{
        coord.print_description();
        let dist = coord.distance_from_center();
        if dist < 1.0{
            shots.push(Shot::Bullseye);
        }else  if dist >= 1.0 && dist <= 5.0{
            shots.push(Shot::Hit(dist));
        }else{
            shots.push(Shot::Miss);
        }
    }

    let mut total = 0;
    for shot in shots{
        total += shot.points();
    }

    println!("Final point total is: {}", total);
}

// A coordinate of where an Arrow hit
#[derive(Debug)]
struct Coord {
    x: f64,
    y: f64,
}

impl Coord {
    fn distance_from_center(&self) -> f64 {
        (self.x.powf(2.0) + self.y.powf(2.0)).sqrt()
    }
    fn print_description(&self) {
        println!(
            "coord is {:.1} away, at ({:.1}, {:.1})",
            self.distance_from_center(),
            self.x,
            self.y);
    }

}

// Generate some random coordinates
fn get_arrow_coords(num: u32) -> Vec<Coord> {
    let mut coords: Vec<Coord> = Vec::new();
    for _ in 0..num {
        let coord = Coord {
            x: (rand::random::<f64>() - 0.5) * 12.0,
            y: (rand::random::<f64>() - 0.5) * 12.0,
        };
        coords.push(coord);
    }
    coords
}

fn expensive_sum(v: Vec<i32>) -> i32 {
    pause_ms(500);
    println!("Child thread: just about finished");
    v.iter()
        .filter(|x| { *x%2 == 0}) 
        .map(|&x| { x * x}) 
        .sum()
}

fn pause_ms(ms: u64) {
    thread::sleep(Duration::from_millis(ms));
}

pub fn ex8() {
    let my_vector = vec![2, 5, 1, 0, 4, 3];
    let handle = thread::spawn(move || {expensive_sum(my_vector) });

    
    for letter in vec!["a", "b", "c", "d", "e", "f"] {
        println!("Main thread: Letter {}", letter);
        pause_ms(200);
    }

    let sum = handle.join().unwrap();
    println!("The child thread's expensive sum is {}", sum);

    let (tx, rx) = channel::unbounded();
    let tx2 = tx.clone();
    let handle_a = thread::spawn(move || {
        pause_ms(0);
        tx2.send("Thread A: 1").unwrap();
        pause_ms(200);
        tx2.send("Thread A: 2").unwrap();
    });
    pause_ms(100); // Make sure Thread A has time to get going before we spawn Thread B
    let handle_b = thread::spawn(move || {
        pause_ms(0);
        tx.send("Thread B: 1").unwrap();
        pause_ms(200);
        tx.send("Thread B: 2").unwrap();
    });
    
    for msg in rx {
        println!("Main thread: Received {}", msg);
    }

    handle_a.join().unwrap();
    handle_b.join().unwrap();

    let (tx, rx) = channel::unbounded();
    let rx1 = rx.clone();
    let handle_a = thread::spawn(move || {
        for msg in rx {
            println!("thread1: Received {}", msg);
        }
    });
    let handle_b = thread::spawn(move || {
        for msg in rx1 {
            println!("thread2: Received {}", msg);
        }
    });

    for i in 1..=15{
        tx.send(i).unwrap();
    }

    drop(tx);
    handle_a.join().unwrap();
    handle_b.join().unwrap();
    
    println!("Main thread: Exiting.")
}