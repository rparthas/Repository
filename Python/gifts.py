import sys
import os

loop = int(input())
for i in range(loop):
    gifts = int(input())
    avl = int(input())
    input_arr = []
    numbers = input()
    for num in numbers.split(" "):
        input_arr.append(int(num))
    input_arr.sort()
    res = 0
    for k in range(gifts):
        res = res + input_arr[k]
    print(res)

'''
2
3
8
50 70 30 100 80 20 150 10
4
6
10 20 32 412 500 11
'''