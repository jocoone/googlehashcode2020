package com.axxes.googlehashcode.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Library {
	public int id;
	public int bookPerDay;
	public int signUp;
	public List<Book> books;

    public int getDaysNeededToUploadLibrary() {
        return (this.books.size() / this.bookPerDay + this.signUp);
    }
}
