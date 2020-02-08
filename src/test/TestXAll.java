import org.junit.Before;
import org.junit.Test;
import stonet2000.igusharray.IgushArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class TestXAll {

    IgushArray<Integer> ia;

    @Before
    public void setUp() {
        ia = new IgushArray<>();
        for (int i = 0; i < 5; i++) {
            ia.add(i);
        }
    }

    @Test
    public void testAddAll() {
        assertFalse(ia.addAll(new ArrayList<>()));
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1234);
        list.add(5678);
        list.add(91011);
        assertTrue(ia.addAll(list));
        for (int i = 0; i < 5; i++) { //check parts not changed
            assertEquals(ia.get(i), (Integer) i);
        }
        assertEquals(ia.get(5), (Integer) 1234);
        assertEquals(ia.get(6), (Integer) 5678);
        assertEquals(ia.get(7), (Integer) 91011);
    }

    @Test
    public void testAddAllWithIndex() {
        assertFalse(ia.addAll(0, new LinkedList<>()));
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1234);
        list.add(5678);
        list.add(91011);
        assertTrue(ia.addAll(0, list));

        assertEquals(ia.get(0), (Integer) 1234);
        assertEquals(ia.get(1), (Integer) 5678);
        assertEquals(ia.get(2), (Integer) 91011);

        for (int i = 3; i < 8; i++) { //check others moved
            assertEquals(ia.get(i), (Integer) (i-3));
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
        for (int i = 0; i < 5; i++) { //check nothing else was changed
            assertEquals(ia.get(i), (Integer) i);
        }
    }

    @Test
    public void testRetainAll() {
        LinkedList<Integer> hs = new LinkedList<>();
        hs.add(100);
        hs.add(200);
        ia.add(100);
        ia.add(200);

        assertTrue(ia.retainAll(hs));
        assertTrue(ia.contains(100));
        assertTrue(ia.contains(200));

        for (int i = 0; i < 5; i++) { //check all others removed
            assertFalse(ia.contains(i));
        }

        assertTrue(ia.retainAll(new HashSet<>())); //remove all
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
