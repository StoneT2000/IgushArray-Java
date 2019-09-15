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
  public static void main(String args[]) throws Exception {

    /*
      Test class initialization
     */
    System.out.println("-- Testing class initialization --");
    ArrayList<Integer> list = new ArrayList<>(10);
    for (int i = 0; i < 10; i++) {
      list.add(i);
    }
    IgushArray<Integer> test = new IgushArray<>(list, 20);
    compareLists(list, test);
    if (test.capacity() != 20) throw new Exception("Capacity not correct");
    test = new IgushArray<>(list);
    compareLists(list, test);
    test = new IgushArray<>(20);
    test.add(10);
    test.add(20);
    test.remove(1);
    list.add(10);
    list.add(20);
    list.remove(1);


    /*
    Test indexOf, lastIndexOf
     */
    System.out.println("-- Testing indexOf lastIndexOf --");
    test = new IgushArray<>(20);
    list = new ArrayList<>(20);
    int randInt = (int) (Math.random() * 10000 - 5000);
    for (int i = 0; i < 20; i++) {
      if (i % 4 == 0) {
        test.add(randInt);
        list.add(randInt);
      }
      else {
        test.add(i);
        list.add(i);
      }
    }
    test.add(randInt);
    list.add(randInt);
    if(test.lastIndexOf(randInt) != list.lastIndexOf(randInt)) throw new Exception("lastIndexOf failed");
    if(test.indexOf(randInt) != list.indexOf(randInt)) throw new Exception("indexOf failed");

    /*
    Test using collections method on IgushArray
     */
    System.out.println("-- Testing collections manipulation on lists --");
    list = new ArrayList<>(100);
    for (int i = 0; i < 10; i++) {
      list.add(100 - i);
    }
    test = new IgushArray<>(list, 100);
    Collections.sort(test);
    Collections.reverse(test);
    Collections.sort(list);
    Collections.reverse(list);
    compareLists(list, test);
    Collections.max(test);

    /*
    Test addition, insertion, popping, pushing/addition, removal
     */

    test.add(100);
    test.add(1043);
    test.add(-142);
    list.add(100);
    list.add(1043);
    list.add(-142);
    compareLists(list, test);


    test = new IgushArray<>(10);
    list = new ArrayList<>(10);
    for (int i = 0; i < 10; i++) {
      test.add(0);
      list.add(0);
    }
    list.add(1);
    test.add(1); // ensure capacity

    compareLists(list, test);

    test = new IgushArray<>(20);
    list = new ArrayList<>(20);
    for (int i = 0; i < 20; i++) {
      test.add(i);
      list.add(i);
    }
    test.ensureCapacity(100);
    list.ensureCapacity(100);
    compareLists(list, test);
    if (test.capacity() != 100) throw new Exception("Capacity not correct");

    test = new IgushArray<>(12);
    list = new ArrayList<>(12);
    for (int i = 0; i < 12; i++) {
      test.add(i);
      list.add(i);
    }
    test.ensureCapacity(30);
    list.ensureCapacity(30);
    compareLists(list, test);

    test = new IgushArray<>(11);
    list = new ArrayList<>(11);
    for (int i = 0; i < 11; i++) {
      test.add(i);
      list.add(i);
    }
    test.ensureCapacity(100);
    list.ensureCapacity(100);
    compareLists(list, test);

    test = new IgushArray<>(10);
    list = new ArrayList<>(10);
    for (int i = 0; i < 10; i++) {
      test.add(i);
      list.add(i);
    }
    test.ensureCapacity(20);
    list.ensureCapacity(20);
    for (int i = 0; i < 4; i++) {
      test.remove(4);
      list.remove(4);
    }
    compareLists(list, test);
  }

  public static void compareLists(List list1, List list2) throws Exception {
    for (int i = 0; i < list1.size(); i++) {
      if (!list1.get(i).equals(list2.get(i))) {
        System.out.println("List 1: " + list1.toString());
        System.out.println("List 2: " + list2.toString());
        throw new Exception("Lists not equal");
      }
    }
    System.out.println("Lists equal");

  }
}
