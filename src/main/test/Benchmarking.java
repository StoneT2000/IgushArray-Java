/**
 * MIT License
 *
 * Copyright (c) 2020 StoneT2000 (Stone Tao) email <stonezt2019@gmail.com>
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

package test;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import stonet2000.igusharray.IgushArray;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import java.util.Random;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(value = 1)

public class Benchmarking {

    static final int SEED = 31415926; // must be large
    @Param({"10000000"})
    public int accessTimes;
    @Param({"1000"})
    public int removalTimes;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Benchmarking.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }

    /**
     * Measure pushing to end for IgushArray
     */
    @Benchmark
    public void IgushArrayPushEnd(IgushArrayStateVariableDensity state) {
        for (int i = state.spaceLeft; --i >= 0; ) {
            state.igushArray.add(0);
        }
    }

    /**
     * Measure pushing to end for ArrayList
     */
    @Benchmark
    public void ArrayListPushEnd(ArrayListStateVariableDensity state) {
        for (int i = 0; i < state.spaceLeft; i++) {
            state.arrayList.add(0);
        }
    }

    /**
     * Measure pushing to front (unshift) for IgushArray
     */
    @Benchmark
    public void IgushArrayPushFront(IgushArrayStateVariableDensity state) {
        for (int i = state.spaceLeft; --i >= 0; ) {
            state.igushArray.add(0, 0);
        }
    }

    /**
     * Measure pushing to front (unshift) for IgushArray
     */
    @Benchmark
    public void ArrayListPushFront(ArrayListStateVariableDensity state) {
        for (int i = state.spaceLeft; --i >= 0; ) {
            state.arrayList.add(0, 0);
        }
    }

    /**
     * Measure removing front of IgushArray
     */
    @Benchmark
    public void IgushArrayRemoveFront(IgushArrayStateFull state) {
        for (int i = 0; i < removalTimes; i++) {
            state.igushArray.remove(0);
        }
    }

    /**
     * Measure removing front of ArrayList
     */
    @Benchmark
    public void ArrayListRemoveFront(ArrayListStateFull state) {
        for (int i = 0; i < removalTimes; i++) {
            state.arrayList.remove(0);
        }
    }

    /**
     * Measure random access for IgushArray
     */
    @Benchmark
    public void IgushArrayRandomAccess(IgushArrayStateFull state, Blackhole bh) {
        for (int i = accessTimes; --i >= 0; ) {
            bh.consume(state.igushArray.get(0));
        }
    }

    /**
     * Measure random access for ArrayList
     */
    @Benchmark
    public void ArrayListRandomAccess(ArrayListStateFull state, Blackhole bh) {
        for (int i = accessTimes; --i >= 0; ) {
            bh.consume(state.arrayList.get(0));
        }
    }

    @State(Scope.Benchmark)
    /* Parameters to allow for varying density of list */
    public static class VaryDensityState {
        @Param({"10", "100", "1000"})
        public int spaceLeft;

        @Param({"1000", "10000", "100000", "1000000", "10000000"})
        public int listSize;

        private Random sourceRand = new Random(SEED);
        public Random rand = new Random(sourceRand.nextInt(SEED));

        @Setup(Level.Iteration)
        public void changeRandom() {
            rand = new Random(SEED);
        }
    }

    @State(Scope.Benchmark)
    /* Parameters for just a completely filled in array */
    public static class FullDensityState {
        @Param({"1000", "10000", "100000", "1000000"})
        public int listSize;

        private Random sourceRand = new Random(SEED);
        public Random rand = new Random(sourceRand.nextInt(SEED));

        @Setup(Level.Iteration)
        public void changeRandom() {
            rand = new Random(sourceRand.nextInt(SEED));
        }
    }

    @State(Scope.Benchmark)
    public static class IgushArrayStateVariableDensity extends VaryDensityState {
        List<Integer> igushArray;

        @Setup(Level.Invocation)
        public void doSetup() {
            int capacity = listSize + spaceLeft;
            igushArray = new IgushArray<>(capacity);

            // fill up list until there is spaceLeft space
            for (int i = 0; i < listSize; i++) {
                igushArray.add(-1);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class ArrayListStateVariableDensity extends VaryDensityState {
        List<Integer> arrayList;

        @Setup(Level.Invocation)
        public void doSetup() {
            int capacity = listSize + spaceLeft;
            arrayList = new ArrayList<>(capacity);

            // fill up list until there is spaceLeft space
            for (int i = 0; i < listSize; i++) {
                arrayList.add(-1);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class ArrayListStateFull extends FullDensityState {
        List<Integer> arrayList;

        @Setup(Level.Invocation)
        public void doSetup() {
            arrayList = new ArrayList<>(listSize);

            // fill up list completely
            for (int i = 0; i < listSize; i++) {
                arrayList.add(-1);
            }
        }
    }

    @State(Scope.Benchmark)
    public static class IgushArrayStateFull extends FullDensityState {
        List<Integer> igushArray;

        @Setup(Level.Invocation)
        public void doSetup() {
            igushArray = new IgushArray<>(listSize);

            // fill up list completely
            for (int i = 0; i < listSize; i++) {
                igushArray.add(-1);
            }
        }
    }
}
