package com.axxes.googlehashcode;

import com.axxes.googlehashcode.model.*;

import java.nio.file.Paths;
import java.util.*;
import java.util.stream.*;

import static com.axxes.googlehashcode.util.Util.*;

public class LibraryApplication {
	public static final String a_filename = "src/main/resources/a_example.txt";
	public static final String b_filename = "src/main/resources/b_read_on.txt";
	private static final String a_output_file = "a_example_output";
	private static final String b_output_file = "b_read_on_output";
	private static final String newLine = "\n";

	public static void main(String[] args) {
		List<Library> librariesA = convert(a_filename);
		List<Library> librariesB = convert(b_filename);

		createOutput(a_output_file, librariesA);
		createOutput(b_output_file, librariesB);
	}

	private static List<Library> convert(String file) {
		final List<String> lines = readLines(Paths.get(file)
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
							  .map(Integer::parseInt)
							  .map(j -> {
								  Book book = new Book();
								  book.id = j;
								  book.score = books[j];
								  return book;
							  })
							  .sorted(new Comparator<Book>() {
								  @Override
								  public int compare(Book o1, Book o2) {
									  return Integer.compare(o2.score, o1.score);
								  }
							  })
							  .collect(Collectors.toList());

			libraries.add(lib);
			indexLib++;
		}
		return libraries;
	}

	public static void createOutput(String fileName, List<Library> libraries) {
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
