fn main() {
    let heart_eyed_cat = '😻';
    let tup = (500, 6.4, true);
    let (x, y, z) = tup;
    println!("The value of y is: {}", y);
    println!("The first value is {}", tup.0);
    another_fn(x, z);

    let y = {
        let x = 3;
        x + 1
    };
    println!("The value of y is: {}", y);

    let number = 17;

    if number % 4 == 0 {
        println!("number is divisible by 4");
    } else if number % 3 == 0 {
        println!("number is divisible by 3");
    } else if number % 2 == 0 {
        println!("number is divisible by 2");
    } else {
        println!("number is not divisible by 4, 3, or 2");
    }

    let a = [10, 20, 30, 40, 50];
    let mut i=0;

    for element in a.iter() {
        println!("the value is: {}", element);
    }

    while i < a.len() {
        println!("Number is {}",a[i]);
        i=i+1;
    }

    for number in (1..5).rev() {
        println!("{}!", number);
    }
}


fn another_fn(x: i32, z: bool) {
    println!("The value of x and z are {} and {}", x, z);
}