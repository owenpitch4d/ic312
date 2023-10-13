//Owen Pitchford 
import java.util.NoSuchElementException;

public class DoubleTree implements AddMax {
  private Node root = null;

  private class Node {
    public double key;
    public int height;
    public Node left;
    public Node right;

    public Node(double key) {
      this.key = key;
      this.left = null;
      this.right = null;
      this.height = 0;
    }
  }

  private int height(Node curr) { 
    if(curr == null) {
      return -1;
    }
    return curr.height; 
  }

  private void updateHeight(Node curr) {
    curr.height = 1 + max(height(curr.left), height(curr.right));
  }

  private int max(int x, int y) {
    return (x < y) ? y : x;
  }

  private int balanceFactor(Node curr) {
    return height(curr.left) - height(curr.right);
  }
  
  @Override
  public void add(double x) {
    root = add(root, x);
  }

  private Node balance(Node curr) {
    int balanceFactor = balanceFactor(curr);
    
    if(balanceFactor == -2) { //tree is right heavy
      return rightHeavyBalance(curr);
    }
    if (balanceFactor == 2) { //tree is left heavy
      return leftHeavyBalance(curr);
    }
    return curr;
  }

  private Node leftHeavyBalance(Node curr) {
    if(balanceFactor(curr.left) == -1) { //check if double rotation needed
      curr.left = leftRotate(curr.left);
    }
    return rightRotate(curr);
  }

  private Node rightHeavyBalance(Node curr) {
    if(balanceFactor(curr.right) == 1) { //check if double rotation needed
      curr.right = rightRotate(curr.right);
    }
    return leftRotate(curr);

  }

  private Node add(Node curr, double key) {
    if(curr == null) { return new Node(key); }
    
    if(key < curr.key) { //traverse through tree
      curr.left = add(curr.left, key);
    } else if(key > curr.key) {
      curr.right = add(curr.right, key);
    } else {
      return curr;
    }

    updateHeight(curr); //update height of each node
    return balance(curr); //balance the tree
  }

  private Node leftRotate(Node oldRoot) {
    Node newRoot = oldRoot.right;
    Node middle = newRoot.left;
    newRoot.left = oldRoot;
    oldRoot.right = middle;

    //update height of left subtree and root. right subtree will stay the same
    updateHeight(newRoot.left);
    updateHeight(newRoot);
    return newRoot;
  }

  private Node rightRotate(Node oldRoot) {
    Node newRoot = oldRoot.left;
    Node middle = newRoot.right;
    newRoot.right = oldRoot;
    oldRoot.left = middle;

    //update height of right subtree and root. left subtree will stay the same
    updateHeight(newRoot.right);
    updateHeight(newRoot);
    return newRoot;
  }

  @Override
  public double removeMax() throws NoSuchElementException {
    if(root == null) {
      throw new NoSuchElementException();
    }

    Node temp = root;
    //traverse right subtrees to get max value to return
    while(temp.right != null) { temp = temp.right; }
    double max = temp.key;
    root = removeMax(root); //update tree    
    return max;
  }

  private Node removeMax(Node curr) {
    //traverse to right most node
    if(curr.right == null) {
      return curr.left;
    }
    curr.right = removeMax(curr.right);

    //update height and rebalance tree
    updateHeight(curr);
    int balanceFactor = balanceFactor(curr);
    if (balanceFactor == 2) {
      return leftHeavyBalance(curr);
    }

    return curr;
  }

  //Main used for testing
  public static void main(String[] args) {
    DoubleTree tree = new DoubleTree();
    tree.add(10);
    tree.add(15);
    tree.add(18);
    tree.add(19);
    tree.add(17);
    tree.add(16);
    System.out.println(tree.root.height);
    System.out.println(tree.root.key);
    System.out.print(tree.root.left.key + " ");
    System.out.println(tree.root.left.height);
    System.out.print(tree.root.left.left.key + " ");
    System.out.println(tree.root.left.left.height);
    System.out.print(tree.root.left.right.key + " ");
    System.out.println(tree.root.left.right.height);
    System.out.print(tree.root.right.key + " ");
    System.out.println(tree.root.right.height);
    System.out.println(tree.root.right.right.key);
  }
}
