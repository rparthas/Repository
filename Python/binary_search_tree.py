class Node:
    # Implement a node of the binary search tree.
    # Constructor for a node with key and a given parent
    # parent can be None for a root node.
    def __init__(self, key, parent=None):
        self.key = key
        self.parent = parent
        self.left = None  # We will set left and right child to None
        self.right = None
        self.treemap = {}
        # Make sure that the parent's left/right pointer
        # will point to the newly created node.
        if parent != None:
            if key < parent.key:
                assert (parent.left == None), 'parent already has a left child -- unable to create node'
                parent.left = self
            else:
                assert key > parent.key, 'key is same as parent.key. We do not allow duplicate keys in a BST since it breaks some of the algorithms.'
                assert (parent.right == None), 'parent already has a right child -- unable to create node'
                parent.right = self

    # Utility function that keeps traversing left until it finds
    # the leftmost descendant
    def get_leftmost_descendant(self):
        if self.left != None:
            return self.left.get_leftmost_descendant()
        else:
            return self

    # TODO: Complete the search algorithm below
    # You can call search recursively on left or right child
    # as appropriate.
    # If search succeeds: return a tuple True and the node in the tree
    # with the key we are searching for.
    # Also note that if the search fails to find the key
    # you should return a tuple False and the node which would
    # be the parent if we were to insert the key subsequently.
    def search(self, key):
        if self.key == key:
            return True, self
        if key < self.key:
            if self.left is None:
                return False, self
            return self.left.search(key)
        else:
            if self.right is None:
                return False, self
            return self.right.search(key)

    # TODO: Complete the insert algorithm below
    # To insert first search for it and find out
    # the parent whose child the currently inserted key will be.
    # Create a new node with that key and insert.
    # return None if key already exists in the tree.
    # return the new node corresponding to the inserted key otherwise.
    def insert(self, key):
        found, parent = self.search(key)
        if found:
            return None
        new_node = Node(key, parent)
        if parent.key > key:
            parent.left = new_node
        else:
            parent.right = new_node
        return new_node

    # your code here

    # TODO: Complete algorithm to compute height of the tree
    # height of a node whose children are both None is defined
    # to be 1.
    # height of any other node is 1 + maximum of the height
    # of its children.
    # Return a number that is th eheight.
    def height(self):
        if self.left is None and self.right is None:
            return 1
        return max(self.left.height() if self.left is not None else 0,
                   self.right.height() if self.right is not None else 0) + 1

    # your code here

    # TODO: Write an algorithm to delete a key in the tree.
    # First, find the node in the tree with the key.
    # Recommend drawing pictures to visualize these cases below before
    # programming.
    # Case 1: both children of the node are None
    #   -- in this case, deletion is easy: simply find out if the node with key is its
    #      parent's left/right child and set the corr. child to None in the parent node.
    # Case 2: one of the child is None and the other is not.
    #   -- replace the node with its only child. In other words,
    #      modify the parent of the child to be the to be deleted node's parent.
    #      also change the parent's left/right child appropriately.
    # Case 3: both children of the parent are not None.
    #    -- first find its successor (go one step right and all the way to the left).
    #    -- function get_leftmost_descendant may be helpful here.
    #    -- replace the key of the node by its successor.
    #    -- delete the successor node.
    # return: no return value specified

    def delete(self, key):
        (found, node_to_delete) = self.search(key)
        assert (found == True), f"key to be deleted:{key}- does not exist in the tree"

        def set_child_to_parent(node_to_set):
            if key < node_to_delete.parent.key:
                node_to_delete.parent.left = node_to_set
            else:
                node_to_delete.parent.right = node_to_set

        if node_to_delete.left is None and node_to_delete.right is None:
            set_child_to_parent(None)

        if node_to_delete.left is None and node_to_delete.right is not None:
            set_child_to_parent(node_to_delete.right)

        if node_to_delete.left is not None and node_to_delete.right is None:
            set_child_to_parent(node_to_delete.left)

        if node_to_delete.left is not None and node_to_delete.right is not None:
            successor = node_to_delete.right.get_leftmost_descendant()
            node_to_delete.key = successor.key
            successor.delete(successor.key)

    def print(self):
        keys = []
        if self.left is not None:
            keys.append(self.left.key)
        else:
            keys.append("NIL")
        keys.append(self.key)
        if self.right is not None:
            keys.append(self.right.key)
        else:
            keys.append("NIL")

        print(keys)
        if self.left is not None:
            self.left.print()
        if self.right is not None:
            self.right.print()


