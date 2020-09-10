def largest(arr, x=None):
    copy_arr = arr.copy()
    if x:
        copy_arr.remove(x)
    big = copy_arr[0]
    for num in copy_arr:
        if num > big:
            big = num
    return big, copy_arr


large = None
input_arr = [3, 2, 8, 5]
return_arr = input_arr.copy()
for i in range(2):
    large, return_arr = largest(return_arr, large)
print(large)
