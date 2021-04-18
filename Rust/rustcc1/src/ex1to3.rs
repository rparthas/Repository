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