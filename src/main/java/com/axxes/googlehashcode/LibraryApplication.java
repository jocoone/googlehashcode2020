package com.axxes.googlehashcode;

import com.axxes.googlehashcode.model.Library;

import java.nio.file.Paths;
import java.util.*;
import java.util.stream.*;

import static com.axxes.googlehashcode.util.Util.*;

public class LibraryApplication {
	//	public static final String filename = "a_example";
	public static final String filename = "b_read_on";
	public static final String file = "src/main/resources/" + filename + ".txt";
	//	private static final String output_file = "a_example_output";
	private static final String output_file = "b_read_on_output";
	private static final String newLine = "\n";

	public static void main(String[] args) {
		List<Library> libraries = convert();

		createOutput(output_file, libraries);
	}

	private static List<Library> convert() {
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
			int amountOfBooks = Integer.parseInt(libraryProperties[0]);
			int processedDays = Integer.parseInt(libraryProperties[1]);
			int signupDays = Integer.parseInt(libraryProperties[2]);

			Library lib = new Library();

			lib.bookPerDay = processedDays;
			lib.signUp = signupDays;
			lib.id = indexLib;
			lib.books = Stream.of(lines.get(i + 1)
									   .split(" "))
							  .map(Integer::parseInt)
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
			lib.books.forEach(b -> builder.append(b)
										  .append(" "));
			builder.append(newLine);
		});
		writeString(fileName, builder.toString());
	}
}
