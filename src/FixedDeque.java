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

import java.util.ArrayList;

/**
 * A Fixed Deque implemented with a Ring/Circular Buffer. This is not a full implementation, to be only used with IgushArray
 *
 * @param <E> the type of data stored in this fixed deque
 * @author Stone Tao
 */
public class FixedDeque<E> extends ArrayList<E> {
  private int begin;
  private int end;
  private int capacity;

  public FixedDeque(int capacity) {
    super(capacity);
    begin = 0;
    end = -1;
    this.capacity = capacity;
  }

  @Override
  public boolean add(E element) {
    if (size() < capacity) {
      super.add(end + 1, element);
      if (end < begin) {
        begin = (begin + 1) % size();
      }
      end = (end + 1) % size();
      return true;
    }

    return false;
  }

  @Override
  public void add(int index, E element) {
    fixedAdd(index, element);
  }

  @Override
  public E get(int index) {
    rangeCheck(index);
    return super.get((begin + index) % size());
  }

  @Override
  public E set(int index, E element) {
    rangeCheck(index);
    E temp = get(index);
    super.set((begin + index) % size(), element);
    return temp;
  }


  /**
   * This is ArrayList.add but returning true if added and false if not added because capacity reached
   * Will always increase size of ArrayList by 1.
   *
   * @param index
   * @param element
   * @return
   */
  public boolean fixedAdd(int index, E element) {
    if (size() < capacity) {
      rangeCheckForAdd(index);
      int addIndex;
      if (size() == 0)
        addIndex = (begin + index);
      else
        addIndex = (begin + index) % (size() + 1);


      super.add(addIndex, element);

      //FIXME reduce code
      if (index == size()) {
        // adding to end
        end = (end + 1) % size(); // always moves up
        if (addIndex <= begin) {
          begin = (begin + 1) % size();
        }
      } else {
        if (addIndex < begin) {
          begin = (begin + 1) % size();
        }
        if (addIndex <= end) {
          end = (end + 1) % size();
        }
      }
      return true;
    }
    return false;
  }

  public boolean isFull() {
    return size() == capacity;
  }

  private boolean rangeCheck(int index) {
    if (index < size() && index >= 0) {
      return true;
    }
    throw new IndexOutOfBoundsException("Index: " + index + " is out of bounds of fixed deque with size " + size());
  }

  private boolean rangeCheckForAdd(int index) {
    if (index <= size() && index >= 0) {
      return true;
    }
    throw new IndexOutOfBoundsException("Index: " + index + " is out of bounds of fixed deque with size " + size());
  }

  @Override
  public E remove(int index) {
    rangeCheck(index);

    int removeIndex = (begin + index) % size();
    E removedElement = get(index); // use index here as get does the offsetting for us
    super.remove(removeIndex);
    if (removeIndex < begin)
      begin = size() == 0 ? begin - 1 : (begin - 1 + size()) % size();
    if (removeIndex <= end)
      end = size() == 0 ? end - 1 : (end - 1 + size()) % size();
    return removedElement;
  }

  public E pop() {
    E endElement = get(size() - 1);
    end = (end - 1 + capacity) % capacity;
    return endElement;
  }

  public E popFront() {
    E frontElement = get(0);
    begin = (begin + 1) % capacity;
    return frontElement;
  }

  public boolean pushFront(E element) {
    if (size() < capacity) {
      fixedAdd(0, element);
      return true;
    }
    // if the fixed deque is full, we should perform a O(1) move operation in the IgushArray.java
    return false;
  }

  /**
   * Returns the last element and moves a element to the front of the fixed deque. To be used only when deque is full
   *
   * @param element the element to shift to the front of the deque
   * @return the last element which is removed.
   */
  public E shiftUp(E element) {

    E oldElement = set(size() - 1, element);
    end = (end - 1 + capacity) % capacity;
    begin = (begin - 1 + capacity) % capacity;
    return oldElement;
  }

  /**
   * Returns the first element and moves a element to the end of the fixed deque. To be used only when deque is full
   *
   * @param element the element to shift to the end of the deque
   * @return the first element which is removed.
   */
  public E shiftDown(E element) {

    E oldElement = set(0, element);
    end = (end + 1) % capacity;
    begin = (begin + 1) % capacity;
    return oldElement;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append('[');
    int i = 0;
    for (; i < size() - 1; i++) {
      sb.append(get(i));
      sb.append(',').append(' ');
    }
    if (i < size()) {
      sb.append(get(i));
    }
    sb.append(']');
    return sb.toString();
  }

  public String origString() {
    System.out.println("Begin: " + begin + ", End: " + end + ", Size: " + size());
    return super.toString();
  }
}