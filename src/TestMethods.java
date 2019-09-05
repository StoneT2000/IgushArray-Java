/**
 * MIT License
 *
 * Copyright (c) 2019 StoneT2000 (Stone Tao) email <stonezt2019@gmail.com>
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
