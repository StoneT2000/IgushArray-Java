/**
 * MIT License
 *
 * Copyright (c) 2019 StoneT2000 (Stone Tao)
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

import java.util.*;

public class IgushArray<E> implements Iterable<E>, Collection<E>, List<E>, RandomAccess {

  public List<FixedDeque<E>> data; // data is stored as a list of lists (array of arrays).
  private int capacity; // total capacity of the IgushArray
  private int size; // the current size of IgushArray, not the same as capacity
  private int deqSize; // size of each ArrayDeque in the list
  private int listSize; // size of the list containing references to the ArrayDeques
  private int lastDeqSize; // == capacity % deqSize;
  /*
   * To ensure O(1) access time, each list in the data list is implemented with ArrayList, not ArrayDeque.
   */

  public IgushArray(int capacity) {

    this.capacity = capacity;
    deqSize = (int) Math.pow(capacity, 0.5);
    lastDeqSize = capacity % deqSize;
    listSize = (int) Math.ceil((double) capacity / deqSize);

    /* System.out.println("New Igush Array: \nCapacity: " + capacity +
            " | listSize: " + listSize +
            " | deqSize: " + deqSize +
            " | lastDeqSize: " + lastDeqSize);
    */

    data = new ArrayList<>(listSize);
    for (int i = 0; i < listSize - 1; i++) {
      data.add(new FixedDeque<E>(deqSize));
    }
    if (lastDeqSize != 0) {
      data.add(new FixedDeque<E>(lastDeqSize));
    } else {
      data.add(new FixedDeque<E>(deqSize));
    }
    size = 0;

  }

  // initialize IgushArray with an existing Arraylist
  public IgushArray(ArrayList<E> arr) {

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
   * Returns the size of the IgushArray, which is the number of elements stored in this collection
   *
   * @return
   */
  @Override
  public int size() {
    return this.size;
  }

  /**
   * Returns whether the IgushArray is empty or not
   *
   * @return
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
   * @param o
   * @return
   */
  @Override
  public boolean contains(Object o) {
    return indexOf(o) >= 0;
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

  @Override
  public Object[] toArray() {
    return new Object[0];
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return null;
  }

  private void rangeCheck(int index) {
    // Attempting to access an index outside of the allocated memory
    if (index >= capacity)
      throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
  }

  private void rangeCheckForAdd(int index) {
    if (index > size() || index < 0) {
      throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
  }

  private String outOfBoundsMsg(int index) {
    return "Index: " + index + ", Size: " + size;
  }

  /**
   * Adds an element to the end of the IgushArray. The IgushArray will automatically allocate more memory
   * for the array if there isn't enough capacity. By default... it expands by a factor of FIXME
   *
   * @param e The element to add
   * @return
   */
  @Override
  // will also automatically reallocate memory if size reaches capacity
  public boolean add(E e) {
    if (size() < capacity) {
      int listIndex = (int) (size() / deqSize);
      int deqIndex = size() % deqSize;
      data.get(listIndex).add(e);
      size++;
      return true;
    } else {
      //we reach capacity, we need to expand the capacities of the deques.
      // NOT RECOMMENDED TO OCCUR
    }
    return false;
  }

  /**
   * Removes the first instance of the object FIXME
   *
   * @param o
   * @return
   */
  @Override
  public boolean remove(Object o) {
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    return false;
  }

  @Override
  public boolean addAll(int index, Collection<? extends E> c) {
    return false;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return false;
  }

  /**
   * Removes all of the elements from this IgushArray.
   * The list will be empty after this call returns
   */
  @Override
  public void clear() {
    for (int i = 0; i < listSize; i++) {
      data.get(i).clear();
      ;
    }
    size = 0;
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

    int listIndex = (int) (index / deqSize);
    int deqIndex = index % deqSize;
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
    int listIndex = (int) (index / deqSize);
    int deqIndex = index % deqSize;

    return data.get(listIndex).set(deqIndex, element);
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

    int listIndex = index / deqSize;
    int deqIndex = index % deqSize;
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

  // Only used when we add/insert an element usually
  private void shiftUp(int listIndex, E frontElement) {
    while (listIndex < listSize) {
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

  //FIXME
  // Only used when we remove/erase an element usually
  private void shiftDown(int listIndex) {
    E endElement;
    int currListIndex = (int) Math.ceil((double) size() / deqSize - 1); //FIXME this could be done better
    if (currListIndex == 0)
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
    int k;
    //deque.shiftDown(endElement);

  }

  @Override
  public E remove(int index) {
    rangeCheck(index);
    int listIndex = index / deqSize;
    int deqIndex = index % deqSize;
    FixedDeque<E> deque = data.get(listIndex);
    E removedElement = deque.remove(deqIndex);
    shiftDown(listIndex + 1);
    size--;
    return removedElement;
  }

  /**
   * Increases the capacity of this IgushArray instance, if necessary, to ensure that it can hold
   * at least the number of elements specified by the minimum capacity argument. Akin to ArrayList's same method
   *
   * @param minCapacity
   */
  public void ensureCapacity(int minCapacity) {

  }


  @Override
  public int indexOf(Object o) {
    // in the future, use listIterator to find it.

    if (o == null) {
      for (int i = 0; i < capacity; i++)
        if (data.get(i) == null)
          return i;
    } else {
      for (int i = 0; i < capacity; i++)
        if (o.equals(data.get(i)))
          return i;
    }
    return -1;
  }

  @Override
  public int lastIndexOf(Object o) {
    return 0;
  }

  @Override
  public ListIterator<E> listIterator() {
    return new ListIteratorPrototype();
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    return new ListIteratorPrototype(index);
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    return null;
  }

  public List<ArrayDeque<E>> subIgushList(int fromIndex, int toIndex) {

    return null;
  }

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
      IgushArray.this.set(cursor, e);
    }

    @Override
    public void add(E e) {
      IgushArray.this.add(cursor, e);
      lastRet = -1;
    }
  }
}