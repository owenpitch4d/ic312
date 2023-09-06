// Owen Pitchford
// ANY SOURCES OR COLLABORATION YOU USED HERE

import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T> {
  // TODO your private inner classes and fields...
  private Node head;
  private int size;

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

  private T get(int index, Node curr) {
    if(index == 0) { return curr.data; }
    else { return get(index - 1, curr.next); }

    /*
     *if(index > 0) { get(index-1, curr.next); }
      return curr.data; 
     */
  }

  @Override
  public void set(int index, T data) throws IndexOutOfBoundsException {
    validIndexCheck(index);
    set(index, data, head);
  }

  private void set(int index, T data, Node curr) {
    if(index == 0) { curr.data = data; } 
    else { set(index - 1, data, curr.next); }
  }

  @Override
  public void add(int index, T data) throws IndexOutOfBoundsException {
    if(index < 0 || index > size) { 
      throw new IndexOutOfBoundsException("Invalid Index: " + index);}

    if(head == null) {
      head = new Node(data, null);
      size++;
      return;
    } else if(index == size) {
      head.next = new Node(data, null);
      size++;
    }

    add(index, data, head);
  }

  private void add(int index, T data, Node curr) {
    if(index == 0) {
      Node newNode = new Node(data, null);
      curr = newNode.next;
      size++;
      //return newNode;
    } else if(index == 1) {
      //curr.next = newNode;
    } 
    else {
      add(index - 1, data, curr.next);
      //return curr;
    }
  }

  @Override
  public void remove(int index) throws IndexOutOfBoundsException {
    validIndexCheck(index);
    remove(index, head);
  }

  private void remove(int index, Node curr) {
    if(index == 1) {
      curr.next = curr.next.next;
    } else {
      remove(index - 1, curr.next);
    }
  }

  @Override
  public int size() {
    return size;
  }

  /** Removes ALL elements matching the given one using .equals().
   *
   * @param element The element that should be removed
   */
  public void removeAll(T element) {
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

  private class Node {
    public T data;
    public Node next;

    Node(T data, Node next) {
      this.data = data;
      this.next = next;
    }
  }

  public static void main(String[] args) {
    LinkedList<Integer> list = new LinkedList<>();
    list.add(0,5);
    list.add(1, 9);
    //list.add(1, 2);
    System.out.println(list.get(0));
    //System.out.println(list.get(1));
    System.out.println(list.size());

  }
}
