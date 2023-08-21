/** A slightly-simplified version of Java's {@link java.util.List} interface.
 *
 * Represents an indexed, growable list of generic objects.
 */
public interface List<T> {
  /** Return the element at the index in the list.
   *
   * If the list currently has k elements, the only valid indices are 0 through
   * k-1, regardless of its capacity; any other index should be an
   * IndexOutOfBoundsException.
   *
   * @param index the index to get
   * @return the value of the element at the index
  **/
  T get(int index) throws IndexOutOfBoundsException;

  /** Set the value of the element at an existing, legal index to something new.
   *
   * If the list currently has k elements, the only valid indices are 0 through
   * k-1, regardless of its capacity; any other index should result in an
   * IndexOutOfBoundsException.
   *
   * @param index the index to alter
   * @param data the value to assign at that index
  */
  void set(int index, T data) throws IndexOutOfBoundsException;

  /** Insert a new element into the list and move later elements up by one index.
   *
   * If a list consists of [ A B C ], and add(1,D) is called, it should newly
   * be [ A D B C ].  If there are k elements in the list, legal indices are
   * 0-k.  All others should result in IndexOutOfBoundsException.
   *
   * Also, performing an add when it would result in outgrowing the capacity
   * should result in an IllegalStateException.
   *
   * @param index the index where the new element will go
   * @param data the value to insert at that index
   */
  void add(int index, T data) throws IndexOutOfBoundsException, IllegalStateException;


  /** Remove the element at the given index and move later elements down by one.
   *
   * If a list consists of [ A B C D ], and remove(1) is called, this should
   * return B, and result in [ A C D ].  If you try and remove something at a
   * bad index, it should throw a IndexOutOfBoundsException.
   *
   * @param index the index to remove from
   */
  void remove(int index) throws IndexOutOfBoundsException;

  /** Return the number of elements in the list.
   *
   * Note this is NOT the same as the capacity of the underlying array storage.
   *
   * @return the number of elements currently in the list
   */
  int size();
}
