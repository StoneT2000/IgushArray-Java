import java.util.*;

public class TestMethods {
  /**
   * Integrity Testing for all methods. Use JUnit in future
   */
  public static void main(String args[]) {

    /*
      Test class initialization
     */
    ArrayList<Integer> list = new ArrayList<>(10);
    for (int i = 0; i < 10; i++) {
      list.add(i);
    }
    IgushArray<Integer> test = new IgushArray<>(list, 20);
    System.out.println(test.toString() + " == 0 to 9");
    test = new IgushArray<>(list);
    System.out.println(test.toString() + " == 0 to 9");
    test = new IgushArray<>(20);
    test.add(10);
    test.add(20);
    test.remove(1);
    System.out.println(test.toString() + " == [10]");


    /*
    Test indexOf, lastIndexOf
     */
    test = new IgushArray<>(list, 20);
    test.add(4);
    System.out.println(test.lastIndexOf(4) + " == " + 10);
    System.out.println(test.indexOf(4) + " == " + 4);

    /*
    Test using collections method on IgushArray
     */
    for (int i = 0; i < 10; i++) {
      list.add(100 - i);
    }
    test = new IgushArray<>(list, 100);
    System.out.println(test.toString() + " == unsorted");
    Collections.sort(test);
    System.out.println(test.toString() + " == sorted");
    Collections.reverse(test);
    System.out.println(test.toString() + " == descending sorted");
    System.out.println(Collections.max(test) + " == " + 100);

    /*
    Test addition, insertion, popping, pushing/addition, removal
     */

    test.add(100);
    test.add(1043);
    test.add(142);
    System.out.println(sum(test) + " == 2285");

  }
  public static Integer sum(List list) {
    Integer sum = 0;
    ListIterator itr = list.listIterator();
    while (itr.hasNext())
      sum += (Integer) itr.next();
    return sum;
  }
}
