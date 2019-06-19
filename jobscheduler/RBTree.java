package jobscheduler;

import java.util.*;

public class RBTree<Key extends Comparable<Key>, Val> {

    private static final boolean Red = true;
    private static final boolean Black = false;

    // This is the root node of the jobscheduler.jobscheduler.RBTree
    private Node root;

    // node object for the jobscheduler.jobscheduler.RBTree
    private class Node {
        private Key k;
        private Val val;
        private Node left, right;
        private boolean col;
        private int size;

        public Node(Key key, Val val, boolean colour, int size) {
            this.k = key;
            this.val = val;
            this.col = colour;
            this.size = size;
        }
    }

    public RBTree() {
    }

    // check for Red in case of a specific node
    private boolean checkRed(Node nd) {
        if (nd == null) return false;
        return nd.col == Red;
    }

    private int size(Node nd) {
        if (nd == null)
            return 0;
        return nd.size;
    }

    /*Check if the jobscheduler.jobscheduler.RBTree is empty*/

    public boolean checkEmpty() {
        return root == null;
    }

    /*Search operation in jobscheduler.jobscheduler.RBTree*/

    public Val search(Key k) {
        if (k == null) throw new IllegalArgumentException("null argument");
        return search(root, k);
    }

    // returns the value of the given key of a node in subtree rooted at a specified node; it returns null if there is no such node

    private Val search(Node nd, Key k) {
        while (nd != null) {
            int cmp = k.compareTo(nd.k);
            if      (cmp < 0) nd = nd.left;
            else if (cmp > 0) nd = nd.right;
            else              return nd.val;
        }
        return null;
    }

    public boolean contains(Key k) {
        return search(k) != null;
    }

    /*Insert Operation for jobscheduler.jobscheduler.RBTree*/
    public void insert(Key k, Val val) {
        if (k == null) throw new IllegalArgumentException("null");
        if (val == null) {
            del(k);
            return;
        }
        root = insert(root, k, val);
        root.col = Black;
    }

    // insert node with key and value in the subtree of the jobscheduler.jobscheduler.RBTree rooted at a specific node nd
    private Node insert(Node nd, Key k, Val val) {
        if (nd == null) return new Node(k, val, Red, 1);
        int cmp = k.compareTo(nd.k);
        if      (cmp < 0) nd.left  = insert(nd.left,  k, val);
        else if (cmp > 0) nd.right = insert(nd.right, k, val);
        else              nd.val   = val;

        if (checkRed(nd.right) && !checkRed(nd.left))      nd = rotLeft(nd);
        if (checkRed(nd.left)  &&  checkRed(nd.left.left)) nd = rotRight(nd);
        if (checkRed(nd.left)  &&  checkRed(nd.right))     colorChange(nd);
        nd.size = size(nd.left) + size(nd.right) + 1;
        return nd;
    }

    /*Delete Operation for the jobscheduler.jobscheduler.RBTree*/

    // deletes the min key-value pair of nd

    private Node deleteMin(Node nd) {
        if (nd.left == null)
            return null;

        if (!checkRed(nd.left) && !checkRed(nd.left.left))
            nd = moveRlft(nd);

        nd.left = deleteMin(nd.left);
        return bal(nd);
    }

    /* Deletion of a specified node with its key and value from the jobscheduler.jobscheduler.RBTree*/

    public void del(Key k) {
        if (k == null) throw new IllegalArgumentException("argument to del() is null");
        if (!contains(k)) return;

        // if both children of root are black, set root to red
        if (!checkRed(root.left) && !checkRed(root.right))
            root.col = Red;
        root = del(root, k);
        if (!checkEmpty()) root.col = Black;

    }

    // deletes the key-value pair with the given key rooted at nd

