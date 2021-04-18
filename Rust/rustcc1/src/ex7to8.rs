use crossbeam::channel;
use std::thread;
use std::time::Duration;

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