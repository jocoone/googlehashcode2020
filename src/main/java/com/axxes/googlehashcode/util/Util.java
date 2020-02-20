package com.axxes.googlehashcode.util;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {
    public static List<String> readLines(final String fileName) {
        return readLines(fileName, 0);
    }

    public static List<String> readLines(final String fileName, final int from) {
        return readLinesAsStream(fileName).skip(from).collect(Collectors.toList());
    }

    public static String readLine(final String fileName, final int lineNumber) {
        return readLines(fileName).get(lineNumber);
    }

    public static Stream<String> readLinesAsStream(final String fileName) {
        try {
            return Files.readAllLines(Path.of(URI.create("file://" + Paths.get(fileName).toAbsolutePath()))).stream();
        } catch (IOException e) {
            throw new ImpossibleFileException(e);
        }
    }

    public static void writeString(final String fileName, final String lines) {
        try {
            final Path out = Paths.get("out");
            final Path file = Paths.get( "out/" + fileName);
            if (!Files.exists(out)) {
                Files.createDirectory(out);
            }
            if (!Files.exists(file)) {
                Files.createFile(file);
            }
            Files.write(file,lines.getBytes());
            System.out.println("Written: " + fileName);
        } catch (IOException e) {
            throw new ImpossibleFileException(e);
        }
    }

    public static void writeLines(final String fileName, final List<String> lines) {
        final StringBuilder builder = new StringBuilder();
        lines.forEach(x -> builder.append(x).append("\n"));
        writeString(fileName, builder.toString());
    }

    public static void printLines(final List<String> lines) {
        System.out.println("Printed lines:");
        lines.forEach(System.out::println);
        System.out.println("--------------------------");
    }

    public static void printFile(final String fileName) {
        printLines(readLines(fileName));
    }

    public static <T> List<List<T>> combination(List<T> values, int size) {

        if (0 == size) {
            return Collections.singletonList(Collections.<T> emptyList());
        }

        if (values.isEmpty()) {
            return Collections.emptyList();
        }

        List<List<T>> combination = new LinkedList<>();

        T actual = values.iterator().next();

        List<T> subSet = new LinkedList<T>(values);
        subSet.remove(actual);

        List<List<T>> subSetCombination = combination(subSet, size - 1);

        for (List<T> set : subSetCombination) {
            List<T> newSet = new LinkedList<T>(set);
            newSet.add(0, actual);
            combination.add(newSet);
        }

        combination.addAll(combination(subSet, size));

        return combination;
    }
}
