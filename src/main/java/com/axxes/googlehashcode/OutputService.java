package com.axxes.googlehashcode;

import com.axxes.googlehashcode.util.Util;

import java.util.Collection;

public class OutputService {
	public void la(Collection<Library> libraries) {
		StringBuilder stringBuilder = new StringBuilder();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuilder.append(libraries.size() + "\n");

		libraries.forEach(library -> {
			stringBuffer.append(library.getId() + " " + library.getBooks()
															   .size() + "\n");
			library.getBooks()
				   .foreach(book -> {
					   stringBuffer.append(book.getId() + "\n");
				   });
		});

		Util.printLines();
	}
}
