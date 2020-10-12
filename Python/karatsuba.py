def mul(num1, num2):
    factor = len(str(num1))
    num2_len = len(str(num2))
    factor = num2_len if factor < num2_len else factor
    if factor == 1:
        return num1 * num2
    a = int(num1 / pow(10, factor / 2))
    b = int(num1 - pow(10, int(factor / 2)) * a)
    c = int(num2 / pow(10, factor / 2))
    d = int(num2 - pow(10, int(factor / 2)) * c)
    p = mul(a, c)
    q = mul(b, d)
    r = mul((a + b), (c + d)) - p - q
    return pow(10, factor) * p + pow(10, int(factor / 2)) * r + q


print(mul(1250, 3240))
