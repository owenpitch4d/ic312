//Owen Pitchford
//255076

import java.util.Iterator;
/** A List implementation with fixed, bounded capacity.
 *
 * When an instance of this class is constructed, the chosen capacity
 * represents the maximum size the list can ever grow to.
 */
public class BoundedList<T> implements List<T>, Iterable<T>{
  private T[] elements;
  private int capacity;
  private int size;

  /** Create a new BoundedList with the given maximum capacity.
   */
  public BoundedList(int capacity) {
    /*This is what I added to test the starting size but it failed the
    submit tests*/
    /*if(capacity < 1) { //make sure list startng size is greater than 0 
      throw new IllegalArgumentException("Invalid Starting List Size"); 
    }*/ 

    @SuppressWarnings("unchecked")
    T[] elements = (T[]) new Object[capacity];

    this.elements = elements;
    this.capacity = capacity;
    this.size = 0;
  }

  @Override
  public T get(int index) throws IndexOutOfBoundsException {
    if(index >= 0 && index < size) { //check for valid index
      return elements[index];
    } else {
      throw new IndexOutOfBoundsException("Invalid index " + index );
    }
  }

  @Override
  public void set(int index, T data) throws IndexOutOfBoundsException {
    if(index >= 0 && index < size) { //check for valid index
      elements[index] = data;
    } else {
      throw new IndexOutOfBoundsException("Invalid index " + index);
    }
  }

  @Override
  public void add(int index, T data) throws IndexOutOfBoundsException, IllegalStateException {
    if(index < 0 || index > size) { throw new IndexOutOfBoundsException(); }
    
    if(size < capacity) {
      //shift elements to the right
      for(int i = size; i > index; i--) {
        elements[i] = elements[i-1];
      }
      elements[index] = data;
      size++;
    } else {
      throw new IllegalStateException();
    }
  }

  @Override
  public void remove(int index) throws IndexOutOfBoundsException {
    if(index >= 0 && index < size) {
      //shift elements to left
      for(int i = index; i < size - 1; i++ ) { 
        elements[i] = elements[i + 1]; 
      }
      size--;
    } else {
      throw new IndexOutOfBoundsException("Invalid index " + index);
    }
  }

  @Override
  public int size() { return size; }

  @Override
  // this produces a string like "[ 1 2 3 4 ]"
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[ ");
    for (int i = 0; i < size(); ++i) {
      sb.append(get(i).toString());
      sb.append(' ');
    }
    sb.append(']');
    return sb.toString();
  }

  public BoundedListIter iterator() { return new BoundedListIter(); }

  /**
   * Iterator class
   */
  public class BoundedListIter implements Iterator<T> {
    private int currentIndex;

    public BoundedListIter() { currentIndex = 0; }
    
    @Override
    public boolean hasNext() { return currentIndex < size; }

    @Override
    public T next() {
      if(!hasNext()) {
        throw new IllegalStateException("No more elements in the list!");
      }

      return get(currentIndex++);
    }

    @Override
    public void remove() { throw new UnsupportedOperationException(); }

  }

  /**
   * Main method used for testing
   * @param args
   */
  public static void main(String[] args) {
    List<Integer> lst = new BoundedList<Integer>(5); //list with a capacity of 10
    lst.add(0, 1);   // [ 1 ]
    System.out.println(lst);
    lst.add(0, 2);   // [ 2 1 ]
    System.out.println(lst);
    lst.add(1, 3);   // [ 2 3 1 ]
    System.out.println(lst);
    //lst.add(4, 4);   // IndexOutOfBoundsException -- adding more than +1 the current size
   // lst.add(5, 5);   // IndexOutOfBoundsException -- outside the bounds of the fixedList at size 5
    //lst.add(-1, -1); // IndexOutOfBoundsException -- negative index
    lst.add(3, 4);   // [ 2 3 1 4 ]
    System.out.println(lst);
    //System.out.println(lst.size());
    lst.add(0, 5);   // [ 5 2 3 1 4 ]
    System.out.println(lst);
    lst.remove(2);
    System.out.println(lst);
  }
}
