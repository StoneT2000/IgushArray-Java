import org.junit.Before;
import org.junit.Test;
import stonet2000.igusharray.IgushArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class TestXAll {

    static final int INITIAL_AMOUNT = 55; // amount of initial values in IgushArray for testing

    static final int ADD_AMOUNT = 10; // amount of values to add for testing
    static final int RANGE = 1000; // range of values to add, namely [-RANGE/2, RANGE/2]

    static final boolean SIMPLE_VALUES = false; // flag for whether to populate a list with simple values for debugging

    IgushArray<Integer> ia;

    @Before
    public void setUp() {
        ia = new IgushArray<>();
        // add 0, 1, ..., INITIAL_AMOUNT to IgushArray
        for (int i = 0; i < INITIAL_AMOUNT; i++) {
            ia.add(i);
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
                list.add((int) (Math.random() * RANGE - RANGE / 2));
            }
        }
    }

    @Test
    public void testAddAll() {
        assertFalse(ia.addAll(new ArrayList<>()));
        ArrayList<Integer> list = new ArrayList<>();
        populateListWithIntegers(list);
        assertTrue(ia.addAll(list));

        // check initial parts not changed
        for (int i = 0; i < INITIAL_AMOUNT; i++) {
            assertEquals(ia.get(i), (Integer) i);
        }

        // check that new list elements were added in correctly
        for (int i = 0; i < ADD_AMOUNT; i++) {
            assertEquals(ia.get(INITIAL_AMOUNT + i), list.get(i));
        }
    }

    @Test
    public void testAddAllWithIndex() {
        assertFalse(ia.addAll(0, new LinkedList<>()));
        ArrayList<Integer> list = new ArrayList<>();
        populateListWithIntegers(list);
        assertTrue(ia.addAll(0, list));

        for (int i = 0; i < ADD_AMOUNT; i++) {
            assertEquals(ia.get(i), list.get(i));
        }

        // check others moved
        for (int i = ADD_AMOUNT; i < INITIAL_AMOUNT; i++) {
            assertEquals(ia.get(i), (Integer) (i - ADD_AMOUNT));
        }

    }

    @Test
    public void testRemoveAll() {
        assertFalse(ia.removeAll(new HashSet<>()));
        HashSet<Integer> hs = new HashSet<>();
        hs.add(100);
        hs.add(200);
        assertFalse(ia.removeAll(hs));
        ia.add(100);
        ia.add(200);
        assertTrue(ia.removeAll(hs));
        assertFalse(ia.contains(100));
        assertFalse(ia.contains(200));

        // check nothing else was changed
        for (int i = 0; i < 5; i++) {
            assertEquals(ia.get(i), (Integer) i);
        }
    }

    @Test
    public void testRetainAll() {
        List<Integer> hs = new LinkedList<>();
        hs.add(100);
        hs.add(200);
        ia.add(100);
        ia.add(200);

        assertTrue(ia.retainAll(hs));
        assertTrue(ia.contains(100));
        assertTrue(ia.contains(200));

        // check all others removed
        for (int i = 0; i < INITIAL_AMOUNT; i++) {
            assertFalse(ia.contains(i));
        }

        assertTrue(ia.retainAll(new HashSet<>())); // remove all
        assertTrue(ia.isEmpty());
    }

    @Test
    public void testContainsAll() {
        HashSet<Integer> hs = new HashSet<>();
        hs.add(1);
        hs.add(4);
        assertTrue(ia.containsAll(hs));
        hs.add(100);
        assertFalse(ia.containsAll(hs));
    }

    // Make removeRange public to use this test. (Don't forget to re-protect it after!)
    /*
    @Test
    public void testRemoveRange() {
        ia.removeRange(0, 5); //should remove items at indices 0-4 (remove everything)
        assertTrue(ia.isEmpty());

        for (int i = 0; i < 9; i++) {
            ia.add(i);
        }

        ia.removeRange(2, 7); //remove 2,3,4,5,6
        assertTrue(ia.contains(0));
        assertTrue(ia.contains(1));
        assertTrue(ia.contains(7));
        assertTrue(ia.contains(8));
    }
    */
}
