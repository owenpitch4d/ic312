import java.util.NoSuchElementException;

//Owen Pitchford 

public class DoubleTree implements AddMax {
  public Node root = null;
  public int balanceFactor, rbf;

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

  public int rootHeight() {
    return root.height;
  }

  private int height(Node curr) { 
    if(curr == null) {
      return -1;
    } else {
      return curr.height; 
    }
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
    balance();
  }

  private void balance() {
    
    balanceFactor = balanceFactor(root);
    
    if(balanceFactor == -2) { //tree is right heavy
      if(balanceFactor(root.right) == 1) {
        root.right = rightRotate(root.right);
        //updateHeight(root.right);
      }
      root = leftRotate(root);
      //updateHeight(root);
    }

    if (balanceFactor == 2) {
      if(balanceFactor(root.left) == -1) {
        root.left = leftRotate(root.left);
        //updateHeight(root.left);
      }
      root = rightRotate(root);
      //updateHeight(root);
    }
    
  }

  private Node add(Node curr, double key) {
    if(curr == null) { return new Node(key); }
    if(key < curr.key) {
      curr.left = add(curr.left, key);
    } else if(key > curr.key) {
      curr.right = add(curr.right, key);
    }

    updateHeight(curr);
    
    return curr;
  }

  private Node leftRotate(Node oldRoot) {
    Node newRoot = oldRoot.right;
    Node middle = newRoot.left;
    newRoot.left = oldRoot;
    oldRoot.right = middle;

    updateHeight(newRoot.left);
    updateHeight(newRoot);
    return newRoot;
  }

  private Node leftRightRotate(Node oldRoot) {
    oldRoot.left = leftRotate(oldRoot.left);
    return rightRotate(oldRoot);
  }

  private Node rightRotate(Node oldRoot) {
    Node newRoot = oldRoot.left;
    Node middle = newRoot.right;
    newRoot.right = oldRoot;
    oldRoot.left = middle;

    updateHeight(newRoot.right);
    updateHeight(newRoot);
    return newRoot;
  }

  private Node rightLeftRotate(Node oldRoot) {
    oldRoot.right = rightRotate(oldRoot.right);
    return leftRotate(oldRoot);
  }


  @Override
  public double removeMax() throws NoSuchElementException {
    return 0;
  }

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



    
    /*tree.add(18);
    System.out.println(tree.rootHeight());
    tree.add(12);
    tree.add(11);
    //tree.add(14);*/
  }
}
