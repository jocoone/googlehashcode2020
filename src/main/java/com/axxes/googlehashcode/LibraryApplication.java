package com.axxes.googlehashcode;

import com.axxes.googlehashcode.model.*;

import java.nio.file.Paths;
import java.util.*;
import java.util.stream.*;

import static com.axxes.googlehashcode.util.Util.*;

public class LibraryApplication {
	public static final String a_filename = "a_example";
	public static final String b_filename = "b_read_on";
	public static final String c_filename = "c_incunabula";
	public static final String d_filename = "d_tough_choices";
	public static final String e_filename = "e_so_many_books";
	public static final String f_filename = "f_libraries_of_the_world";
	private static final String newLine = "\n";

	public static void main(String[] args) {
		convert(a_filename);
		//convert(b_filename);
		convert(c_filename);
		/*convert(d_filename);
		convert(e_filename);
		convert(f_filename);*/
	}

	private static void convert(String file) {
		final List<String> lines = readLines(Paths.get("src/main/resources/" + file + ".txt")
												  .toString(), 0);
		String[] firstLine = lines.get(0)
								  .split(" ");
		List<Library> libraries = new ArrayList<>();
		int days = Integer.parseInt(firstLine[2]);
		String[] second = lines.get(1)
							   .split(" ");
		int books[] = new int[second.length];
		for (int i = 0; i < second.length; i++) {
			books[i] = Integer.parseInt(second[i]);
		}
		int indexLib = 0;
		for (int i = 2; i < lines.size() - 1; i += 2) {
			String[] libraryProperties = lines.get(i)
											  .split(" ");
			int processedDays = Integer.parseInt(libraryProperties[2]);
			int signupDays = Integer.parseInt(libraryProperties[1]);

			Library lib = new Library();

			lib.bookPerDay = processedDays;
			lib.signUp = signupDays;
			lib.id = indexLib;
			lib.processedBooks = "";
			lib.books = getAllBooks(lines, books, i);
			lib.pointsPerDay = calculatePointsPerDay(lib);
			libraries.add(lib);
			indexLib++;
		}

		libraries.sort((o1, o2) -> o2.getFirstPointsPerDay() - o1.getFirstPointsPerDay());

		final Set<Integer> allProcessedBooks = new HashSet<>();
		final List<Integer> processedLibraries = new ArrayList<>();
		Library lib = libraries.get(0);
		int signDays = lib.signUp;
		for (int day = 0; day < days; day++) {
			libraries.forEach(library -> {
				if (library.signed) {
					processLibrary(library, allProcessedBooks);
				}
			});
			signDays--;
			if (signDays == 0) {
				lib.signed = true;
				processedLibraries.add(lib.id);
				final Optional<Library> first = libraries.stream().filter(l -> !l.signed).findFirst();
				if (first.isPresent()) {
					lib = first.get();
					signDays = lib.signUp;
				}
			}
			libraries.forEach(l -> {
				l.books = l.books.stream().filter(b -> !allProcessedBooks.contains(b.id)).collect(Collectors.toList());
				l.pointsPerDay = calculatePointsPerDay(l);
			});
			libraries.sort((o1, o2) -> o2.getFirstPointsPerDay() - o1.getFirstPointsPerDay());
		}

		createOutput(file + "_out", processedLibraries.stream().map(id -> libraries.stream().filter(l -> l.id == id).findFirst().get()).collect(Collectors.toList()), days);
	}

	private static void processLibrary(final Library lib, final Set<Integer> allProcessedBooks) {
		if (!lib.books.isEmpty()) {
			final List<Book> processedbooks = lib.books.stream().limit(lib.bookPerDay).collect(Collectors.toList());
			lib.books.removeAll(processedbooks);
			lib.processedBooks += processedbooks.stream().map(x -> x.id.toString()).reduce("", (a,b) -> a + " " + b);
			allProcessedBooks.addAll(processedbooks.stream().map(b -> b.id).collect(Collectors.toList()));
		}
	}

	private static List<Integer> calculatePointsPerDay(final Library lib) {
		final List<Integer> collect = new ArrayList<>();
		for (int y = 0; y < lib.books.size(); y+= lib.bookPerDay) {
			collect.add(lib.books.stream().map(x -> x.score).skip(collect.size() * lib.bookPerDay).limit(lib.bookPerDay).reduce(0, Integer::sum));
		}
		return collect;
	}

	private static List<Book> getAllBooks(final List<String> lines, final int[] books, final int i) {
		return Stream.of(lines.get(i + 1)
								   .split(" "))
						  .distinct()
						  .map(Integer::parseInt)
						  .map(j -> {
							  Book book = new Book();
							  book.id = j;
							  book.score = books[j];
							  return book;
						  })
						  .sorted((o1, o2) -> Integer.compare(o2.score, o1.score))
						  .collect(Collectors.toList());
	}

	public static void createOutput(String fileName, List<Library> libraries, int totalDays) {
		// libraries.sort((o1, o2) -> o2.getMagicValue() - o1.getMagicValue());
		final StringBuilder builder = new StringBuilder("" + libraries.size()).append(newLine);
		libraries.forEach(lib -> {
			builder.append(lib.id)
				   .append(" ")
				   .append(lib.processedBooks.split(" ").length)
				   .append(newLine);
			builder.append(lib.processedBooks).append("\n");
		});
		writeString(fileName, builder.toString());
	}
}
