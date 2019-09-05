# IgushArray-Java WIP
 An implementation of the [IgushArray](https://github.com/igushev/IgushArray) in Java, with **O(1)** access and **O(N^1/2)** insertion and removal

The java implementation of the IgushArray is a one for one replacement for an ArrayList as it extendsextends **[AbstractList](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractList.html)\<E>** and implements the same interfaces as ArrayList, namely **[Serializable](https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html), [Cloneable](https://docs.oracle.com/javase/7/docs/api/java/lang/Cloneable.html), [List](https://docs.oracle.com/javase/7/docs/api/java/util/List.html)\<E>, [RandomAccess](https://docs.oracle.com/javase/7/docs/api/java/util/RandomAccess.html)**

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

The performance tests are shown below. The test code is in **src/Test.Java**. 

All operations are performed a 1,000 times and 50 trials unless otherwise noted.

All results are in milliseconds and are the averages.

## Access by Index

In this test, random indices were accessed 100,000 times for 50 trials.

It can be seen that the IgushArray maintains **O(1)** access time complexity, equivalent to ArrayList. However, there is a little extra overhead with the get operation and access times are longer, but still constant.

| List Size and Capacity | IgushArray | ArrayList |
| ---------------------- | ---------- | --------- |
| 1,000                  | 2.04       | 1.10      |
| 10,000                 | 1.78       | 0.94      |
| 100,000                | 1.96       | 0.96      |
| 1,000,000              | 2.02       | 0.96      |
| 10,000,000             | 2.68       | 0.96      |

## Insertion

Lists were initialized to a capacity of 10^k and initialized to a size of 10^k - 1000. The 1000 insertion/add operations performed then filled the lists to size 10^k.

It can be seen that for larger lists, IgushArray out performs the ArrayList by a massive amount.

| List Size and Capacity | IgushArray | ArrayList |
| ---------------------- | ---------- | --------- |
| 1,000                  | 0.60       | 0.12      |
| 10,000                 | 1.34       | 0.72      |
| 100,000                | 4.56       | 13.04     |
| 1,000,000              | 20.10      | 124.84    |
| 10,000,000             | 89.54      | 5790.44   |

## Remove Front

Each list was initialized to a size and capacity of 10^k. The 1000 remove front operations performed then reduced their sizes down to 10^k - 1000. In Java, removing the front is equivalent to `ArrayList.remove(0)`

It can be seen that for larger lists, IgushArray out performs the ArrayList by a massive amount.

| List Capacity | IgushArray | ArrayList |
| ------------- | ---------- | --------- |
| 1,000         | 0.52       | 0.06      |
| 10,000        | 1.12       | 0.76      |
| 100,000       | 4.12       | 10.68     |
| 1,000,000     | 22.04      | 123.42    |
| 10,000,000    | 93.82      | 3284.22   |

# Implementation

Please see [here](https://github.com/igushev/IgushArray#implementation) for an in depth explanation of how this is generally implemented.

For the Java version, the IgushArray is essentially an ArrayList of FixedDeques.

Each FixedDeque is a fixed length Deque (Double Ended Queue) implemented as a Circular/Ring Buffer. Additionally, to maintain **O(1)** access time complexity, the Circular/Ring Buffer is implemented with ArrayList, which works as a contiguous set of elements in memory.

# Todo

- Finish documentation of all methods, akin to ArrayList (JavaDocs)
- Finish rewrite of all ArrayList methods
- Finish stability test code to test all the IgushArray methods and logic and compare result to ArrayList
- Construct graphs based on the data to show performance comparisons