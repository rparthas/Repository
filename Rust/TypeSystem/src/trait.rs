fn main() {
    let number_list = vec![34, 50, 25, 100, 65];


    let result = find_largest(&number_list);
    println!("The largest number is {}", result);

    let char_list = vec!['y', 'm', 'a', 'q'];

    let result = find_largest(&char_list);
    println!("The largest character is {}", result);
}

fn find_largest<T:PartialOrd+Copy>(list: &Vec<T>) -> T {
    let mut largest = list[0];
    for &item in list.iter() {
        if item > largest {
            largest = item;
        }
    }
    largest
}