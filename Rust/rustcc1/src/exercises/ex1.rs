const STARTING_MISSILES:i32 = 8;
const READY_AMOUNT:i32 = 2;

pub fn main(){
    let  (mut missiles , ready) = (STARTING_MISSILES,READY_AMOUNT);
    println!("Firing {} of my {} missiles...", ready, missiles);
    missiles -= ready;
    println!("Missiles left: {}",missiles);
}