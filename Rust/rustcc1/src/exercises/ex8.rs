use crossbeam::channel;
use std::thread;
use std::time::Duration;


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

pub fn main() {
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