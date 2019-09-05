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
      super.add(element);
      end = (end + 1) % capacity;
      return true;
    }

    return false;
  }
  @Override
  public void add(int index, E element) {
    throw new Error("Please use fixedAdd to add an element at this index");
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
        addIndex = (begin + index) % size();


      //FIXME: can be reduced to one if statement using ||
      if (index == size()) {
        // adding to end
        begin = (begin + 1);
        end = (end + 1);
      }
      else if (index != 0){
        if (addIndex <= end) {
          begin = (begin + 1) % size();
          end = (end + 1) % size();
        }
      }
      super.add(addIndex, element);
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
    E removedElement = get(index);
    int removeIndex = (begin + index) % size();
    super.remove(removeIndex);
    if (removeIndex <= begin)
      begin = (begin - 1 + size()) % size();
    if (removeIndex <= end)
      end = (end - 1 + size()) % size();
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

    E oldElement = set(end, element);
    end = (end - 1 + capacity) % capacity;
    begin = (begin - 1 + capacity) % capacity;
    return oldElement;
  }

  /**
   * Returns the first element and moves a element to the end of the fixed deque. To be used only when deque is full
   *
   * @param element the element to shift to the front of the deque
   * @return the last element which is removed.
   */
  public E shiftDown(E element) {

    E oldElement = set(begin, element);
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
      //System.out.println((begin + i) % capacity);
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