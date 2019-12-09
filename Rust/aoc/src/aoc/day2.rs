fn program_alarm(x: i32, y: i32) -> i32 {
    let mut input = [1, 0, 0, 3, 1, 1, 2, 3, 1, 3, 4, 3, 1, 5, 0, 3, 2, 1, 13, 19, 1, 9, 19, 23, 1, 6, 23, 27, 2, 27, 9, 31,
        2, 6, 31, 35, 1, 5, 35, 39, 1, 10, 39, 43, 1, 43, 13, 47, 1, 47, 9, 51, 1, 51, 9, 55, 1, 55, 9, 59, 2, 9, 59, 63, 2, 9, 63,
        67, 1, 5, 67, 71, 2, 13, 71, 75, 1, 6, 75, 79, 1, 10, 79, 83, 2, 6, 83, 87, 1, 87, 5, 91, 1, 91, 9, 95, 1, 95, 10, 99, 2, 9,
        99, 103, 1, 5, 103, 107, 1, 5, 107, 111, 2, 111, 10, 115, 1, 6, 115, 119, 2, 10, 119, 123, 1, 6, 123, 127, 1, 127, 5, 131, 2,
        9, 131, 135, 1, 5, 135, 139, 1, 139, 10, 143, 1, 143, 2, 147, 1, 147, 5, 0, 99, 2, 0, 14, 0];
    input[1] = x;
    input[2] = y;
    let mut loop_iter = 0;
    while loop_iter < input.len() {
        match input[loop_iter] {
            1 => {
                input[input[loop_iter + 3] as usize] = input[input[loop_iter + 1] as usize] + input[input[loop_iter + 2] as usize];
                loop_iter = loop_iter + 2;
            }
            2 => {
                input[input[loop_iter + 3] as usize] = input[input[loop_iter + 1] as usize] * input[input[loop_iter + 2] as usize];
                loop_iter = loop_iter + 2;
            }
            99 => break,
            _ => ()
        }
        loop_iter = loop_iter + 1;
    }
    println!("Final state is {}", input[0]);
    return input[0];
}


pub fn find_correct_program() {
    let i = 49;
    let mut j = 20;
    while j < 40 {
        j = j + 1;
        let a = program_alarm(i, j);
        if a == 19690720 {
            println!("X is {},Y is {}", i, j);
            break;
        }
    }
}