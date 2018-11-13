def binary_search(arr, num, low=0, high=2):
    middle = ((high-low)/2).__int__()
    if(arr[middle] == num):
        return {'index': middle, 'insert': middle}
    elif(low == high):
        return {'index': -1, 'insert': high}
    elif(arr[middle] > num):
        return binary_search(arr, num, middle, high)
    else:
        return binary_search(arr, num, low, middle)


def swap(arr, index):
    arr.append(arr[len(arr)-1])
    pointer = len(arr)-2
    while(pointer >= index):
        arr[pointer] = arr[pointer-1]
        pointer -= 1


arr = [2, 4, 5]
while True:
    choice = input("Enter 1 for add 2 for search ")
    number = input("Enter your input ")
    if(choice == '1'):
        insert = binary_search(arr, int(number), 0, len(arr)-1)['insert']
        print(f"Index is {insert}")
        swap(arr, insert)
        print(arr)
    elif(choice == '2'):
        index = binary_search(arr, int(number), 0, len(arr)-1)['index']
        print(f"Index is {index}")
