// Owen Pitchford
// ANY SOURCES OR COLLABORATION YOU USED HERE

import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T> {
  private Node head;
  private int size;

  /**
   * Function to check that index is valid
   * @param index int for index that user inputs
   */
  private void validIndexCheck(int index) {
    if(index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Invalid Index: " + index);
    }
  }

  @Override
  public T get(int index) throws IndexOutOfBoundsException {
    validIndexCheck(index);
    return get(index, head);
  }

  /**
   * get() helper method
   * @param index
   * @param curr
   * @return
   */
  private T get(int index, Node curr) {
    if(index == 0) { return curr.data; }
    else { return get(index - 1, curr.next); }
  }

  @Override
  public void set(int index, T data) throws IndexOutOfBoundsException {
    validIndexCheck(index);
    set(index, data, head);
  }

  /**
   * Set helper method
   * @param index
   * @param data
   * @param curr
   */
  private void set(int index, T data, Node curr) {
    if(index == 0) { curr.data = data; } 
    else { set(index - 1, data, curr.next); }
  }

  @Override
  public void add(int index, T data) throws IndexOutOfBoundsException {
    if(index < 0 || index > size) { 
      throw new IndexOutOfBoundsException("Invalid Index: " + index);}

    if(head == null) { //empty list
      head = new Node(data, null);
      size++;
      return;
    }

    if(index == 0) { //add to front
      Node newNode = new Node(data, head);
      head = newNode;
      size++;
      return;
    }
    add(index, data, head, null);
  }

  private void add(int index, T data, Node curr, Node prev) {
    if(index == 0) {  //base case
      Node newNode = new Node(data, curr);
      prev.next = newNode;
      size++;
    } else if(curr.next == null) {//add to back
      Node newNode = new Node(data, null);
      curr.next = newNode;
      size++;
    } else { 
      add(index - 1, data, curr.next, curr);
    }
  }

  @Override
  public void remove(int index) throws IndexOutOfBoundsException {
    validIndexCheck(index);
    if (index == 0) { //remove first element
      head = head.next;
      size--;
      return;
    }
    remove(index, head);
  }

  private void remove(int index, Node curr) {
    if(curr == null) { return; }
    if(index == 1) { //middle index
      curr.next = curr.next.next;
      size--;
    } else if(curr.next == null) {//remove the last element
      curr = null;
    } else {
      remove(index - 1, curr.next);
    }
  }

  @Override
  public int size() { return size; }

  /** Removes ALL elements matching the given one using .equals().
   *
   * @param element The element that should be removed
   */
  public void removeAll(T element) {
    removeAll(0, element, head);
  }

  /**
   * removeAll() helper function
   * @param index index of list
   * @param element key to delete
   * @param curr Node to keep track of current node
   */
  private void removeAll(int index, T element, Node curr) {
    if(curr == null ) { return; } //made it to the end of the list
    else if(curr.data.equals(element)) { //find node that contains the key
      remove(index);
      removeAll(0, element, head); //start from beginning
    } else {
      removeAll(index + 1, element, curr.next);
    }
  }

  /** Gets the 2nd-to-last element.
   *
   * @return The data in the second-to-last node in the list (if any)
   * @throws NoSuchElementException if the list size is less than 2
   */
  public T penultimate() throws NoSuchElementException {
    if(size < 2) {throw new NoSuchElementException("List size is less than 2"); }
    return get(size-2);
  }

  //Node class
  private class Node {
    public T data;
    public Node next;

    Node(T data, Node next) {
      this.data = data;
      this.next = next;
    }
  }

  /**
   * Simple print to visualize linked list
   */
  public void print() {
    Node temp = head;
    while(temp != null) {
      System.out.print(temp.data + " --> ");
      temp=temp.next;
    }
    System.out.println("null");
    System.out.println();
  }

  /**
   * Main used for testing
   */
  public static void main(String[] args) {
    LinkedList<Integer> list = new LinkedList<>();
    list.add(0,1);
    list.print();
    list.set(0, 3);
    list.print();
    /*list.add(0,0);
    list.add(1, 1);
    list.add(2, 2);
    list.add(3,3);
    list.add(2, 500);
    list.add(5, 2);
    list.add(0, 2);
    list.print();
    //list.remove(0);
    //list.print();
    //list.remove(3);
    //list.print();
    //list.remove(list.size() - 1 );
    //list.add(2, 2);
    list.removeAll(2);
    list.print();
    //list.remove(0);
    //list.print();
    //list.remove(list.size()-1);
    //list.print();
    System.out.println(list.get(0));
    System.out.println(list.get(1));
    System.out.println(list.get(2));
    //System.out.println(list.get(3));
    //System.out.println(list.penultimate());
    System.out.println(list.size());*/

  }
}
