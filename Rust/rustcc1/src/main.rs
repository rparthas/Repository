const STARTING_MISSILES:i32 = 8;
const READY_AMOUNT:i32 = 2;

fn main() {
    ex1();
    ex2();
}

fn ex1(){
    let  (mut missiles , ready) = (STARTING_MISSILES,READY_AMOUNT);
    println!("Firing {} of my {} missiles...", ready, missiles);
    missiles -= ready;
    println!("Missiles left: {}",missiles);
}


fn ex2() {
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