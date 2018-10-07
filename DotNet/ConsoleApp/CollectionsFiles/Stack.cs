using System.Collections.Generic;

namespace CollectionsFiles
{
    class Stack<T>
    {
        private List<T> _items = new List<T>();
        public void push(T item) => _items.Add(item);
       
        public T pop()
        {
            T item = _items[_items.Count - 1];
            _items.RemoveAt(_items.Count - 1);
            return item;
        }
    }
}
