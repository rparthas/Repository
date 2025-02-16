from max_heap import MaxHeap
from min_heap import MinHeap


class MedianMaintainingHeap:
    def __init__(self):
        self.hmin = MinHeap()
        self.hmax = MaxHeap()
        
    def satisfies_assertions(self):
        if self.hmin.size() == 0:
            assert self.hmax.size() == 0
            return 
        if self.hmax.size() == 0:
            assert self.hmin.size() == 1
            return 
        # 1. min heap min element >= max heap max element
        assert self.hmax.max_element() <= self.hmin.min_element(), f'Failed: Max element of max heap = {self.hmax.max_element()} > min element of min heap {self.hmin.min_element()}'
        # 2. size of max heap must be equal or one lessthan min heap.
        s_min = self.hmin.size()
        s_max = self.hmax.size()
        assert (s_min == s_max or s_max  == s_min -1 ), f'Heap sizes are unbalanced. Min heap size = {s_min} and Maxheap size = {s_max}'
    
    def __repr__(self):
        return 'Maxheap:' + str(self.hmax) + ' Minheap:'+str(self.hmin)
    
    def get_median(self):
        if self.hmin.size() == 0:
            assert self.hmax.size() == 0, 'Sizes are not balanced'
            assert False, 'Cannot ask for median from empty heaps'
        if self.hmax.size() == 0:
            assert self.hmin.size() == 1, 'Sizes are not balanced'
            return self.hmin.min_element()
        # your code here
        if self.hmax.size() == self.hmin.size():
            return (self.hmax.max_element() + self.hmin.min_element()) / 2
        else:   
            return self.hmin.min_element()
        
    
    # function: balance_heap_sizes
    # ensure that the size of hmax == size of hmin or size of hmax +1 == size of hmin
    # If the condition above does not hold, move the max element from max heap into the min heap or
    # vice versa as needed to balance the sizes.
    # This function could be called from insert/delete_median methods
    def balance_heap_sizes(self):
        # your code here
        if self.hmax.size() == self.hmin.size() or self.hmax.size() + 1 == self.hmin.size():
            return

        if self.hmax.size() > self.hmin.size():
            current_max = self.hmax.max_element()
            self.hmax.delete_max()
            self.hmin.insert(current_max)        
        else:
            current_min = self.hmin.min_element()
            self.hmin.delete_min()
            self.hmax.insert(current_min)

        
    
    def insert(self, elt):
        # Handle the case when either heap is empty
        if self.hmin.size() == 0:
            # min heap is empty -- directly insert into min heap
            self.hmin.insert(elt)
            return 
        if self.hmax.size() == 0:
            # max heap is empty -- this better happen only if min heap has size 1.
            assert self.hmin.size() == 1
            if elt > self.hmin.min_element():
                # Element needs to go into the min heap
                current_min = self.hmin.min_element()
                self.hmin.delete_min()
                self.hmin.insert(elt)
                self.hmax.insert(current_min)
                # done!
            else:
                # Element goes into the max heap -- just insert it there.
                self.hmax.insert(elt)
            return 
        # Now assume both heaps are non-empty
        # your code here
        if elt > self.hmin.min_element():
            self.hmin.insert(elt)
        else:
            self.hmax.insert(elt)
        self.balance_heap_sizes()
        
        
    def delete_median(self):
        self.hmax.delete_max()
        self.balance_heap_sizes()

m = MedianMaintainingHeap()
print('Inserting 1, 5, 2, 4, 18, -4, 7, 9')

m.insert(1)
print(m)
print(m.get_median())
m.satisfies_assertions()
assert m.get_median() == 1,  f'expected median = 1, your code returned {m.get_median()}'

m.insert(5)
print(m)
print(m.get_median())
m.satisfies_assertions()
assert m.get_median() == 3,  f'expected median = 3.0, your code returned {m.get_median()}'

m.insert(2)
print(m)
print(m.get_median())
m.satisfies_assertions()

assert m.get_median() == 2,  f'expected median = 2, your code returned {m.get_median()}'
m.insert(4)
print(m)
print(m.get_median())
m.satisfies_assertions()
assert m.get_median() == 3,  f'expected median = 3, your code returned {m.get_median()}'

m.insert(18)
print(m)
print(m.get_median())
m.satisfies_assertions()
assert m.get_median() == 4,  f'expected median = 4, your code returned {m.get_median()}'

m.insert(-4)
print(m)
print(m.get_median())
m.satisfies_assertions()
assert m.get_median() == 3,  f'expected median = 3, your code returned {m.get_median()}'

m.insert(7)
print(m)
print(m.get_median())
m.satisfies_assertions()
assert m.get_median() == 4, f'expected median = 4, your code returned {m.get_median()}'

m.insert(9)
print(m)
print(m.get_median())
m.satisfies_assertions()
assert m.get_median()== 4.5, f'expected median = 4.5, your code returned {m.get_median()}'

print('All tests passed: 15 points')
