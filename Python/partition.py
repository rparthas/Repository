def swap(a, i, j):
    assert 0 <= i < len(a), f'accessing index {i} beyond end of array {len(a)}'
    assert 0 <= j < len(a), f'accessing index {j} beyond end of array {len(a)}'
    a[i], a[j] = a[j], a[i]


def tryPartition(a):
    # implementation of Lomuto partitioning algorithm
    n = len(a)
    pivot = a[n - 1]  # choose last element as the pivot.
    i, j = 0, 0  # initialize i and j both to be 0
    for j in range(n - 1):  # j = 0 to n-2 (inclusive)
        # Invariant: a[0] .. a[i] are <= pivot
        #            a[i+1]...a[j-1] are > pivot
        if a[j] <= pivot:
            swap(a, i + 1, j)
            i = i + 1
    swap(a, i + 1, n - 1)  # place pivot in its correct place.
    return i + 1  # return the index where we placed the pivot


def testIfPartitioned(a, k):
    # TODO : test if all elements at indices < k are all <= a[k]
    #         and all elements at indices > k are all > a[k]
    # return TRUE if the array is correctly partitioned around a[k] and return FALSE otherwise
    assert 0 <= k < len(a)
    # your code here
    for i in range(k):
        if a[i] > a[k]:
            return False
    for i in range(k + 1, len(a)):
        if a[i] <= a[k]:
            return False
    return True


assert testIfPartitioned([-1, 5, 2, 3, 4, 8, 9, 14, 10, 23], 5) == True, ' Test # 1 failed.'
assert testIfPartitioned([-1, 5, 2, 3, 4, 8, 9, 14, 11, 23], 4) == False, ' Test # 2 failed.'
assert testIfPartitioned([-1, 5, 2, 3, 4, 8, 9, 14, 23, 21], 0) == True, ' Test # 3 failed.'
assert testIfPartitioned([-1, 5, 2, 3, 4, 8, 9, 14, 22, 23], 9) == True, ' Test # 4 failed.'
assert testIfPartitioned([-1, 5, 2, 3, 4, 8, 9, 14, 8, 23], 5) == False, ' Test # 5 failed.'
assert testIfPartitioned([-1, 5, 2, 3, 4, 8, 9, 13, 9, -11], 5) == False, ' Test # 6 failed.'
assert testIfPartitioned([4, 4, 4, 4, 4, 8, 9, 13, 9, 11], 4) == True, ' Test # 7 failed.'
print('Passed all tests (10 points)')


# Write an array called a1 that will be incorrectly partitioned by the tryPartition algorithm above
# Your input when run on tryPartition algorithm should raise an out of bounds array access exception
# in the swap function or fail to partition correctly.

## Define an array a1 below of length > 0 that will be incorrectly partitioned by tryPartition algorithm.
## We will test whether your solution works in the subsequent cells.
# your code here
a1 = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
assert( len(a1) > 0)



# Write an array called a2 that will be incorrectly partitioned by the tryPartition algorithm above
# Your input when run on tryPartition algorithm should raise an out of bounds array access exception
# in the swap function or fail to partition correctly.
# a2 must be different from a1

# your code here
a2 = [1,2,3,4,5,6,7,8,9,10,11]
assert( len(a2) > 0)
assert (a1 != a2)


# Write an array called a3 that will be incorrectly partitioned by the tryPartition algorithm above
# Your input when run on tryPartition algorithm should raise an out of bounds array access exception
# in the swap function or fail to partition correctly.
# a3 must be different from a1, a2

# your code here
a3 = [1,2,3,4,5,6]
assert( len(a3) > 0)
assert (a3 != a2)
assert (a3 != a1)


def dummyFunction():
    pass


try:
    j1 = tryPartition(a1)
    assert not testIfPartitioned(a1, j1)
    print("success1")
    print('Partitioning was unsuccessful - this is what you were asked to break the code')
except Exception as e:
    print(f'Assertion failed {e} - this is fine since you were asked to break the code.')

try:
    j2 = tryPartition(a2)
    assert not testIfPartitioned(a2, j2)
    print("success2")
except Exception as e:
    print(f'Assertion failed {e} - this is fine since you were asked to break the code.')

try:
    j3 = tryPartition(a3)
    assert not testIfPartitioned(a3, j3)
    print("success3")
except Exception as e:
    print(f'Assertion failed {e} - this is fine since you were asked to break the code.')

dummyFunction()

print('Passed 5 points!')


