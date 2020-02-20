package com.axxes.googlehashcode.model;

import java.util.ArrayList;
import java.util.List;

public class Library {
	public int id;
	private int amountBooks;
	private int bookPerDay;
	private int signUp;
	public List<Integer> books;

	public Library() {
		books = new ArrayList<>();
	}

	public int getAmountBooks() {
		return amountBooks;
	}

	public Library setAmountBooks(int amountBooks) {
		this.amountBooks = amountBooks;
		return this;
	}

	public int getBookPerDay() {
		return bookPerDay;
	}

	public Library setBookPerDay(int bookPerDay) {
		this.bookPerDay = bookPerDay;
		return this;
	}

	public int getSignUp() {
		return signUp;
	}

	public Library setSignUp(int signUp) {
		this.signUp = signUp;
		return this;
	}
}
