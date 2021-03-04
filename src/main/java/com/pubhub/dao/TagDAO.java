package com.pubhub.dao;

import java.util.List;

import com.pubhub.model.Book;
import com.pubhub.model.Tag;

/**
 * Interface for our Data Access Object to handle database queries related to Tag.
 */
public interface TagDAO {

	public List<Tag> getAllTags();
	public List<Tag> getTagsByTagName(String tagName);
	public List<Tag> getTagsByBook(Book book);
	public List<Book> getBooksByTagName(String tagName);
	public Tag getTagByISBN(String isbn);
	
	public boolean addTag(String tagName, Book book);
	public boolean updateTag(Tag tag);
	public boolean deleteTagByISBN(String isbn);
	public boolean deleteTagByName(String tagName, Book book);

}
