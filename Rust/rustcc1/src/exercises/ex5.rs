pub fn main() {
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
