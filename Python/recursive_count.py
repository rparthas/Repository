def recursive_count(arr, index=0):
    if len(arr) == index:
        return 0
    return 1 + recursive_count(arr, index + 1)


print(recursive_count([x + 1 for x in range(100)]))
