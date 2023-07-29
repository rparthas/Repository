class node:
    def __init__(self, data, left=None, right=None):
        self.data = data
        self.left = left
        self.right = right


def checkBST(root):
    return check(root, -1, 100000)


def check(root, min, max):
    if root is None:
        return True
    if root.data <= min or root.data >= max:
        return False
    return check(root.left, min, root.data) and check(root.right, root.data, max)


node1 = node(3, None, None)
node3 = node(5, None, None)
node5 = node(6, None, None)
node7 = node(7, None, None)
node2 = node(2, node1, node3)
node6 = node(4, node5, node7)
root_node = node(1, node2, node6)

print(checkBST(root_node))
