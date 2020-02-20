package com.axxes.googlehashcode;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.axxes.googlehashcode.util.CustomRunnable;

import static com.axxes.googlehashcode.util.Util.*;

public class PracticeRound {

    public static class Pizza {
        private final int type;
        private final int numSlices;

        public Pizza(int type, int numSlices) {
            this.type = type;
            this.numSlices = numSlices;
        }

        public int getNumSlices() {
            return numSlices;
        }

        @Override
        public String toString() {
            return "Pizza #" + type + " has " + numSlices + " slices.";
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Pizza pizza = (Pizza) o;
            return numSlices == pizza.numSlices;
        }

        @Override
        public int hashCode() {
            return Objects.hash(numSlices);
        }
    }

    public static void main(String[] args) {
        doWork("a_example");
        doWork("b_small");
        doWork("c_medium");
        //doWork("d_quite_big");
        //doWork("e_also_big");
    }

    private static void doWork(final String fileName) {
        new Thread(new CustomRunnable(fileName + ".out", () -> {
            final String file = "src/main/resources/practice/" + fileName + ".in";
            final int maxNumOfSlices = Integer.parseInt(readLine(file, 0).split(" ")[0]);
            final List<String> lines = readLines(file, 1);
            return calculatePizzaCombinations(lines, maxNumOfSlices);
        })).start();
    }

    private static String calculatePizzaCombinations(final List<String> lines, final int maxNumberOfSlices) {
        final List<List<Integer>> collect = lines.stream()
                .map(line -> Stream.of(line.split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        final List<Pizza> pizzas = collect.stream()
                .map(x -> {
                    final AtomicInteger i = new AtomicInteger(0);
                    return x.stream().map(y -> new Pizza(i.getAndIncrement(), y)).collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
        final List<List<Pizza>> combinations = getAllPizzaCombinations(pizzas);

        final List<Pizza> optimalPizzaCombination = combinations.stream()
                .filter(l -> l.stream().map(Pizza::getNumSlices).reduce(0, Integer::sum) <= maxNumberOfSlices)
                .max(Comparator.comparingInt(o -> o.stream().map(Pizza::getNumSlices).reduce(0, Integer::sum)))
                .orElse(null);

        System.out.println("Maximum slices of pizza: " + maxNumberOfSlices);

        final StringBuilder builder = new StringBuilder().append(optimalPizzaCombination.size()).append("\n");
        optimalPizzaCombination.forEach(x -> builder.append(x.type).append(" "));
        return builder.toString();
    }

    private static List<List<Pizza>> getAllPizzaCombinations(final List<Pizza> pizzas) {
        final List<List<Pizza>> combinations = new ArrayList<>();
        for (int i = 1; i < pizzas.size(); i++) {
            combinations.addAll(combination(pizzas, i));
        }
        return combinations;
    }
}
