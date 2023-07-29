prices = [7, 1, 5, 3, 6, 4]
left, right = 0, 1
max_profit = 0

while right < len(prices):
    if prices[left] > prices[right]:
        left += 1
    else:
        temp = prices[right] - prices[left]
        max_profit = max(temp, max_profit)
        right += 1

print(max_profit)
