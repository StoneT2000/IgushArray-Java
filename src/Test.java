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

public class Test {


  public static void main(String args[]) {
    //IgushArray<Integer> arrayTest = new IgushArray(40); // initialize a new IgushArray with 100 elements
    //System.out.println(arrayTest.toString());
    /*
    FixedDeque<Integer> test = new FixedDeque<>(5);
    test.add(1);
    test.add(2);
    test.add(3);
    test.add(4);
    test.add(5);
    System.out.println(test.toString());

    Integer newElement = test.shiftUp(0);

    System.out.println(test.toString());
    test.remove(2);
    System.out.println(test.toString());
    System.out.println(test.origString());
    test.fixedAdd(4, 14);
    System.out.println(test.toString());
    System.out.println(test.origString());
  */
    IgushArray<Integer> test = new IgushArray((int) (10*1E5));

    System.out.println("Testing IgushArray Insertion At Index 0/Push Front/Unshift");
    testPushFront(test, "IgushArray");

    ArrayList<Integer> testArrayList = new ArrayList<>((int) (10*1E5));

    System.out.println("Testing ArrayList Insertion At Index 0/Push Front/Unshift");
    testPushFront(testArrayList, "ArrayList");


  }
  public static void testPushFront(List list, String name) {
    for (int i = 0; i < 10; i++) {
      long stime = System.currentTimeMillis();
      for (int j = 0; j < 1E5; j++)
      {
        list.add(0, (int) (Math.random() * 20));
      }
      long ftime = System.currentTimeMillis();
      System.out.printf("%-15s",name);
      System.out.println("Batch - " + i + ": " + (ftime - stime));
    }
  }
}
