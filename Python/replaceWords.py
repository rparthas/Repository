class Trie:

    def __init__(self):
        self.isEndOfWord = False
        self.children = {}
        self.value = None

    def insert(self, word):
        node = self
        for char in word:
            if char not in node.children:
                node.children[char] = Trie()
                node.children[char].value = char
            node = node.children[char]
        node.isEndOfWord = True

    def match(self, word):
        node = self
        match_str = []
        for char in word:
            if char not in node.children:
                return None
            node = node.children[char]
            match_str.append(char)
            if node.isEndOfWord:
                return ''.join(match_str)

        return None


class Solution:
    def replaceWords(self, dictionary, sentence):
        root = Trie()
        for words in dictionary:
            root.insert(words)
        result = []
        for words in sentence.split(" "):
            res = root.match(words)
            result.append(res if res else words)
        return " ".join(result)


print(Solution().replaceWords(["a", "b", "c"], "aadsfasf absbs bbab cadsfafs"))
