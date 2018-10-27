using System.Collections.Generic;

namespace CollectionsFiles {
    public static class ListExtensions {
        public static System.Collections.Generic.Stack<T> ToStack<T> (this LinkedList<T> list) {
            var stack = new System.Collections.Generic.Stack<T> ();
            foreach (var item in list) {
                stack.Push (item);
            }
            return stack;
        }
    }
}