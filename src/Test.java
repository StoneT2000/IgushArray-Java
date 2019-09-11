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

import java.io.*;
import java.util.*;

public class Test {


  public static void main(String args[]) throws IOException {

    FileWriter csvWriter = new FileWriter("stats-insert.csv");
    csvWriter.append("List Type");
    csvWriter.append(",");
    csvWriter.append("1000 Inserts to index 0");
    csvWriter.append(",");
    csvWriter.append("Time (ms)");
    csvWriter.append("\n");
    csvWriter.close();


    /* Testing:
    We create Lists of minimum size to accommodate the testing
    All tests perform 1000 operations for at least N=10 trials at the moment.
    */

    System.out.println("Testing IgushArray and ArrayList Insertion At Index 0/Push Front/Unshift | 1000 executions 50 trials\n");
    // Initialize IgushArray of capacity 1E6

    System.out.printf("%-20s%-24s%-24s%-24s%-24s\n", "List Type", "List Size", "Average(ms)", "Max(ms)", "Min(ms)");

    for (int i = 3; i < 8; i++) {
      testPushFrontTime("IgushArray", new IgushArray((int) Math.pow(10,i)), (int) Math.pow(10,i), 1000, 50);
    }
    for (int i = 3; i < 8; i++) {
      testPushFrontTime("ArrayList", new ArrayList((int) Math.pow(10,i)), (int) Math.pow(10,i), 1000, 50);
    }

    System.out.println("\nTesting IgushArray and ArrayList random access | 100,000 executions 50 trials\n");
    System.out.printf("%-20s%-24s%-24s%-24s%-24s\n", "List Type", "List Size", "Average(ms)", "Max(ms)", "Min(ms)");

    for (int i = 3; i < 8; i++) {
      testAccessTime("IgushArray", new IgushArray((int) Math.pow(10,i)), (int) Math.pow(10,i), 100000, 50);
    }
    for (int i = 3; i < 8; i++) {
      testAccessTime("ArrayList", new ArrayList((int) Math.pow(10,i)), (int) Math.pow(10,i), 100000, 50);
    }

    System.out.println("\nTesting IgushArray and ArrayList remove front | 1000 executions 50 trials\n");
    System.out.printf("%-20s%-24s%-24s%-24s%-24s\n", "List Type", "List Size", "Average(ms)", "Max(ms)", "Min(ms)");
    for (int i = 3; i < 8; i++) {
      testRemoveFront("IgushArray", new IgushArray((int) Math.pow(10,i)), (int) Math.pow(10,i), 1000, 50);
    }
    for (int i = 3; i < 8; i++) {
      testRemoveFront("ArrayList", new ArrayList((int) Math.pow(10,i)), (int) Math.pow(10,i), 1000, 50);
    }


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

  public static void displayStats(int size, DoubleSummaryStatistics dss) {
    System.out.printf("%-24s", size);
    System.out.printf("%-24.4f", dss.getAverage());
    System.out.printf("%-24.4f", dss.getMax());
    System.out.printf("%-24.4f", dss.getMin());
    System.out.print("\n");
  }

  public static void clearAndInitialize(List list, int size) {
    list.clear();
    Random rand = new Random(1000);
    for (int i = 0; i < size; i++) {
      list.add(rand.nextInt());
    }
  }

  public static void testPushFrontTime(String name, List list, int size, int executions, int trials) {
    System.out.printf("%-20s", name);
    double[] stats = new double[trials];
    DoubleSummaryStatistics dss = new DoubleSummaryStatistics();
    for (int i = 0; i < trials; i++) {
      clearAndInitialize(list, (int) size - executions);
      stats[i] = pushFrontTime(list, executions);
      dss.accept(stats[i]);
    }
    displayStats(list.size(), dss);
  }

  // Gives average time to push/insert to the front of this list for the given number of insertions/executions
  public static double pushFrontTime(List list, int executions) {
    Random rand = new Random(1000);
    long stime = System.currentTimeMillis();
    for (int i = 0; i < executions; i++) {
      list.add(0, rand.nextInt());
    }
    long ftime = System.currentTimeMillis();
    return (double) (ftime - stime);
  }

  public static void testAccessTime(String name, List list, int size, int executions, int trials) {
    System.out.printf("%-20s", name);
    double[] stats = new double[trials];
    DoubleSummaryStatistics dss = new DoubleSummaryStatistics();
    clearAndInitialize(list, (int) size);
    for (int i = 0; i < trials; i++) {
      stats[i] = accessTime(list, executions);
      dss.accept(stats[i]);
    }
    displayStats(list.size(), dss);
  }

  public static double accessTime(List list, int executions) {
    Random rand = new Random(1000);
    long stime = System.currentTimeMillis();
    for (int i = 0; i < executions; i++) {
      list.get((int) (rand.nextInt(list.size())));
    }
    long ftime = System.currentTimeMillis();
    return (double) (ftime - stime);
  }

  public static void testRemoveFront(String name, List list, int size, int executions, int trials) {
    System.out.printf("%-20s", name);
    double[] stats = new double[trials];
    DoubleSummaryStatistics dss = new DoubleSummaryStatistics();
    for (int i = 0; i < trials; i++) {
      clearAndInitialize(list, (int) size);
      stats[i] = removeFront(list, executions);
      dss.accept(stats[i]);
    }
    displayStats(size, dss);
  }

  public static double removeFront(List list, int executions) {
    long stime = System.currentTimeMillis();
    for (int i = 0; i < executions; i++) {
      list.remove(0);
    }
    long ftime = System.currentTimeMillis();
    return (double) (ftime - stime);
  }

}
