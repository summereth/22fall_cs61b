package deque;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {

    private class LLNode {
        public T item;
        public LLNode next;
        public LLNode prev;

        public LLNode(T i, LLNode p, LLNode n) {
            item = i;
            next = n;
            prev = p;
        }
        public LLNode() {
        }
    }

    private LLNode sentinel;
    private int size;

    /** create empty LinkedListDeque */
    public LinkedListDeque() {
        sentinel = new LLNode(); //sentinel = new LLNode(null, sentinel, sentinel); is wrong. It'll make sentinel.next = null
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    public class LinkedListIterator implements Iterator<T> {
        private int index;

        public LinkedListIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return !(get(index) == null);
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            else {
                T result = get(index);
                index += 1;
                return result;
            }
        }
    }
    @Override
    public void addFirst(T item){
        LLNode toAdd = new LLNode(item, sentinel, sentinel.next);
        sentinel.next = toAdd;
        toAdd.next.prev = toAdd;
        size += 1;
    }
    @Override
    public void addLast(T item) {
        LLNode toAdd = new LLNode(item, sentinel.prev, sentinel);
        sentinel.prev = toAdd;
        toAdd.prev.next = toAdd;
        size += 1;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public void printDeque(){
        for (int i = 0; i < size() - 1; i ++) {
            System.out.print(get(i) + " ");
        }
        System.out.println(get(size() - 1));
    }
    @Override
    public T removeFirst(){
        LLNode first = sentinel;
        if (size() > 0) {
            first = first.next;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
        }
        return first.item;
    }
    @Override
    public T removeLast(){
        LLNode last = sentinel;
        if (size() > 0) {
            last = last.prev;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
        }
        return last.item;
    }
    @Override
    public T get(int index){
        LLNode currNode = sentinel.next;
        T getItem = null;
        for(int i = 0; i < size(); i ++) {
            if (i == index) {
                getItem = currNode.item;
                break;
            }
            currNode = currNode.next;
        }
        return getItem;
    }
    @Override
    public boolean equals(Object o){
        if ( (o instanceof Deque<?>) &&
                size() == ((Deque<?>) o).size()) {
            for (int i = 0; i < size(); i ++) {
                if (!get(i).equals(((Deque<?>) o).get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public T getRecursive(int index){
        if (index >= size()) {
            return null;
        }
        LLNode fNode = sentinel.next;
        return getRecursiveHelper(index, fNode);
    }

    private T getRecursiveHelper(int index, LLNode node) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(index - 1, node.next);
    }
}
