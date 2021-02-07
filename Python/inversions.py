def merge_sort(arr, start, end, swaps_input=0):
    if start == end:
        return arr, swaps_input
    mid = int((end - start + 1) / 2) + start
    arr, swaps_input = merge_sort(arr, start, mid - 1, swaps_input)
    arr, swaps_input = merge_sort(arr, mid, end, swaps_input)
    return merge(arr, start, mid, end, swaps_input)


def merge(arr, start, mid, end, swaps_input):
    i, j = start, mid
    temp = arr.copy()
    idx = start
    while idx <= end:
        j_swap = i == mid or (not j > end and arr[i] > arr[j])

        if j_swap:
            temp[idx] = arr[j]
            j = j + 1
            if not i == mid:
                swaps_input = swaps_input + mid - i
        else:
            temp[idx] = arr[i]
            i = i + 1

        idx = idx + 1
    return temp, swaps_input


input_arr = [8, 4, 2, 1]
sorted_arr, swaps = merge_sort(input_arr, 0, len(input_arr) - 1)
print(swaps)
