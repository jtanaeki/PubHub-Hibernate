package com.pubhub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="tag", uniqueConstraints=@UniqueConstraint(columnNames={"isbn_13"}))
public class Tag {

	@Id
	@Column(name="isbn_13", nullable=false, length=13)
	private String isbn13;			// International Standard Book Number, unique
	@Column(name="tag_name", nullable=true)
	private String tagName;

	public Tag(String isbn, String tagName) {
		this.isbn13 = isbn;
		this.tagName = tagName;
	}
	
	// Default constructor
	public Tag() {
		this.isbn13 = null;
		this.tagName = null;
	}
	
	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Override
	public String toString() {
		return "\nTag [isbn13=" + isbn13 + ", tagName=" + tagName + "]";
	}
	
}
