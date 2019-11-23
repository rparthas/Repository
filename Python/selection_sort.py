def sort(arr):
    for x in range(len(arr)):
        index = find_small(arr, x)
        swap(arr, index, x)
    return arr


def find_small(arr, start):
    small = start
    i = start + 1
    while i < len(arr):
        if arr[i] < arr[small]:
            small = i
        i = i + 1
    return small


def swap(arr, i, j):
    temp = arr[i]
    arr[i] = arr[j]
    arr[j] = temp


number = 1000
array = [number - i for i in range(number)]
print(array)
print(sort(array))
