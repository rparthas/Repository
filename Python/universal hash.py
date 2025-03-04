from random import random


def dot_product(lst_a, lst_b):
    and_list = [elt_a * elt_b for (elt_a, elt_b) in zip(lst_a, lst_b)]
    return 0 if sum(and_list) % 2 == 0 else 1


# encode a matrix as a list of lists with each row as a list.
# for instance, the example above is written as the matrix
# H = [[0,1,0,1],[1,0,0,0],[1,0,1,1]]
# encode column vectors simply as a list of elements.
# you can use the dot_product function provided to you.
def matrix_multiplication(H, lst):
    return [dot_product(row, lst) for row in H]


# your code here


# Generate a random m \times n matrix
# see the comment next to matrix_multiplication for how your matrix must be returned.
def return_random_hash_function(m, n):
    # return a random hash function wherein each entry is chosen as 1 with probability >= 1/2 and 0 with probability < 1/2
    # your code here
    return [[1 if random() >= 0.5 else 0 for _ in range(n)] for _ in range(m)]


A1 = [[0, 1, 0, 1], [1, 0, 0, 0], [1, 0, 1, 1]]
b1 = [1, 1, 1, 0]
c1 = matrix_multiplication(A1, b1)
print('c1=', c1)
assert c1 == [1, 1, 0], 'Test 1 failed'

A2 = [[1, 1], [0, 1]]
b2 = [1, 0]
c2 = matrix_multiplication(A2, b2)
print('c2=', c2)
assert c2 == [1, 0], 'Test 2 failed'

A3 = [[1, 1, 1, 0], [0, 1, 1, 0]]
b3 = [1, 0, 0, 1]
c3 = matrix_multiplication(A3, b3)
print('c3=', c3)
assert c3 == [1, 0], 'Test 3 failed'

H = return_random_hash_function(5, 4)
print('H=', H)
assert len(H) == 5, 'Test 5 failed'
assert all(len(row) == 4 for row in H), 'Test 6 failed'
assert all(elt == 0 or elt == 1 for row in H for elt in row), 'Test 7 failed'

H2 = return_random_hash_function(6, 3)
print('H2=', H2)
assert len(H2) == 6, 'Test 8 failed'
assert all(len(row) == 3 for row in H2), 'Test 9 failed'
assert all(elt == 0 or elt == 1 for row in H2 for elt in row), 'Test 10 failed'
print('Tests passed: 10 points!')
