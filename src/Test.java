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

import java.io.*;
import java.util.*;

public class Test {


  public static void main(String args[]) throws IOException {

    /*
    System.out.println("Test adding procedures");
    IgushArray<Integer> testAdd = new IgushArray((int) (17));
    for (int i = 0; i < 14; i++) {
      testAdd.add(i);
    }
    System.out.println(testAdd.toString());
    testAdd.add(6,1000);
    System.out.println(testAdd.toString());
    testAdd.add(0,1001);
    System.out.println(testAdd.toString());
    testAdd.remove(0);
    System.out.println(testAdd.toString());
    testAdd.add(testAdd.size(), 1002);
    testAdd.remove(4);
    testAdd.remove(4);
    System.out.println(testAdd.toString());
    */

    /*
    System.out.println(test.toString());
    test.remove(0);
    System.out.println(test.toString());
    test.remove(0);
    System.out.println(test.toString());
    test.remove(8);
    System.out.println(test.toString());

    */
    System.out.println("Testing IgushArray Insertion At Index 0/Push Front/Unshift");
    // Initialize IgushArray of capacity 1E6
    IgushArray<Integer> test = new IgushArray((int) (10 * 1E5));
    testPushFront(test, "IgushArray");
    System.out.println(test.toString());
    Collections.sort(test);
    System.out.println(test.toString());
    System.out.println("Testing ArrayList Insertion At Index 0/Push Front/Unshift");
    // Initialize ArrayList of capacity 1E6
    ArrayList<Integer> testArrayList = new ArrayList<>((int) (10 * 1E5));


    testPushFront(testArrayList, "ArrayList");
    Collections.sort(testArrayList);
    System.out.println(testArrayList.toString());



    /*
    System.out.println();
    System.out.println("Testing IgushArray random access");
    testAccess(test, "IgushArray");
    System.out.println("Testing ArrayList random access");
    testAccess(testArrayList, "ArrayList");

    System.out.println();
    System.out.println("Testing IgushArray remove from front");
    testRemoveFront(test, "IgushArray");
    System.out.println("Testing ArrayList remove from front");
    testRemoveFront(testArrayList, "ArrayList");
    */

  }

  /**
   * Tests a list's speed to sequentially push 1E6 elements to the front of the list
   * @param list
   * @param name
   */
  public static void testPushFront(List list, String name) {
    for (int i = 0; i < 10; i++) {
      long stime = System.currentTimeMillis();
      for (int j = 0; j < 3; j++) {
        list.add(0, (int) (Math.random() * 20));
      }
      long ftime = System.currentTimeMillis();
      System.out.printf("%-15s", name);
      System.out.println("Batch - " + i + ": " + (ftime - stime) + "ms");
    }
  }

  /**
   * Tests a list's speed to access 1E7 random indices 10 times
   * @param list
   * @param name
   */
  public static void testAccess(List list, String name) {
    for (int i = 0; i < 10; i++) {
      long stime = System.currentTimeMillis();
      for (int j = 0; j < 1E7; j++) {
        list.get((int) (Math.random() * list.size()));
      }
      long ftime = System.currentTimeMillis();
      System.out.printf("%-15s", name);
      System.out.println("Batch - " + i + ": " + (ftime - stime) + "ms");
    }
  }

  public static void testRemoveFront(List list, String name) {
    for (int i = 0; i < 10; i++) {
      long stime = System.currentTimeMillis();
      for (int j = 0; j < 1E5; j++) {
        list.remove(0);
      }
      long ftime = System.currentTimeMillis();
      System.out.printf("%-15s", name);
      System.out.println("Batch - " + i + ": " + (ftime - stime) + "ms");
    }
  }
}
