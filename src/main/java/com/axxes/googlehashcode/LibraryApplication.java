package com.axxes.googlehashcode;

import com.axxes.googlehashcode.model.Library;

import java.nio.file.Paths;
import java.util.List;

import static com.axxes.googlehashcode.util.Util.readLines;

public class LibraryApplication {
	public static final String filename = "a_example";
	public static final String file = "src/main/resources/" + filename + ".txt";

	public static void main(String[] args) {
		final List<String> lines = readLines(Paths.get(file)
												  .toString(), 0);
		String[] firstLine = lines.get(0)
								  .split(" ");
		Library[] libraries = new Library[Integer.parseInt(firstLine[1])];
		int days = Integer.parseInt(firstLine[3]);
		String[] second = lines.get(1)
							   .split(" ");
		int books[] = new int[second.length];
		for (int i = 0; i < second.length; i++) {
			books[i] = Integer.parseInt(second[i]);
		}
		for (int i = 2; i < lines.size(); i++) {
			String[] libraryProperties = lines.get(i)
											  .split(" ");
			int amountOfBooks = Integer.parseInt(libraryProperties[0]);
			int processedDays = Integer.parseInt(libraryProperties[1]);
			int signupDays = Integer.parseInt(libraryProperties[2]);

			String second = lines.get(i + 1);
		}
	}
}
