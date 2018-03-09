package col106.a3.ta;

import col106.a3.DuplicateBTree;
import col106.a3.IllegalKeyException;

import java.util.*;

public class TA_BTree<Key extends Comparable<Key>, Value> implements DuplicateBTree<Key,Value> {
    int n;
    int size;
    BTreeNode root, nil;

    public TA_BTree(int n) {
        this.n = n;
        size = 0;
        nil = new BTreeNode(null);
        nil.parent = nil;
        root = new BTreeNode(nil);
        root.children.add(nil);
    }
    
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return size;
        //return root.count();
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public List<Value> search(Key key) throws IllegalKeyException {
        LinkedList<Value> answer = new LinkedList<>();
        root.fill(answer, key);
        return answer;
    }

    @Override
    public void insert(Key key, Value value) {
        BTreeNode node = root;
        while (true) {
            if (node.leaf()) {
                node.add(key, value, nil);
                if (node.size() == n)
                    fix_overflow(node);
                return;
            } else node = node.subTreeFor(key);
        }
    }

    private void fix_overflow(BTreeNode b) {
        while (true) {
            List<Key> firstHalfKeys = b.keys.subList(0, b.keys.size() / 2);
            List<Value> firstHalfValues = b.values.subList(0, b.keys.size() / 2);
            List<BTreeNode> firstHalfChildren = b.children.subList(0, b.keys.size() / 2);

            BTreeNode newNode = new BTreeNode(b.parent);
            newNode.keys.addAll(firstHalfKeys);
            newNode.values.addAll(firstHalfValues);
            newNode.children.addAll(firstHalfChildren);
            newNode.children.forEach(e -> e.parent = newNode);

            firstHalfKeys.clear();
            firstHalfValues.clear();
            firstHalfChildren.clear();

            b.children.getFirst().parent = newNode;
            newNode.children.addLast(b.children.removeFirst());
            Key k = b.keys.removeFirst();
            Value v = b.values.removeFirst();
            if (b == root) {
                root = new BTreeNode(nil);
                root.children.add(newNode);
                root.add(k, v, b);
                b.parent = root;
                newNode.parent = root;
                break;
            } else {
                int index = b.parent.children.indexOf(b);
                b.parent.children.set(index, newNode);
                newNode.parent = b.parent;
                b.parent.add(k, v, b);
                b = b.parent;
                if (b.size() < n)
                    break;
            }
        }
        int c2=size();
    }

    @Override
    public void delete(Key key) throws IllegalKeyException {

    }

    @Override
    public String toString() {
        if (root.size() == 0)
            return "[]";
        StringBuilder builder = new StringBuilder();
        root.asString(builder);
        return builder.toString();
    }

    class BTreeNode {
        BTreeNode parent;
        LinkedList<Key> keys;
        LinkedList<Value> values;
        LinkedList<BTreeNode> children;

        BTreeNode(BTreeNode p) {
            parent = p;
            keys = new LinkedList<>();
            values = new LinkedList<>();
            children = new LinkedList<>();
        }

        int size() {
            return keys.size();
        }

        boolean leaf() {
            return children.getFirst() == nil;
        }

        int add(Key k, Value v, BTreeNode node) {
            ListIterator<Key> kit = keys.listIterator();
            ListIterator<Value> vit = values.listIterator();
            ListIterator<BTreeNode> tit = children.listIterator();
            while (true) {
                if (!kit.hasNext()) {
                    kit.add(k);
                    vit.add(v);
                    tit.next(); //skip the last subtree
                    tit.add(node);
                    return kit.previousIndex();
                }
                Key x = kit.next();
                tit.next();
                if (k.compareTo(x) <= 0) {
                    kit.previous();
                    kit.add(k);
                    tit.add(node);
                    vit.add(v);
                    return kit.previousIndex();
                }
                vit.next();
            }
        }

        void asString(StringBuilder result) {
            ListIterator<Key> kit = keys.listIterator();
            ListIterator<Value> vit = values.listIterator();
            ListIterator<BTreeNode> tit = children.listIterator();
            result.append('[');
            boolean first = true;
            while (tit.hasNext() || kit.hasNext()) {
                if (tit.hasNext()) {
                    BTreeNode node = tit.next();
                    if (node != nil) {
                        node.asString(result);
                        result.append(", ");
                    }
                }
                if (kit.hasNext())
                    result.append(kit.next()).append('=').append(vit.next()).append(", ");
            }
            result.replace(result.length() - 2, result.length(), "]");
        }

        BTreeNode subTreeFor(Key k) {
            ListIterator<Key> kit = keys.listIterator();
            ListIterator<BTreeNode> tit = children.listIterator();
            while (kit.hasNext()) {
                if (k.compareTo(kit.next()) <= 0)
                    return tit.next();
                tit.next();
            }
            return tit.next();
        }

        @Override
        public String toString() {
            if (this == nil)
                return "nil";
            StringBuilder builder = new StringBuilder();
            asString(builder);
            return builder.toString();
        }

        void fill(List<Value> answer, Key k) {
            ListIterator<Key> kit = keys.listIterator();
            ListIterator<Value> vit = values.listIterator();
            ListIterator<BTreeNode> tit = children.listIterator();
            while (kit.hasNext()) {
                Key x = kit.next();
                Value v = vit.next();
                BTreeNode node = tit.next();
                if (k.compareTo(x) < 0) {
                    node.fill(answer, k);
                    return;
                }
                if (k.equals(x)) {
                    node.fill(answer, k);
                    answer.add(v);
                }
            }
            if (tit.hasNext())
                tit.next().fill(answer, k);
        }
    }
}