package com.axxes.googlehashcode.model;

public class Library {
	private int amountBooks;
	private int bookPerDay;
	private int signUp;

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
