# First write a "helper" function with two extra parameters
# left, right that ddedscribes the search region as shown below
def findCrossoverIndexHelper(x, y, left, right):
    # Note: Output index i such that
    #         left <= i <= right
    #         x[i] <= y[i]
    # First, Write down our invariants as assertions here
    assert (len(x) == len(y))
    assert (left >= 0)
    assert (left <= right - 1)
    assert (right < len(x))
    # Here is the key property we would like to maintain.
    assert (x[left] > y[left])
    assert (x[right] < y[right])

    # your code here
    mid = (left + right) // 2
    if x[mid+1] < y[mid+1]:
        if mid > left and x[mid] < y[mid]:
            return findCrossoverIndexHelper(x, y, left, mid - 1)
        else:
            return mid
    else:
            return findCrossoverIndexHelper(x, y, mid + 1, right)


# Define the function findCrossoverIndex that wil
# call the helper function findCrossoverIndexHelper
def findCrossoverIndex(x, y):
    assert (len(x) == len(y))
    assert (x[0] > y[0])
    n = len(x)
    assert (x[n - 1] < y[n - 1])  # Note: this automatically ensures n >= 2 why?
    # your code here
    return findCrossoverIndexHelper(x, y, 0, n - 1)


# BEGIN TEST CASES
j1 = findCrossoverIndex([0, 1, 2, 3, 4, 5, 6, 7], [-2, 0, 4, 5, 6, 7, 8, 9])
print('j1 = %d' % j1)
assert j1 == 1, "Test Case # 1 Failed"

j2 = findCrossoverIndex([0, 1, 2, 3, 4, 5, 6, 7], [-2, 0, 4, 4.2, 4.3, 4.5, 8, 9])
print('j2 = %d' % j2)
assert j2 == 1 or j2 == 5, "Test Case # 2 Failed"

j3 = findCrossoverIndex([0, 1], [-10, 10])
print('j3 = %d' % j3)
assert j3 == 0, "Test Case # 3 failed"

j4 = findCrossoverIndex([0, 1, 2, 3], [-10, -9, -8, 5])
print('j4 = %d' % j4)
assert j4 == 2, "Test Case # 4 failed"

print('Congratulations: all test cases passed - 10 points')
# END TEST CASES
