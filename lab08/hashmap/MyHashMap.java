package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Summer Li
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int initialSize = 16;
    private double maxLoad = 0.75;
    private int size;
    private int bnum; // the number of buckets
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        bnum = initialSize;
        buckets = createTable(initialSize);
        size = 0;
    }

    public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        bnum = initialSize;
        buckets = createTable(initialSize);
        size = 0;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.initialSize = initialSize;
        this.maxLoad = maxLoad;
        bnum = initialSize;
        buckets = createTable(initialSize);
        size = 0;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = (Collection<Node>[]) new Collection[tableSize];
        for (int i = 0; i < tableSize; i ++) {
            table[i] = createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public void clear() {
        bnum = initialSize;
        buckets = createTable(initialSize);
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return (get(key) != null);
    }

    @Override
    public V get(K key) {
        int hc = key.hashCode();
        int index = Math.floorMod(hc, bnum);
        for (Node x : buckets[index]) {
            if (x.key.equals(key)) {
                return x.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        int hc = key.hashCode();
        int index = Math.floorMod(hc, bnum);
        Node n = createNode(key, value);
        if (!containsKey(key)) {
            buckets[index].add(n);
            size += 1;
        } else {
            for (Node x : buckets[index]) {
                if (x.key.equals(key)) {
                    x.value = value;
                }
            }
        }

        checkResize();
    }

    private void checkResize() {
        double load = (double) size / bnum;
        if (load >= maxLoad) {
            bnum *= 2;
            Collection<Node>[] resized = createTable(bnum);
            for (Collection<Node> bucket : buckets) {
                for (Node n : bucket) {
                    int index = Math.floorMod(n.key.hashCode(), bnum);
                    resized[index].add(n);
                }
            }
            buckets = resized;
        }
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
