def recursive_sum(arr, index=0):
    if len(arr) == index:
        return 0
    return arr[index] + recursive_sum(arr, index + 1)


print(recursive_sum([x + 1 for x in range(100)]))
