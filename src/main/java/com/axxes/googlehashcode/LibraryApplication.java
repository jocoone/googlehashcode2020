package com.axxes.googlehashcode;

import com.axxes.googlehashcode.model.*;

import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
		convert(b_filename);
		convert(c_filename);
		convert(d_filename);
		convert(e_filename);
		convert(f_filename);
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
			int processedDays = Integer.parseInt(libraryProperties[1]);
			int signupDays = Integer.parseInt(libraryProperties[2]);

			Library lib = new Library();

			lib.bookPerDay = processedDays;
			lib.signUp = signupDays;
			lib.id = indexLib;
			lib.books = Stream.of(lines.get(i + 1)
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

			libraries.add(lib);
			indexLib++;
		}
		createOutput(file + "_out", libraries, days);
	}

	public static void createOutput(String fileName, List<Library> libraries, int totalDays) {
		libraries.sort((o1, o2) -> o2.getDaysNeededToUploadLibrary() - o1.getDaysNeededToUploadLibrary());
		final StringBuilder builder = new StringBuilder("" + libraries.size()).append(newLine);
		libraries.forEach(lib -> {
			builder.append(lib.id)
				   .append(" ")
				   .append(lib.books.size())
				   .append(newLine);
			lib.books.forEach(b -> builder.append(b.id)
										  .append(" "));
			builder.append(newLine);
		});
		writeString(fileName, builder.toString());
	}
}
