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
