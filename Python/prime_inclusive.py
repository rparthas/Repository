def get_diff(left, right):
    start, end = 0, 0
    for i in range(left, right + 1):
        if is_prime(i):
            start = i
            break
    if start == 0:
        return -1
    for i in range(right, start - 1, -1):
        if is_prime(i):
            end = i
            break
    if end == 0:
        return 0
    return end - start


def is_prime(num):
    if num == 2 or num == 3:
        return True
    for i in range(2, num // 2 + 1):
        if num % i == 0:
            return False
    return True


def main():
    test_cases = int(input())
    for i in range(test_cases):
        ranges = input().split(" ")
        print(get_diff(int(ranges[0]), int(ranges[1])))


main()
"""
5
5 5
2 7
8 10
10 20
4 5

"""
