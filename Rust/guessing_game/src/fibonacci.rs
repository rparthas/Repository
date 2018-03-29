fn fibonacci(n: i32) -> i32 {

    if n == 1 || n==0 {
      n
    }else{
        fibonacci(n-1)+fibonacci(n-2)
    }
}

fn main() {
    println!("fibonacci is {}",fibonacci(7));
}