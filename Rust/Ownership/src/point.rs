#[derive(Debug, Copy, Clone)]
struct Point {
    x: i32,
    y: i32,
}

fn main() {
    let mut p1 = Point::new(24, 42);
    println!("{:#?}", p1);
    let p2 = p1;
    println!("{}", p1.x);
    inc_x(&mut p1);
    println!("{}", p1.x);
    println!("{}", p1.distance_from_origin());
    let p3 = Point { x: 1, y: 2 };
}

fn inc_x(point: &mut Point) {
    point.x += 1;
}

impl Point {
    fn distance_from_origin(&self) -> f64 {
        ((self.x.pow(2) + self.y.pow(2)) as f64).sqrt()
    }
    fn origin() -> Self {
        Point { x: 0, y: 0 }
    }
    fn new(x: i32, y: i32) -> Self {
        Self(x, y)
    }
}