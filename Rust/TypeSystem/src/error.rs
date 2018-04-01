fn main(){
    use std::fs::File;
    let _f = match File::open("hello.txt") {
        Ok(file) => file,
        Err(error) => {
            panic!("There was a problem opening the file: {:?}", error)
        },
    };
}