t1 = Node(25, None)
t2 = Node(12, t1)
t3 = Node(18, t2)
t4 = Node(40, t1)

print('-- Testing basic node construction (originally provided code) -- ')
assert (t1.left == t2), 'test 1 failed'
assert (t2.parent == t1), 'test 2 failed'
assert (t2.right == t3), 'test 3 failed'
assert (t3.parent == t2), 'test 4 failed'
assert (t1.right == t4), 'test 5 failed'
assert (t4.left == None), 'test 6 failed'
assert (t4.right == None), 'test 7 failed'
# The tree should be :
#             25
#             /\
#         12     40
#         /\
#     None  18
#

print('-- Testing search -- ')
(b, found_node) = t1.search(18)
assert b and found_node.key == 18, 'test 8 failed'
(b, found_node) = t1.search(25)
assert b and found_node.key == 25, 'test 9 failed -- you should find the node with key 25 which is the root'
(b, found_node) = t1.search(26)
assert (not b), 'test 10 failed'
assert (
        found_node.key == 40), 'test 11 failed -- you should be returning the leaf node which would be the parent to the node you failed to find if it were to be inserted in the tree.'

print('-- Testing insert -- ')
ins_node = t1.insert(26)
assert ins_node.key == 26, ' test 12 failed '
assert ins_node.parent == t4, ' test 13 failed '
assert t4.left == ins_node, ' test 14 failed '

ins_node2 = t1.insert(33)
assert ins_node2.key == 33, 'test 15 failed'
assert ins_node2.parent == ins_node, 'test 16 failed'
assert ins_node.right == ins_node2, 'test 17 failed'

print('-- Testing height -- ')

assert t1.height() == 4, 'test 18 failed'
assert t4.height() == 3, 'test 19 failed'
assert t2.height() == 2, 'test 20 failed'

print('Success: 15 points.')

# Testing deletion
t1 = Node(16, None)
# insert the nodes in the list
lst = [18, 25, 10, 14, 8, 22, 17, 12]
for elt in lst:
    t1.insert(elt)
print("............")
# The tree should look like this
#               16
#            /     \
#          10      18
#        /  \     /  \
#       8   14   17  25
#          /         /
#         12        22


# Let us test the three deletion cases.
# case 1 let's delete node 8
# node 8 does not have left or right children.
t1.delete(8)  # should have both children nil.
(b8, n8) = t1.search(8)
assert not b8, 'Test A: deletion fails to delete node.'
(b, n) = t1.search(10)
assert (b), 'Test B failed: search does not work'
assert n.left == None, 'Test C failed: Node 8 was not properly deleted.'

# Let us test deleting the node 14 whose right child is none.
# n is still pointing to the node 10 after deleting 8.
# let us ensure that it's right child is 14
assert n.right != None, 'Test D failed: node 10 should have right child 14'
assert n.right.key == 14, 'Test E failed: node 10 should have right child 14'

# Let's delete node 14
t1.delete(14)
(b14, n14) = t1.search(14)
assert not b14, 'Test F: Deletion of node 14 failed -- it still exists in the tree.'
(b, n) = t1.search(10)
assert n.right != None, 'Test G failed: deletion of node 14 not handled correctly'
assert n.right.key == 12, f'Test H failed: deletion of node 14 not handled correctly: {n.right.key}'

# Let's delete node 18 in the tree.
# It should be replaced by 22.
t1.delete(18)
(b18, n18) = t1.search(18)
assert not b18, 'Test I: Deletion of node 18 failed'
assert t1.right.key == 22, ' Test J: Replacement of node with successor failed.'
assert t1.right.right.left == None, ' Test K: replacement of node with successor failed -- you did not delete the successor leaf properly?'

print('-- All tests passed: 15 points!--')

import random


# 1. make list of  numbers from 0 to n-1
# 2. randomly shuffle the list
# 3. insert the random list elements in order into a tree.
# 4. return the height of the resulting ree.
def run_single_experiment(n):
    numbers = list(range(n))
    random.shuffle(numbers)
    tree = Node(numbers[0], None)
    for number in numbers[1:]:
        tree.insert(number)
    return tree.height()


def run_multiple_trials(n, numTrials):
    lst_of_depths = [run_single_experiment(n) for j in range(numTrials)]
    return (sum(lst_of_depths) / len(lst_of_depths), lst_of_depths)
