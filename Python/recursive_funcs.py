import math
import random


def recursive_sum(arr):
    if len(arr) == 0:
        return 0
    return arr[0] + recursive_sum(arr[1:])


def recursive_count(arr):
    if len(arr) == 0:
        return 0
    return 1 + recursive_count(arr[1:])


def max_num(arr):
    if len(arr) == 0:
        return -math.inf
    curr_num = arr[0]
    max_value = max_num(arr[1:])
    if curr_num > max_value:
        return curr_num
    return max_value


input_arr = [x + 1 for x in range(997)]
random.shuffle(input_arr)
print(recursive_sum(input_arr))
print(recursive_count(input_arr))
print(max_num(input_arr))
