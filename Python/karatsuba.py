def mul(num1, num2):
    num1_len = len(str(num1))
    num2_len = len(str(num2))
    factor = max(num1_len, num2_len)

    if factor <= 4:
        return num1 * num2

    divisor = pow(10, int(factor / 2))
    a = int(num1 / divisor)
    b = int(num1 - divisor * a)
    c = int(num2 / divisor)
    d = int(num2 - divisor * c)
    p = mul(a, c)
    q = mul(b, d)
    r = mul((a + b), (c + d)) - p - q

    multiplier = factor if factor % 2 == 0 else factor - 1
    return (pow(10, multiplier) * p) + (divisor * r) + q


n1 = 3141592653589793238462643383279502884197169399375105820974944592
n2 = 2718281828459045235360287471352662497757247093699959574966967627
print(mul(n1, n2))
