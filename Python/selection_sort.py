import random


def sort(arr):
    for x in range(len(arr)):
        index = find_small(arr, x)
        arr[index], arr[x] = arr[x], arr[index]
    return arr


def find_small(arr, start):
    small = start
    i = start + 1
    while i < len(arr):
        if arr[i] < arr[small]:
            small = i
        i = i + 1
    return small


number = 1000
array = [i + 1 for i in range(number)]
random.shuffle(array)
print(sort(array))
