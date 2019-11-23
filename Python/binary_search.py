def binary_search(arr, num, low=0, high=2):
    middle = ((high + low) / 2).__int__()
    if arr[middle] == num:
        return middle
    if low >= high:
        return -1
    if arr[middle] < num:
        return binary_search(arr, num, middle + 1, high)
    return binary_search(arr, num, low, middle - 1)


array = [i + 1 for i in range(100)]
while True:
    number = input("Enter your input for search")
    print(f"Index is {binary_search(array, int(number), 0, len(array) - 1)}")
