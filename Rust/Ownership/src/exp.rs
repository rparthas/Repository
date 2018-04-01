enum Expr {
    Add(i32, i32),
    Sub(i32, i32),
    Val(i32),
}

fn main() {
    print_expr(Expr::Sub(40, 2));
    print_expr(Expr::Add(40, 2));
    println!("{}", uppercase(b'A'));
}

fn print_expr(expr: Expr) {
    match expr {
        Expr::Add(x, y) => println!("{}", x + y),
        Expr::Sub(x, y) => println!("{}", x - y),
        Expr::Val(x) => println!("{}", x),
    }
}

fn uppercase(c: u8) -> u8 {
    match c {
        b'a'...b'z' => c - 32,
        _ => c,
    }
}