    private Node del(Node nd, Key k) {

        if (k.compareTo(nd.k) < 0)  {
            if (!checkRed(nd.left) && !checkRed(nd.left.left))
                nd = moveRlft(nd);
            nd.left = del(nd.left, k);
        }
        else {
            if (checkRed(nd.left))
                nd = rotRight(nd);
            if (k.compareTo(nd.k) == 0 && (nd.right == null))
                return null;
            if (!checkRed(nd.right) && !checkRed(nd.right.left))
                nd = moveRRight(nd);
            if (k.compareTo(nd.k) == 0) {
                Node x = min(nd.right);
                nd.k = x.k;
                nd.val = x.val;
                nd.right = deleteMin(nd.right);
            }
            else nd.right = del(nd.right, k);
        }
        return bal(nd);
    }

    // rotate a link leaning towards left to right

    private Node rotRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.col = x.right.col;
        x.right.col = Red;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // make a right-leaning link lean to the left

    private Node rotLeft(Node h) {
        // assert (h != null) && checkRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.col = x.left.col;
        x.left.col = Red;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // It flips the colors of a node along with its children

    private void colorChange(Node h) {
        h.col = !h.col;
        h.left.col = !h.left.col;
        h.right.col = !h.right.col;
    }

    private Node moveRlft(Node h) {
        colorChange(h);
        if (checkRed(h.right.left)) {
            h.right = rotRight(h.right);
            h = rotLeft(h);
            colorChange(h);
        }
        return h;
    }

    private Node moveRRight(Node h) {
        colorChange(h);
        if (checkRed(h.left.left)) {
            h = rotRight(h);
            colorChange(h);
        }
        return h;
    }

    // balance the jobscheduler.jobscheduler.RBTree

    private Node bal(Node h) {

        if (checkRed(h.right))                      h = rotLeft(h);
        if (checkRed(h.left) && checkRed(h.left.left)) h = rotRight(h);
        if (checkRed(h.left) && checkRed(h.right))     colorChange(h);

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }


    // find the smallest key in subtree rooted at node nd; returns null if there is no such key

    private Node min(Node nd) {
        if (nd.left == null) return nd;
        else                return min(nd.left);
    }

    /*Returns the largest key from the jobscheduler.jobscheduler.RBTree less than or equal to key*/

    public Key prevLarNd(Key k) {
        if (k == null) throw new IllegalArgumentException("argument null");
        if (checkEmpty()) return null;
        Node x = prevLarNd(root, k);
        if (x == null) return null;
        else           return x.k;
    }

    private Node prevLarNd(Node x, Key k) {
        if (x == null) return null;
        int cmp = k.compareTo(x.k);
        if (cmp <= 0)  return prevLarNd(x.left, k);
        Node t = prevLarNd(x.right, k);
        if (t != null) return t;
        else           return x;
    }

    /*Returns the smallest key from the jobscheduler.jobscheduler.RBTree greater than or equal to key*/

    public Key nxtSmlNd(Key k) {
        if (k == null) throw new IllegalArgumentException("argument to nxtSmlNd() is null");
        if (checkEmpty()) return null; // throw new NoSuchElementException("calls nxtSmlNd() with empty symbol table");
        Node x = nxtSmlNd(root, k);
        if (x == null) return null;
        else           return x.k;
    }

    // the smallest k in the subtree rooted at x greater than or equal to the given k

    private Node nxtSmlNd(Node nd, Key k) {
        if (nd == null) return null;
        int cmp = k.compareTo(nd.k);
        if (cmp >= 0)  return nxtSmlNd(nd.right, k);
        Node t = nxtSmlNd(nd.left, k);
        if (t != null) return t;
        else           return nd;
    }


    /* In case of a given key range it returns all the key in that range for jobscheduler.jobscheduler.RBTree*/

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument null");
        if (hi == null) throw new IllegalArgumentException("second argument null");

        Queue<Key> queue = new LinkedList<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    // This is to add the keys between low and high in the subtree rooted at nd to the queue

    private void keys(Node nd, Queue<Key> queue, Key low, Key high) {
        if (nd == null) return;
        int cmplow = low.compareTo(nd.k);
        int cmphigh = high.compareTo(nd.k);
        if (cmplow < 0) keys(nd.left, queue, low, high);
        if (cmplow <= 0 && cmphigh >= 0) queue.add(nd.k);
        if (cmphigh > 0) keys(nd.right, queue, low, high);
    }
}