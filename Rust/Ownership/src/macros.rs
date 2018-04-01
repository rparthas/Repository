#![macro_use ]

#[macro_export]
macro_rules! welcome {
     () => (
        println!("Welcome to Rust macro")
     );
 }