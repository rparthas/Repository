class MaxHeap:
    def __init__(self):
        self.H = [None]
        
    def size(self):
        return len(self.H)-1
    
    def __repr__(self):
        return str(self.H[1:])
        
    def satisfies_assertions(self):
        for i in range(2, len(self.H)):
            assert self.H[i] <= self.H[i//2],  f'Maxheap property fails at position {i//2}, parent elt: {self.H[i//2]}, child elt: {self.H[i]}'
    
    def max_element(self):
        return self.H[1]
    
    def bubble_up(self, index):
        # your code here
        if index == 1:
            return
        parent_index = index // 2
        if self.H[parent_index] > self.H[index]:
            return
        else:
            self.H[parent_index], self.H[index] = self.H[index], self.H[parent_index]
            self.bubble_up(parent_index)   
            
    
    def bubble_down(self, index):
        # your code here
        if index * 2 > self.size():
            return
        lchild = self.H[index * 2] if index * 2  <= self.size() else float('-inf')
        rchild = self.H[index * 2 + 1] if index * 2 + 1 <= self.size() else float('-inf')
        if self.H[index] > max(lchild, rchild):
            return
        else:
            max_child_index = index * 2 if lchild > rchild else index * 2 + 1
            self.H[index], self.H[max_child_index] = self.H[max_child_index], self.H[index]
            self.bubble_down(max_child_index)
        
    
    # Function: insert
    # Insert elt into minheap
    # Use bubble_up/bubble_down function
    def insert(self, elt):
        # your code here
        self.H.append(elt)
        self.bubble_up(self.size())
        
    # Function: delete_max
    # delete the largest element in the heap. Use bubble_up/bubble_down
    def delete_max(self):
        # your code here
        self.H[1], self.H[-1] = self.H[-1], self.H[1]
        self.H.pop()
        self.bubble_down(1)
        

# h = MaxHeap()
# print('Inserting: 5, 2, 4, -1 and 7 in that order.')
# h.insert(5)
# print(f'\t Heap = {h}')
# assert(h.max_element() == 5)
# h.insert(2)
# print(f'\t Heap = {h}')
# assert(h.max_element() == 5)
# h.insert(4)
# print(f'\t Heap = {h}')
# assert(h.max_element() == 5)
# h.insert(-1)
# print(f'\t Heap = {h}')
# assert(h.max_element() == 5)
# h.insert(7)
# print(f'\t Heap = {h}')
# assert(h.max_element() == 7)
# h.satisfies_assertions()

# print('Deleting maximum element')
# h.delete_max()
# print(f'\t Heap = {h}')
# assert(h.max_element() == 5)
# h.delete_max()
# print(f'\t Heap = {h}')
# assert(h.max_element() == 4)
# h.delete_max()
# print(f'\t Heap = {h}')
# assert(h.max_element() == 2)
# h.delete_max()
# print(f'\t Heap = {h}')
# assert(h.max_element() == -1)
# # Test delete_max on heap of size 1, should result in empty heap.
# h.delete_max()
# print(f'\t Heap = {h}')
# print('All tests passed: 5 points!')
