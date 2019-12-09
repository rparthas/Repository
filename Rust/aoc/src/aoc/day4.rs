use std::collections::HashMap;

pub fn predict_passwords() {
    let mut count = 0;
    for i in 206938..679128 {
        if valid_num(i) {
            count = count + 1;
        }
    }
    println!("valid number count is {}", count);
}


fn valid_num(num: i32) -> bool {
    let mut adj_num = HashMap::new();
    let mut mut_num = num;
    let mut digits = [0; 6];
    for loop_num in 0..6 {
        digits[loop_num] = mut_num / (10_i32.pow(5 - loop_num as u32));
        mut_num = mut_num - (digits[loop_num] * 10_i32.pow(5 - loop_num as u32));
    }

    let mut adj_val = false;
    let mut increasing = true;
    for loop_num in 0..5 {
        if digits[loop_num] == digits[loop_num + 1] {
            match adj_num.get(&digits[loop_num]) {
                Some(&number) => adj_num.insert(digits[loop_num], number + 1),
                _ => adj_num.insert(digits[loop_num], 1)
            };
        }
        if digits[loop_num + 1] >= digits[loop_num] {
            increasing = increasing && true;
        } else {
            increasing = false;
        }
    }
    for (key, val) in adj_num.iter() {
        if (val % 2 == 0) {
            adj_val = false;
            break;
        }
        adj_val = true;
    }
    return adj_val && increasing;
}