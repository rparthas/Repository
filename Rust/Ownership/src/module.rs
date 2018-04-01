mod own{
    fn say_hi(){
        println!("hi");
    }
    pub fn say_hello(){
        println!("hello");
    }
}



fn main(){
    own::say_hello();
    use own::{say_hello as alias};
    alias();

}

