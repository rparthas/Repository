# First let us complete a minheap data structure.
# Please complete missing parts below.

class MinHeap:
    def __init__(self):
        self.H = [None]
 
    def size(self):
        return len(self.H)-1
    
    def __repr__(self):
        return str(self.H[1:])
        
    def satisfies_assertions(self):
        for i in range(2, len(self.H)):
            assert self.H[i] >= self.H[i//2],  f'Min heap property fails at position {i//2}, parent elt: {self.H[i//2]}, child elt: {self.H[i]}'
    
    def min_element(self):
        return self.H[1]

    ## bubble_up function at index
    ## WARNING: this function has been cut and paste for the next problem as well 
    def bubble_up(self, index):
        assert index >= 1
        if index == 1: 
            return 
        parent_index = index // 2
        if self.H[parent_index] < self.H[index]:
            return 
        else:
            self.H[parent_index], self.H[index] = self.H[index], self.H[parent_index]
            self.bubble_up(parent_index)
    
    ## bubble_down function at index
    ## WARNING: this function has been cut and paste for the next problem as well 
    def bubble_down(self, index):
        assert index >= 1 and index < len(self.H)
        lchild_index = 2 * index
        rchild_index = 2 * index + 1
        # set up the value of left child to the element at that index if valid, or else make it +Infinity
        lchild_value = self.H[lchild_index] if lchild_index < len(self.H) else float('inf')
        # set up the value of right child to the element at that index if valid, or else make it +Infinity
        rchild_value = self.H[rchild_index] if rchild_index < len(self.H) else float('inf')
        # If the value at the index is lessthan or equal to the minimum of two children, then nothing else to do
        if self.H[index] <= min(lchild_value, rchild_value):
            return 
        # Otherwise, find the index and value of the smaller of the two children.
        # A useful python trick is to compare 
        min_child_value, min_child_index = min ((lchild_value, lchild_index), (rchild_value, rchild_index))
        # Swap the current index with the least of its two children
        self.H[index], self.H[min_child_index] = self.H[min_child_index], self.H[index]
        # Bubble down on the minimum child index
        self.bubble_down(min_child_index)
        
        
    # Function: heap_insert
    # Insert elt into heap
    # Use bubble_up/bubble_down function
    def insert(self, elt):
        # your code here
        self.H.append(elt)
        self.bubble_up(self.size())
       
        
    # Function: heap_delete_min
    # delete the smallest element in the heap. Use bubble_up/bubble_down
    def delete_min(self):
        # your code here
        if self.size() == 1:
            self.H.pop()
            return
        self.H[1], self.H[-1] = self.H[-1], self.H[1]
        self.H.pop()
        self.bubble_down(1)
        
# h = MinHeap()
# print('Inserting: 5, 2, 4, -1 and 7 in that order.')
# h.insert(5)
# print(f'\t Heap = {h}')
# assert(h.min_element() == 5)
# h.insert(2)
# print(f'\t Heap = {h}')
# assert(h.min_element() == 2)
# h.insert(4)
# print(f'\t Heap = {h}')
# assert(h.min_element() == 2)
# h.insert(-1)
# print(f'\t Heap = {h}')
# assert(h.min_element() == -1)
# h.insert(7)
# print(f'\t Heap = {h}')
# assert(h.min_element() == -1)
# h.satisfies_assertions()

# print('Deleting minimum element')
# h.delete_min()
# print(f'\t Heap = {h}')
# assert(h.min_element() == 2)
# h.delete_min()
# print(f'\t Heap = {h}')
# assert(h.min_element() == 4)
# h.delete_min()
# print(f'\t Heap = {h}')
# assert(h.min_element() == 5)
# h.delete_min()
# print(f'\t Heap = {h}')
# assert(h.min_element() == 7)
# # Test delete_max on heap of size 1, should result in empty heap.
# h.delete_min()
# print(f'\t Heap = {h}')
# print('All tests passed: 10 points!')