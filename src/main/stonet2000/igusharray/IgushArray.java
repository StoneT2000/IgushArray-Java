/**
 * MIT License
 *
 * Copyright (c) 2020 StoneT2000 (Stone Tao) email <stonezt2019@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package stonet2000.igusharray;

import java.io.Serializable;
import java.util.*;

public class IgushArray<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, Serializable {

  public ArrayList<FixedDeque<E>> data; // data is stored as a list of lists (array of arrays).
  private int capacity; // total capacity of the IgushArray
  private int size; // the current size of IgushArray, not the same as capacity
  private int deqCapacity; // capacity of each ArrayDeque in the list
  private int listCapacity; // capacity of the list containing references to the ArrayDeques
  private int lastDeqCapacity; // capacity of the final deq
  /*
   * To ensure O(1) access time, each list in the data list is implemented with ArrayList, not ArrayDeque.
   */

  /**
   * Constructs an empty IgushArray with initial capacity of 10
   */
  public IgushArray() {
    this(10);
  }

  /**
   * Constructs an empty IgushArray with the specified initial capacity.
   *
   * @param initialCapacity
   * @throws IllegalArgumentException if the specified initial capacity
   *                                  is negative
   */
  public IgushArray(int initialCapacity) {
    capacity = initialCapacity;
    if (capacity < 0)
      throw new IllegalArgumentException("Illegal Capacity: " + capacity);

    deqCapacity = (int) Math.pow(capacity, 0.5);
    lastDeqCapacity = capacity % deqCapacity;
    listCapacity = (int) Math.ceil((double) capacity / deqCapacity);

    /*
    System.out.println("New Igush Array: \nCapacity: " + capacity +
            " | listCapacity: " + listCapacity +
            " | deqCapacity: " + deqCapacity +
            " | lastDeqCapacity: " + lastDeqCapacity);

    */
    data = new ArrayList<>(listCapacity);
    for (int i = 0; i < listCapacity - 1; i++) {
      data.add(new FixedDeque<E>(deqCapacity));
    }
    if (lastDeqCapacity != 0) {
      data.add(new FixedDeque<E>(lastDeqCapacity));
    } else {
      data.add(new FixedDeque<E>(deqCapacity));
    }
    size = 0;

  }

  /**
   * Constructs a IgushArray containing the elements of the specified
   * collection, in the order they are returned by the collection's iterator
   * The capacity is by default set equal to the collection size
   *
   * @param c the collection whose elements are to be placed into this list
   * @throws NullPointerException if the specified collection is null
   */
  public IgushArray(Collection<? extends E> c) {
    this(c.size());
    Iterator<?> itr = c.iterator();
    while (itr.hasNext()) {
      add((E) itr.next());
    }
  }

  /**
   * Constructs a IgushArray of the specified capacity containing the elements of the specified
   * collection, in the order they are returned by the collection's iterator
   *
   * @param c the collection whose elements are to be placed into this list
   * @throws NullPointerException     if the specified collection is null
   * @throws IllegalArgumentException if the specified capacity is not enough to contain the
   *                                  collection passed
   */
  public IgushArray(Collection<? extends E> c, int initialCapacity) {
    this(initialCapacity);
    Iterator<?> itr = c.iterator();
    if (capacity < size)
      throw new IllegalArgumentException("Capacity of " + capacity + "is not enough to contain the collection");

    while (itr.hasNext()) {
      add((E) itr.next());
    }
  }

  /**
   * FIXME
   * Trims the capacity of this <tt>IgushArray</tt> instance to be the
   * list's current size.  An application can use this operation to minimize
   * the storage of an <tt>IgushArray</tt> instance.
   */
  public void trimToSize() {

  }

  /**
   * Returns the current allocated capacity of the IgushArray
   *
   * @return
   */
  public int capacity() {
    return capacity;
  }

  /**
   * Increases the capacity of this IgushArray instance, if necessary, to ensure that it can hold
   * at least the number of elements specified by the minimum capacity argument. Akin to ArrayList's same method
   *
   * @param minCapacity the size to expand to
   */
  public void ensureCapacity(int minCapacity) {
    if (minCapacity < capacity) {
      throw new IllegalArgumentException("New capacity: " + minCapacity + " is not larger than the old capacity of " + capacity);
    }
    capacity = minCapacity;

    // reset the deqCapacities to maintain optimal insert and remove times
    // quite costly

    int newDeqCapacity = (int) Math.pow(capacity, 0.5);
    int newLastDeqCapacity = capacity % newDeqCapacity;
    int newListCapacity = (int) Math.ceil((double) capacity / newDeqCapacity);

    // FIXME might be a redundant check, see how ArrayList ensureCapacity is implemented
    if (newListCapacity != listCapacity) {
      // list capacity has changed, so we need to update data

      if (data.get(listCapacity - 1).size() != newDeqCapacity) {
        // new list capacity means old last FixedDeque needs to be of size deqCapacity

        // TODO: Check if ensure capacity does the above comparison for us already
        // TODO: This might not be needed given that we update all deques to newDeqCapacity later possibly
        data.get(listCapacity - 1).ensureCapacity(newDeqCapacity);
      }

      data.ensureCapacity(newListCapacity);

      // add new FixedDeques that are full (not partial capacity)
      for (int i =  listCapacity; i < newListCapacity - 1; i++) {
        data.add(new FixedDeque<>(newDeqCapacity));
      }

      lastDeqCapacity = newLastDeqCapacity;

      if (lastDeqCapacity != 0) {
        data.add(new FixedDeque<E>(newLastDeqCapacity));
      } else {
        data.add(new FixedDeque<E>(newDeqCapacity));
      }

      listCapacity = newListCapacity;
    }


    // deq capacity changed, which implies list capacity must change
    if (newDeqCapacity != deqCapacity) {



      // go from 0 to listCapacity instead of listCapacity - 1 as list capacity must change, and the final fixedDeque
      // should now have normal deq size.
      for (int i = 0; i < listCapacity; i++) {
        // FIXME BUG: bug here, ensureing capaciting on something liek begin > end... not working
        data.get(i).ensureCapacity(newDeqCapacity);
      }
      // now we have to shift all data down to fill in holes, fairly costly to do

      // store index of the last partially filled fixed deque
      int indexOfLastPartialFixedDeque = (int) Math.ceil(((double) size()) / newDeqCapacity);

      // now update the deqCapacity
      deqCapacity = newDeqCapacity;

      int j = 0;

      while (j < indexOfLastPartialFixedDeque) {
        int higherIndice = j + 1;

        // keep filling FixedDeque at indice j until it is full. Pop from FixedDeque higherIndice until it is empty
        // before moving on to next FixedDeque to pop from.
        while (!data.get(j).isFull()) {

          // data.get(j) fixedDeque has space left, fill it up with array data from next index.
          if (data.get(higherIndice).isEmpty()) {
            higherIndice++;
            if (higherIndice > listCapacity - 1) {
              break;
            }
          }
          else {
            E popped = data.get(higherIndice).popFront();
            data.get(j).add(popped);
          }
        }
        j++;
      }
    }

  }

  /**
   * Returns the size of the IgushArray, which is the number of elements stored in this collection
   *
   * @return the number of elements in this IgushArray
   */
  @Override
  public int size() {
    return this.size;
  }

  /**
   * Returns whether the IgushArray is empty or not
   *
   * @return true if the IgushArray is empty
   */
  @Override
  public boolean isEmpty() {
    if (!data.isEmpty()) {
      return data.get(0).isEmpty();
    }
    return true;
  }

  /**
   * Returns whether or not an instance of o is in this IgushArray
   *
   * @param o element whose presence in this IgushArray is to be tested
   * @return true if the element is present in the IgushArray
   */
  @Override
  public boolean contains(Object o) {
    return indexOf(o) >= 0;
  }

  /**
   * Returns the index of the first occurrence of the specified element in
   * the IgushArray instance.
   *
   * @param o element whose index in this IgushArray is to be found
   * @return index of the element found or -1 if there is no such index
   */
  @Override
  public int indexOf(Object o) {
    ListIterator<E> itr = listIterator();
    if (o == null) {
      while (itr.hasNext()) {
        if (itr.next() == null)
          return itr.previousIndex();
      }
    } else {
      while (itr.hasNext()) {
        if (o.equals(itr.next()))
          return itr.previousIndex();
      }
    }
    return -1;
  }

  @Override
  public int lastIndexOf(Object o) {
    ListIterator<E> itr = listIterator(size());
    if (o == null) {
      while (itr.hasPrevious()) {
        if (itr.previous() == null)
          return itr.nextIndex();
      }
    } else {
      while (itr.hasPrevious()) {
        if (o.equals(itr.previous()))
          return itr.nextIndex();
      }
    }
    return -1;
  }

  /**
   * Returns a shallow copy of this IgushArray instance.  (The
   * elements themselves are not copied.) FIXME
   *
   * @return a clone of this IgushArray instance
   */
  public Object clone() {
    try {
      IgushArray<?> v = (IgushArray<?>) super.clone();
      return v;
    } catch (CloneNotSupportedException e) {
      // shouldn't happen
      throw new InternalError(e);
    }
  }

  /**
   * Return an Iterator object
   *
   * @return
   */
  @Override
  public Iterator<E> iterator() {
    return listIterator();
  }

  //FIXME ADD DOCUMENTATION
  @Override
  public Object[] toArray() {
    Object[] a = new Object[size];
    ListIterator<E> itr = listIterator();
    while (itr.hasNext()) {
      a[itr.nextIndex()] = itr.next();
    }
    return a;
  }

  /*FIXME ADD DOCUMENTATION. PROBABLY INCORRECT IMPLEMENTATION
   */
  @Override
  public <T> T[] toArray(T[] a) {
    T[] arr = (T[]) new Object[size()];

    ListIterator<E> itr = listIterator();
    while (itr.hasNext()) {
      arr[itr.nextIndex()] = (T) itr.next();
    }

    return arr;
  }

  private void rangeCheck(int index) {
    // Attempting to access an index outside of the allocated memory
    if (index >= size)
      throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
  }

  private void rangeCheckForAdd(int index) {
    if (index > size() || index < 0) {
      throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
  }

  private String outOfBoundsMsg(int index) {
    return "Index: " + index + ", Size: " + size + ", Capacity: " + capacity;
  }

  /**
   * Gets the element stored in the specified index of this IgushArray
   *
   * @param index
   * @return
   */
  @Override
  public E get(int index) {
    rangeCheck(index);

    int listIndex = (int) (index / deqCapacity);
    int deqIndex = index % deqCapacity;
    return data.get(listIndex).get(deqIndex);
  }

  /**
   * Replaces the element at the specified position in this IgushArray with
   * specified element
   *
   * @param index   index of the element to replace
   * @param element element to be stored at the specified position
   * @return the element previously at the specified position
   */
  @Override
  public E set(int index, E element) {
    int listIndex = (int) (index / deqCapacity);
    int deqIndex = index % deqCapacity;

    return data.get(listIndex).set(deqIndex, element);
  }

  /**
   * Adds an element to the end of the IgushArray. The IgushArray will automatically allocate more memory
   * for the array if there isn't enough capacity. By default... it expands by a factor of FIXME
   *
   * @param e The element to add
   * @return
   */
  @Override
  //FIXME will also automatically reallocate memory if size reaches capacity
  public boolean add(E e) {
    if (size() >= capacity) {
      ensureCapacity((int) (capacity * 1.5));
    }
    int listIndex = (int) (size() / deqCapacity);
    data.get(listIndex).add(e);
    size++;
    return true;
  }

  /**
   * Adds (Inserts) element to the specified position into the IgushArray. If capacity is exceeded, memory is auto reallocated FIXME
   *
   * @param index   position in the IgushArray to add the element
   * @param element the element to add to the specified position
   */
  @Override
  public void add(int index, E element) {
    rangeCheckForAdd(index);
    if (size() >= capacity) {
      ensureCapacity((int) (capacity * 1.5));
    }
    int listIndex = index / deqCapacity;
    int deqIndex = index % deqCapacity;
    FixedDeque<E> deque = data.get(listIndex);
    if (!deque.fixedAdd(deqIndex, element)) {
      // if fail to add, then deque must be full
      //O(n^1/2) insertion process into N^1/2 capacity deque
      // remove end of the deque to give space for element and avoid expanding deque size
      E removedElement = deque.remove(deque.size() - 1);
      deque.fixedAdd(deqIndex, element);
      shiftUp(listIndex + 1, removedElement);

    }
    size++;
  }

  /**
   * Removes the element stored in the specified position in the IgushArray
   *
   * @param index of the element to remove
   * @return the element that was removed
   */
  @Override
  public E remove(int index) {
    rangeCheck(index);
    int listIndex = index / deqCapacity;
    int deqIndex = index % deqCapacity;
    FixedDeque<E> deque = data.get(listIndex);
    E removedElement = deque.remove(deqIndex);
    // only shift down if listIndex isn't final listIndex
    shiftDown(listIndex + 1);
    size--;
    return removedElement;
  }

  /**
   * Removes the first instance of the specified object in the IgushArray
   * FIXME DOCUMENTATION
   *
   * @param o the instance of the element to remove
   * @return true if it is successfully removed
   */
  @Override
  public boolean remove(Object o) {
    //FIXME can improve performance by using a no bounds checked remove operation

    remove(indexOf(o));
    return true;
  }

  /**
   * Removes all of the elements from this IgushArray.
   * The list will be empty after this call returns
   */
  @Override
  public void clear() {
    for (int i = 0; i < listCapacity; i++) {
      data.get(i).clear();
    }
    size = 0;
  }

  /**
   * Appends all of the elements in the specified collection to end of this IgushArray,
   * in the order that they are returned by the specified collection's Iterator
   *
   * @param c
   * @return true if this IgushArray was modified
   */
  @Override
  public boolean addAll(Collection<? extends E> c) {
    Iterator<?> itr = c.iterator();
    boolean flag = false;
    while (itr.hasNext()) {
        add((E) itr.next());
        flag = true;
    }
    return flag;
  }

  /**
   * Inserts all of the elements in the specified collection into this IgushArray,
   * starting at the specified position.
   *
   * @param c
   * @return true if this IgushArray was modified
   */
  @Override
  public boolean addAll(int index, Collection<? extends E> c) {
    Iterator<?> itr = c.iterator();
    while (itr.hasNext()) {
        add(index, (E) itr.next());
        index++;
    }
    return c.size() > 0;
  }

  /**
   * Removes from this IgushArray all elements that are contained in the
   * specified collection.
   *
   * @param c
   * @return true if this IgushArray was modified
   */
  @Override
  public boolean removeAll(Collection<?> c) {
    Iterator<?> itr = c.iterator();
    boolean flag = false;
    while (itr.hasNext()) {
        Object o = itr.next();
        if (contains(o)) {
            remove(o);
            flag = true;
        }
    }
    return flag;
  }

  /**
   * Retains only the elements in this IgushArray that are contained in the
   * specified collection.
   *
   * @param c
   * @return true if this IgushArray was modified
   */
  @Override
  public boolean retainAll(Collection<?> c) {
    // FIXME optimize performance
    HashSet<Object> hs = new HashSet<>();
    for (int i = 0; i < size; i++) {
        Object o = get(i);
        if (!c.contains(o)) {
            hs.add(o);
        }
    }

    boolean flag = removeAll(hs);

    return flag;
  }

  /**
   * Returns true if this IgushArray contains all elements of the specified collection
   *
   * @param c
   * @return
   */
  @Override
  public boolean containsAll(Collection<?> c) {
    Iterator<?> it = c.iterator();
    boolean result = true;
    while (it.hasNext()) {
        if (!contains(it.next())) {
            result = false;
            break;
        }
    }
    return result;
  }


  @Override
  protected void removeRange(int fromIndex, int toIndex) {
    HashSet<Object> hs = new HashSet<>();
    for (int i = fromIndex; i < toIndex; i++) {
      hs.add(get(i));
    }
    removeAll(hs);
  }

  // Only used when we add/insert an element usually
  private void shiftUp(int listIndex, E frontElement) {
    while (listIndex < listCapacity) {
      FixedDeque<E> deque = data.get(listIndex);

      // We shiftUp the deque if they are full, otherwise we just add to the final non full deque and stop the
      // shifting process
      if (deque.isFull()) {
        frontElement = deque.shiftUp(frontElement);
      } else {
        deque.fixedAdd(0, frontElement);
        break;
      }
      listIndex += 1;
    }
  }

  private void shiftDown(int listIndex) {
    E endElement;
    int currListIndex = (int) Math.ceil((double) size() / deqCapacity - 1); //FIXME this could be done better
    if (currListIndex == listIndex - 1)
      return;
    FixedDeque<E> deque = data.get(currListIndex);
    endElement = deque.remove(0);
    currListIndex -= 1;

    while (currListIndex >= listIndex) {
      deque = data.get(currListIndex);

      // Move the first element of the previous deque to the end of the current deque
      endElement = deque.shiftDown(endElement);

      currListIndex -= 1;
    }

    deque = data.get(currListIndex);
    deque.add(endElement);
  }

  @Override
  public ListIterator<E> listIterator() {
    return new ListIteratorPrototype();
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    return new ListIteratorPrototype(index);
  }

  // FIXME, look at how ArrayList implements this
  // FIXME DOCUMENTATION

  /**
   * @param fromIndex
   * @param toIndex
   * @return
   */
  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    int newSize = toIndex - fromIndex;
    List<E> subArr = new IgushArray<E>(newSize);

    ListIterator<E> itr = listIterator();
    while (itr.hasNext()) {
      subArr.add(itr.next());
    }
    return subArr;
  }

  //FIXME forEach method?

  //FIXME sort method?
  /**
   *
   * @param c the comparator to use to sort the IgushArray

  public void sort(Comparator<? super E> c) {
  Collections.sort(this, c);
  }
   */

  /**
   * Returns the string representation of the contents of the IgushArray
   *
   * @return
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append('[');
    ListIterator<E> itr = listIterator();
    while (itr.hasNext()) {
      sb.append(itr.next().toString());
      if (itr.hasNext()) {
        sb.append(", ");
      }
    }

    return sb.append(']').toString();
  }

  private class ListIteratorPrototype implements ListIterator<E> {
    private int cursor;
    private int lastRet = -1; // index of last returned element;

    ListIteratorPrototype() {
      cursor = 0; // cursor is right before the first element
    }

    ListIteratorPrototype(int index) {
      cursor = index;
    }

    @Override
    public boolean hasNext() {
      if (cursor < size()) {
        return true;
      }
      return false;
    }

    @Override
    public E next() {
      if (hasNext()) {
        lastRet = cursor;
        cursor++;
        return get(lastRet);
      }
      throw new NoSuchElementException();
    }

    @Override
    public boolean hasPrevious() {
      if (cursor > 0) {
        return true;
      }
      return false;
    }

    @Override
    public E previous() {
      if (hasPrevious()) {
        lastRet = cursor;
        cursor--;
        return get(cursor);
      }
      throw new NoSuchElementException();
    }

    @Override
    public int nextIndex() {
      return cursor;
    }

    @Override
    public int previousIndex() {
      return cursor - 1;
    }

    @Override
    public void remove() {
      IgushArray.this.remove(lastRet);
      lastRet = -1;
    }

    @Override
    public void set(E e) {
      IgushArray.this.set(lastRet, e);
    }

    @Override
    public void add(E e) {
      IgushArray.this.add(cursor, e);
      lastRet = -1;
    }
  }
}
