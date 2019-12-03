import random


def sort(arr):
    if len(arr) <= 0:
        return []
    if len(arr) == 1:
        return arr
    left_arr, right_arr = partition(arr)
    return sort(left_arr) + sort(right_arr)


def partition(arr):
    pivot_index = ((len(arr) - 1) / 2).__int__()
    left_arr = []
    right_arr = []
    for i in range(len(arr)):
        num = arr[i]
        if i == pivot_index:
            continue
        if num < arr[pivot_index]:
            left_arr.append(num)
        else:
            right_arr.append(num)
    left_arr.append(arr[pivot_index])
    return left_arr, right_arr


number = 100000
array = [i + 1 for i in range(number)]
random.shuffle(array)
print(sort(array)[0:10])
