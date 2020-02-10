# IgushArray Java
 An implementation of the [IgushArray](https://github.com/igushev/IgushArray) in Java, with **O(1)** access and **O(N^1/2)** insertion and removal

The java implementation of the IgushArray is a one for one replacement for an ArrayList as it extends **[AbstractList](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractList.html)\<E>** and implements the same interfaces as ArrayList, namely **[Serializable](https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html), [Cloneable](https://docs.oracle.com/javase/7/docs/api/java/lang/Cloneable.html), [List](https://docs.oracle.com/javase/7/docs/api/java/util/List.html)\<E>, [RandomAccess](https://docs.oracle.com/javase/7/docs/api/java/util/RandomAccess.html)**, whilst also including some of ArrayList's own methods.

You are welcome to **contribute** to this repository, just create a pull request and submit it. Need an idea on what to work on? Check out the **Todo** list at the bottom of this readme. Have a suggestion or issue? Submit an issue to this repository on GitHub.

## Overview

Comparisons of time complexities in key operations in similar data structures are shown below.

| Operation        | Array | IgushArray        | List  |
| ---------------- | ----- | ----------------- | ----- |
| Access (Get)     | O (1) | **O (1)**         | O (N) |
| Insert (Add)     | O (N) | **O (N**^**1/2)** | O (1) |
| Erase (Remove)   | O (N) | **O (N**^**1/2)** | O (1) |
| Push Back (Push) | O (1) | O (1)             | O (1) |
| Push Front       | O (N) | **O (N**^**1/2)** | O (1) |

More details on the general idea, motivation etc. can be found [here](https://github.com/igushev/IgushArray#overview)

# Performance Tests

The performance tests are shown below. The benchmarking code and parameters can be found in **src/main/test/Benchmarking.Java**. 

Note, all timings are in milliseconds. The Java Microbench Harness library was used from openjdk to benchmark the code and compare with ArrayList

## Access by Index

The results shown are the results of accessing random indices 1,000,000 times.

It can be seen that the IgushArray maintains **O(1)** access time complexity, equivalent to ArrayList. However, there is a little extra overhead with the get operation and access times are longer, but still constant.

| List Size and Capacity | IgushArray     | ArrayList      |
| ---------------------- | -------------- | -------------- |
| 1,000                  | 96.174 ± 2.247 | 29.222 ± 0.971 |
| 10,000                 | 96.179 ± 1.747 | 29.054 ± 0.633 |
| 100,000                | 97.847 ± 3.720 | 28.907 ± 0.611 |
| 1,000,000              | 98.262 ± 5.650 | 29.132 ± 0.690 |

## Insertion / Push Front

The results shown are the result of inserting 1000 elements to the front of the array, without needing to increase capacity of the arrays. In particular, the lists were initialized to a capacity of 10^k, initialized to a size of 10^k - 1000, then insertions were performed to fill the list to capacity.

It can be seen that for larger lists, IgushArray out performs the ArrayList by a massive amount.

| List Size and Capacity | IgushArray     | ArrayList           |
| ---------------------- | -------------- | ------------------- |
| 1,000                  | 0.504 ± 0.017  | 0.123 ± 0.004       |
| 10,000                 | 1.302 ± 0.028  | 0.825 ± 0.025       |
| 100,000                | 4.408 ± 0.063  | 10.478 ± 1.591      |
| 1,000,000              | 28.194 ± 3.392 | 230.084 ± 26.017    |
| 10,000,000             | 91.762 ± 4.132 | 12415.907 ± 451.443 |

## Remove Front

The results shown are the result of removing the front element 1000 times. In particular, the lists were initialized to a capacity of 10^k, initialized to a size of 10^k, then 1000 removals from the front were performed.

It can be seen that for larger lists, IgushArray out performs the ArrayList by a massive amount.

| List Capacity | IgushArray     | ArrayList        |
| ------------- | -------------- | ---------------- |
| 1,000         | 0.267 ± 0.011  | 0.060 ± 0.004    |
| 10,000        | 1.199 ± 0.022  | 0.836 ± 0.126    |
| 100,000       | 4.112 ± 0.056  | 13.253 ± 1.699   |
| 1,000,000     | 22.776 ± 1.197 | 189.594 ± 10.297 |

# Implementation

Please see [here](https://github.com/igushev/IgushArray#implementation) for an in depth explanation of how this is generally implemented.

For the Java version, the IgushArray is essentially an ArrayList of FixedDeques.

Each FixedDeque is a fixed length Deque (Double Ended Queue) implemented as a Circular/Ring Buffer. Additionally, to maintain **O(1)** access time complexity, the Circular/Ring Buffer is implemented with ArrayList, which works as a contiguous set of elements in memory.

# Todo

- Finish documentation of all methods, akin to ArrayList (JavaDocs)
- Finish rewrite of all ArrayList methods
- Finish stability test code to test all the IgushArray methods and logic and compare result to ArrayList
- Construct graphs based on the data to show performance comparisons
- Fine tune methods to improve speed (use some optimized private methods for internal calculations)
