extern crate mylib;

use mylib::demo;

mod macros;

pub fn main(){
    welcome!();
    demo();
}