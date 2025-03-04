def integerCubeRootHelper(n, left, right):
    cube = lambda x: x * x * x  # anonymous function to cube a number
    assert (n >= 1)
    assert (left < right)
    assert (left >= 0)
    assert (right < n)
    assert (cube(left) < n), f'{left}, {right}'
    assert (cube(right) > n), f'{left}, {right}'
    # your code here

    mid = (left + right) // 2
    mid_cubed = cube(mid)
    if mid_cubed <= n < cube(mid + 1):
        return mid
    if cube(mid + 1) <= n < cube(mid + 2):
        return mid + 1
    if cube(mid - 1) <= n < cube(mid):
        return mid - 1
    if mid_cubed < n:
        return integerCubeRootHelper(n, mid + 1, right)
    else:
        return integerCubeRootHelper(n, left, mid - 1)


# Write down the main function
def integerCubeRoot(n):
    assert (n > 0)
    if (n == 1):
        return 1
    if (n == 2):
        return 1
    return integerCubeRootHelper(n, 0, n - 1)


assert (integerCubeRoot(1) == 1)
assert (integerCubeRoot(2) == 1)
assert (integerCubeRoot(4) == 1)
assert (integerCubeRoot(7) == 1)
assert (integerCubeRoot(8) == 2)
assert (integerCubeRoot(20) == 2)
assert (integerCubeRoot(26) == 2)
for j in range(27, 64):
    assert (integerCubeRoot(j) == 3)
for j in range(64, 125):
    assert (integerCubeRoot(j) == 4)
for j in range(125, 216):
    assert (integerCubeRoot(j) == 5)
for j in range(216, 343):
    assert (integerCubeRoot(j) == 6)
for j in range(343, 512):
    assert (integerCubeRoot(j) == 7)
print('Congrats: All tests passed! (10 points)')
