package bstmap;

import bstmap.Map61B;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B {
    private int size;
    private BSTNode root;
    private BSTNode emptyNode = new BSTNode();
    public BSTMap() {
        size = 0;
        root = emptyNode;
    }
    @Override
    public void clear() {
        root = emptyNode;
        size = 0;
    }
    @Override
    public boolean containsKey(Object key) {
        return (root.findNode((K)key) instanceof BSTNode);
    }

    @Override
    public Object get(Object key) {
        if (root.findNode((K)key) == null) {
            return null;
        }
        return root.findNode((K)key).value();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(Object key, Object value) {
        root = insert(root, (K) key, (V) value);
    }
    private BSTNode insert(BSTNode node, K key, V value) {
        if (node == emptyNode) {
            size += 1;
            return new BSTNode(key, value);
        }
        if (node.key().compareTo(key) > 0) {
            node.left = insert(node.left(), key, value);
        } else if (node.key().compareTo(key) < 0) {
            node.right = insert(node.right(), key, value);
        } else if (node.key().compareTo(key) == 0) {
            node.setValue(value);
        }
        return node;
    }

    @Override
    public Set keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }
    public void printInOrder() {
        root.printNode();
    }
    private class BSTNode {
        private K key;
        private V value;
        private BSTNode parent;
        private BSTNode left;
        private BSTNode right;
        private BSTNode() {
        }
        private BSTNode(K k, V v) {
            key = k;
            value = v;
            left = emptyNode;
            right = emptyNode;
        }
        private BSTNode(K k, V v, BSTNode p) {
            key = k;
            value = v;
            parent = p;
            left = emptyNode;
            right = emptyNode;
        }
        private K key() {
            return key;
        }
        private V value() {
            return value;
        }
        private BSTNode parent() {
            return parent;
        }
        private BSTNode left() {
            return left;
        }
        private BSTNode right() {
            return right;
        }
        private BSTNode findNode(K targetKey) {
            if (this == emptyNode) {
                return null;
            }
            if (key.compareTo(targetKey) == 0 ) {
                return this;
            }
            if (key.compareTo(targetKey) > 0 && left != emptyNode) {
                return left.findNode(targetKey);
            }
            if (key.compareTo(targetKey) < 0 && right != emptyNode) {
                return right.findNode(targetKey);
            }
            return null;
        }
        private void setValue(V newValue) {
            value = newValue;
        }
        private void printNode() {
            System.out.println("key: " + key + "; value :" + value);
            if (left != emptyNode) {
                System.out.print("    ");
                left.printNode();
            }
            if (right != emptyNode) {
                System.out.print("    ");
                right.printNode();
            }
        }
    }
}
