/** A List implementation with fixed, bounded capacity.
 *
 * When an instance of this class is constructed, the chosen capacity
 * represents the maximum size the list can ever grow to.
 */
public class BoundedList<T> implements List<T> {
  private T[] elements;
  private int capacity;
  private int size;
  // TODO you might want another private field...

  /** Create a new BoundedList with the given maximum capacity.
   */
  public BoundedList(int capacity) {
    @SuppressWarnings("unchecked")
    T[] elements = (T[]) new Object[capacity];
    this.elements = elements;
    this.capacity = capacity;
    this.size = 0;
  }

  @Override
  public T get(int index) throws IndexOutOfBoundsException {
    //throw new UnsupportedOperationException("DELETE THIS!");
    if(index >= 0 && index < capacity - 1) {
      return elements[index];
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  @Override
  public void set(int index, T data) throws IndexOutOfBoundsException {
    //throw new UnsupportedOperationException("DELETE THIS!");
    if(index >= 0 && index < capacity - 1) {
      elements[index] = data;
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  @Override
  public void add(int index, T data) throws IndexOutOfBoundsException, IllegalStateException {
    if(index < 0 || index > size) { throw new IndexOutOfBoundsException(); }
    
    if(size < capacity) {
      for(int i = size; i > index; i--) {
        elements[i] = elements[i-1];
      }
      elements[index] = data;
      size++;
    } else {
      throw new IllegalStateException();
    }
    //throw new UnsupportedOperationException("DELETE THIS!");
  }

  @Override
  public void remove(int index) throws IndexOutOfBoundsException {
    if(index >= 0 && index <= size() - 1) {
      for(int i = index; i < size(); i++ ) {
        elements[i] = elements[i + 1]; //last iteration moves null char into final element pos
      }
      //elements[index] = data;
    } else {
      throw new IndexOutOfBoundsException();
    }
    throw new UnsupportedOperationException("DELETE THIS!");
  }

  @Override
  public int size() {
    /*int cnt = 1;
    for(int i = 0; i < capacity; i++) {
      if(elements[i] != null) { cnt++; }
    }

    return cnt;*/
    return size;
  }
    //throw new UnsupportedOperationException("DELETE THIS!");

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

  public static void main(String[] args) {
    List<Integer> lst = new BoundedList<Integer>(5); //list with a capacity of 10
    lst.add(0, 1);   // [ 1 ]
    System.out.println(lst);
    lst.add(0, 2);   // [ 2 1 ]
    System.out.println(lst);
    lst.add(1, 3);   // [ 2 3 1 ]
    System.out.println(lst);
    //lst.add(4, 4);   // IndexOutOfBoundsException -- adding more than +1 the current size
    //lst.add(5, 5);   // IndexOutOfBoundsException -- outside the bounds of the fixedList at size 5
    //lst.add(-1, -1); // IndexOutOfBoundsException -- negative index
    lst.add(3, 4);   // [ 2 3 1 4 ]
    System.out.println(lst);
    System.out.println(lst.size());
    //lst.add(0, 5);   // [ 5 2 3 1 4 ]
    System.out.println(lst);
    lst.add(0, 6);   // IllegalStateException -- list is full
  }
}
