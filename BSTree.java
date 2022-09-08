public class Node {
  int key;
  Node left;
  Node right;
  Node p;

  public Node(int key) {
    this.key = key;
    left = null;
    right = null;
    p = null;
  }

  public Node(int key, Node left, Node right, Node p) {
    this.key = key;
    this.left = left;
    this.right = right;
    this.p = p;
  }
}


public class BinarySearchTree {

  Node root;

  public BinarySearchTree() {
    this.root = null;
  }

  public BinarySearchTree(Node root) {
    this.root = root;
  }

  public Node findMin(Node x) {
    if(x == null) { return null; }
    if(x.left != null) { return findMin(x.left); }
    return x;
  }

  public Node findMax(Node x) {
    if(x == null) { return null; }
    if(x.right != null) { return findMin(x.right); }
    return x;
  }

  public int minKey() {
    if(root == null) { throw new IllegalStateException("root is null"); }
    Node n = findMin(root);
    return n.key;
  }

  public int maxKey() {
    if(root == null) { throw new IllegalStateException("root is null"); }
    Node n = findMax(root);
    return n.key;
  }

  public Node search(int key) {
    Node y = root;
    boolean found = false;
    while(!found && y != null) {
      if(key < y.key) { y = y.left; } 
      else if(key > y.key) { y = y.right; }
      else { found = true; }
    }
    return y;
  }

  public Node searchRec(Node x, int key) {
    if(x == null) { return null; }
    if(x.key > key) { return searchRec(x.left, key); }
    if(x.key < key) { return searchRec(x.right, key); }
    return x;
  }

  public Node successor(Node x) {
    if(x != null) {
      if(x.right != null) {
        Node y = x.right;
        while(y.left != null) {
          y = y.left;
        }
        return y;
      } else {
        Node y = x;
        Node z = y.p;
        while(z != null && y.equals(z.right)) {
          y = z;
          z = z.p;
        }
        return z;
      }
    }
    return null;
  }

  public Node predecessor(Node x) {
    if(x != null) {
      if(x.left != null) {
        Node y = x.left;
        while(y.right != null) {
          y = y.right;
        }
        return y;
      } else {
        Node y = x;
        Node z = x.p;
        while(z != null && y.equals(z.left)) {
          y = z;
          z = z.p;
        }
        return z;
      }
    }
    return null;
  }

  public void insert(Node x) {
    if(root == null) {
      root = x;
    } else {
      Node tmp = root;
      Node y = null;
      while(tmp != null) {
        y = tmp;
        if(tmp.key >= x.key) {
          tmp = tmp.left;
        } else {
          tmp = tmp.right;
        }
      }
      x.p = y;
      if(y.key >= x.key) {
        y.left = x;
      } else {
        y.right = x;
      }
    }
  }

  public void delete(Node x) {
    if(x != null) {
      Node y = null;
      if(x.left == null) {
        y = x.right;
      } else if(x.right == null) {
        y = x.left;
      } else {
        y = successor(x);
        y.left = x.left;
        if(!x.equals(y.p)) {
          y.p.left = y.right;
          y.right = x.right;
        }
      }
      if(y != null) { y.p = x.p; }
      if(x.p != null) {
        if(x.p.left != null && x.p.left.equals(x)) { x.p.left = y; }
        if(x.p.right != null && x.p.right.equals(x)) { x.p.right = y; }
      } else {
        root = y;
      }
    }
  }
}

