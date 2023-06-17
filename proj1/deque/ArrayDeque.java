package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.lang.Math.*;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst = 4;
    private int nextLast = 4;
    private int FIRST = 4;
    private int LAST = 4;
    private double usage_rate;
    @Override
    public void addFirst(T item) {
        if (size() == items.length) {
            upgradeSize();
        }
        items[nextFirst] = item;
        FIRST = nextFirst;
        size += 1;
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
        //Deal with situation where addFirst has the same effect as addLast
        if (size() == 1) {
            if (nextLast == items.length - 1) {
                nextLast = 0;
            } else {
                nextLast += 1;
            }
        }
    }
    @Override
    public void addLast(T item) {
        if (size() == items.length) {
            upgradeSize();
        }
        items[nextLast] = item;
        LAST = nextLast;
        size += 1;
        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
        //Deal with situation where addLast has the same effect as addFirst
        if (size() == 1) {
            if (nextFirst == 0) {
                nextFirst = items.length - 1;
            } else {
                nextFirst -= 1;
            }
        }
    }
    private void upgradeSize() {
        T[] biggerItems = (T[]) new Object[items.length * 2];
        copyArray(items, FIRST, LAST, biggerItems);
        items = biggerItems;
        nextFirst = items.length - 1;
        nextLast = size();
        FIRST = 0;
        LAST = nextLast - 1;
    }
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        for (int i = 0; i < size() - 1; i ++) {
            System.out.print(get(i) + " ");
        }
        System.out.println(get(size() - 1));
    }
    @Override
    public T removeFirst() {
        T toRemove = items[FIRST];
        if (size() > 0) {
            items[FIRST] = null;
            size -= 1;
            nextFirst = FIRST;
            if (FIRST == items.length - 1) {
                FIRST = 0;
            } else {
                FIRST += 1;
            }
            checkAndDowngradeSize();
        }
        return toRemove;
    }

    private void checkAndDowngradeSize() {
        usage_rate = (double) size() / items.length;
        if (usage_rate < 0.25 & usage_rate > 0) {
            T[] smallerItems = (T[]) new Object[(int) items.length / 4];
            copyArray(items, FIRST, LAST, smallerItems);
            items = smallerItems;
            nextFirst = items.length-1;
            nextLast = size();
            FIRST = 0;
            LAST = nextLast - 1;
        }
    }

    private void copyArray(T[] source, int start, int end, T[] dest) {
        if (end < start) {
            for (int i = start; i < source.length; i ++) {
                dest[i - start] = source[i];
            }
            int l = source.length - start;
            for (int i = 0; i <= end; i ++) {
                dest[i + l] = source[i];
            }
        } else {
            for (int i = start; i <= end; i ++) {
                dest[i - start] = source[i];
            }
        }
    }
    @Override
    public T removeLast() {
        T toRemove = items[LAST];
        if (size() > 0) {
            items[LAST] = null;
            size -= 1;
            nextLast = LAST;
            if (LAST == 0) {
                LAST = items.length - 1;
            } else {
                LAST -= 1;
            }
            checkAndDowngradeSize();
        }
        return toRemove;
    }
    @Override
    public T get(int index) {
        if (index >= size()) {
            return null;
        }
        int n = FIRST + index;
        if (n >= items.length) {
            n -= items.length;
        }
        return items[n];
    }
    @Override
    public boolean equals(Object o) {
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
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    public class ArrayDequeIterator implements Iterator<T> {
        int index;
        public ArrayDequeIterator() {
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
}
