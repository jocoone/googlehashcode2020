package com.axxes.googlehashcode.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Library {
	public int id;
	public int bookPerDay;
	public int signUp;
	public List<Book> books;
	public List<Integer> pointsPerDay;
    public String processedBooks;
    public boolean signed = false;

    public int getDaysNeededToUploadLibrary() {
        return (this.books.size() / this.bookPerDay + this.signUp);
    }

    public int getMagicValue() {
        return pointsPerDay.stream().reduce(0, Integer::sum) / (pointsPerDay.size() + signUp);
    }

    public int getFirstPointsPerDay() {
        return pointsPerDay.isEmpty() ? 0 : pointsPerDay.get(0);
    }
}
