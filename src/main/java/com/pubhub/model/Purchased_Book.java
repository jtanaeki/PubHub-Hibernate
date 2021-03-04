package com.pubhub.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="purchased_book", uniqueConstraints=@UniqueConstraint(columnNames={"pb_id"}))
public class Purchased_Book {

	@Id
	@Column(name="pb_id", nullable=false)
	private int id;
	@Column(name="purchase_date", nullable=true)
	private LocalDate purchaseDate;
	@Column(name="isbn_13", nullable=false, unique=true, length=13)
	private String isbn13;			// International Standard Book Number, unique
	@Column(name="title", nullable=true)
	private String title;
	@Column(name="author", nullable=true)
	private String author;
	@Column(name="publish_date", nullable=true)
	private LocalDate publishDate;	// Date of publish to the website
	
	@Column(name="price", nullable=true)
	private double price;
	
	@Column(name="content", nullable=true)
	private byte[] content;
	
	@Column(name="tag_name", nullable=true)		
	private String tagName;

	// Constructor used when no date is specified
	public Purchased_Book(String isbn, String title, String author, double price, byte[] content) {
		this.purchaseDate = LocalDate.now();
		this.isbn13 = isbn;
		this.title = title;
		this.author = author;
		this.price = price;
		this.publishDate = LocalDate.now();
		this.content = content;
	}
	
	// Constructor used when dates are specified
	public Purchased_Book(LocalDate purchaseDate, String isbn, String title, String author, double price, byte[] content, LocalDate publishDate) {
		this.purchaseDate = purchaseDate;
		this.isbn13 = isbn;
		this.title = title;
		this.author = author;
		this.price = price;
		this.publishDate = publishDate;
		this.content = content;
	}
	
	// Default constructor
	public Purchased_Book() {
		this.purchaseDate = LocalDate.now();
		this.isbn13 = null;
		this.title = null;
		this.author = null;
		this.price = 0.0;
		this.publishDate = LocalDate.now();
		this.content = null;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public LocalDate getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Override
	public String toString() {
		return "\nPurchased_Book [id=" + id + ", purchaseDate=" + purchaseDate + ", isbn13=" + isbn13 + 
				", title=" + title + ", author=" + author + ", publishDate=" + publishDate + 
				", price=" + price + ", content=" + content + ", tagName=" + tagName + "]";
	}
	
}
