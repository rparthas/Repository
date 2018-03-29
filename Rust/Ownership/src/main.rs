fn main() {
    let mut s1 = String::from("hello");
    let len = calculate_length(& mut s1);
    println!("The length of '{}' is {}.", s1, len); 
    let word = first_word(&s1[..]); 
    println!("slice is {}",word);
}

fn calculate_length(s: & mut String) -> usize {
    s.push_str(", world");
    s.len()
}

fn first_word(s: &str) -> &str {
    let bytes = s.as_bytes();

    for (i, &item) in bytes.iter().enumerate() {
        if item == b' ' {
            return &s[..i];
        }
    }

    &s[..]
}
