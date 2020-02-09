import org.junit.Before;
import org.junit.Test;
import stonet2000.igusharray.IgushArray;

import java.util.*;

import static org.junit.Assert.*;

public class TestMethods {

  static final int INITIAL_AMOUNT = 17; // amount of initial values in IgushArray for testing

  static final int ADD_AMOUNT = 10; // amount of values to add for testing
  static final int RANGE = 1000; // range of values to add, namely [-RANGE/2, RANGE/2]

  static final boolean SIMPLE_VALUES = true; // flag for whether to populate a list with simple values for debugging

  List<Integer> igushArray;
  List<Integer> truthList;

  @Before
  public void setUp() {
    igushArray = new IgushArray<>();
    // this truthList should be the same as IgushArray if we apply the same methods on them
    truthList = new ArrayList<>();

    // add 0, 1, ..., INITIAL_AMOUNT to IgushArray if using SIMPLE_VALUES
    // otherwise some random numbers
    for (int i = 0; i < INITIAL_AMOUNT; i++) {
      igushArray.add(i);
      truthList.add(i);
    }
  }

  /**
   * Populates a given list with ADD_AMOUNT of integers from the interval
   * [RANGE/2, RANGE/2]
   * @param list - the list to populate with random values
   */
  public void populateListWithIntegers(List<Integer> list) {
    for (int i = 0; i < ADD_AMOUNT; i++) {
      // TODO: Replace with seeded random number generator
      if (SIMPLE_VALUES) {
        // use simple values from INITIAL_AMOUNT + 100 to INITIAL_AMOUNT + 100 + ADD_AMOUNT
        list.add(INITIAL_AMOUNT + 100 + i);
      }
      else {
        list.add(randInt());
      }
    }
  }

  public int randInt() {
    return (int) (Math.random() * RANGE - RANGE / 2);
  }

  /**
   * Returns random index of a list
   * @param list
   * @return
   */
  public int randomIndexInList(List list) {
    return (int) (Math.random() * list.size());
  }

  @Test
  public void testAdd() {
    assertArrayEquals(igushArray.toArray(), truthList.toArray());
    truthList.add(3);
    assertFalse(Arrays.equals(igushArray.toArray(), truthList.toArray()));
    igushArray.add(3);
    assertArrayEquals(igushArray.toArray(), truthList.toArray());

    igushArray = new IgushArray<>(INITIAL_AMOUNT + 100);
    truthList = new ArrayList<>(INITIAL_AMOUNT + 100);

    // add 0, 1, ..., INITIAL_AMOUNT to IgushArray if using SIMPLE_VALUES
    // otherwise some random numbers
    for (int i = 0; i < INITIAL_AMOUNT; i++) {
      int rand = randInt();
      igushArray.add(rand);
      truthList.add(rand);
    }
    assertArrayEquals(igushArray.toArray(), truthList.toArray());
  }

  @Test
  public void testRemove() {

    // remove front index until empty
    for (int i = 0; i < INITIAL_AMOUNT / 2; i++) {
      int rand = randInt();
      igushArray.remove(0);
      truthList.remove(0);
    }

    assertArrayEquals(igushArray.toArray(), truthList.toArray());

    // remove random index until none left
    while (truthList.size() != 0) {
      int index = randomIndexInList(truthList);
      truthList.remove(index);
      igushArray.remove(index);
    }

    assertArrayEquals(igushArray.toArray(), truthList.toArray());
  }


  @Test
  public void testAddAll() {
    assertFalse(igushArray.addAll(new ArrayList<>()));
    ArrayList<Integer> list = new ArrayList<>();
    populateListWithIntegers(list);
    assertTrue(igushArray.addAll(list));

    // check initial parts not changed
    for (int i = 0; i < INITIAL_AMOUNT; i++) {
      assertEquals(igushArray.get(i), (Integer) i);
    }

    // check that new list elements were added in correctly
    for (int i = 0; i < ADD_AMOUNT; i++) {
      assertEquals(igushArray.get(INITIAL_AMOUNT + i), list.get(i));
    }
  }

  @Test
  public void testAddAllWithIndex() {
    assertFalse(igushArray.addAll(0, new LinkedList<>()));
    ArrayList<Integer> list = new ArrayList<>();
    populateListWithIntegers(list);
    assertTrue(igushArray.addAll(0, list));
    truthList.addAll(0, list);

    for (int i = 0; i < ADD_AMOUNT; i++) {
      assertEquals(igushArray.get(i), truthList.get(i));
    }

    // check others moved
    for (int i = ADD_AMOUNT; i < INITIAL_AMOUNT; i++) {
      assertEquals(igushArray.get(i), truthList.get(i));
    }

  }

  @Test
  public void testRemoveAll() {
    assertFalse(igushArray.removeAll(new HashSet<>()));
    HashSet<Integer> hs = new HashSet<>();
    hs.add(100);
    hs.add(200);
    assertFalse(igushArray.removeAll(hs));
    igushArray.add(100);
    igushArray.add(200);
    assertTrue(igushArray.removeAll(hs));
    assertFalse(igushArray.contains(100));
    assertFalse(igushArray.contains(200));

    // check nothing else was changed
    for (int i = 0; i < 5; i++) {
      assertEquals(igushArray.get(i), (Integer) i);
    }
  }

  @Test
  public void testRetainAll() {
    List<Integer> hs = new LinkedList<>();
    hs.add(100);
    hs.add(200);
    igushArray.add(100);
    igushArray.add(200);

    assertTrue(igushArray.retainAll(hs));
    assertTrue(igushArray.contains(100));
    assertTrue(igushArray.contains(200));

    // check all others removed
    for (int i = 0; i < INITIAL_AMOUNT; i++) {
      assertFalse(igushArray.contains(i));
    }

    assertTrue(igushArray.retainAll(new HashSet<>())); // remove all
    assertTrue(igushArray.isEmpty());
  }

  @Test
  public void testContainsAll() {
    HashSet<Integer> hs = new HashSet<>();
    hs.add(1);
    hs.add(4);
    assertTrue(igushArray.containsAll(hs));
    hs.add(100);
    assertFalse(igushArray.containsAll(hs));
  }

  // Make removeRange public to use this test. (Don't forget to re-protect it after!)
    /*
    @Test
    public void testRemoveRange() {
        igushArray.removeRange(0, 5); //should remove items at indices 0-4 (remove everything)
        assertTrue(igushArray.isEmpty());

        for (int i = 0; i < 9; i++) {
            igushArray.add(i);
        }

        igushArray.removeRange(2, 7); //remove 2,3,4,5,6
        assertTrue(igushArray.contains(0));
        assertTrue(igushArray.contains(1));
        assertTrue(igushArray.contains(7));
        assertTrue(igushArray.contains(8));
    }
    */
}
