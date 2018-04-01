fn main() {
    let mut v = vec![1, 2, 3];
    v.push(4);
    println!("{:?}", v);
//    let fourth = v[3];
//    let fifth = & v.get(4);
//    println!("{:?}",fifth);
    for i in &mut v {
        println!("{}", i);
        *i *= 2;
    }
    println!("{:?}", v);

    for c in "नमस्ते".chars() {
        println!("{}", c);
    }

    use std::collections::HashMap;

    let mut scores = HashMap::new();

    scores.insert(String::from("Blue"), 10);
    scores.insert(String::from("Yellow"), 50);

    let teams  = vec![String::from("Blue"), String::from("Yellow")];
    let initial_scores = vec![10, 50];

    let team_scores: HashMap<_, _> = teams.iter().zip(initial_scores.iter()).collect();
    println!("{:?}", team_scores);
}