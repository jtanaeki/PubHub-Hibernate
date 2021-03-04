package com.pubhub.dao;

import java.util.List;

import com.pubhub.model.Purchased_Book;
import com.pubhub.model.Shopping_Cart;

/**
 * Interface for our Data Access Object to handle database queries related to Purchased_Book.
 */
public interface Purchased_BookDAO {

	public List<Purchased_Book> getHistory(String user);
	
	public boolean createHistory(String user);
	public boolean addBook(String user, Shopping_Cart item);
	public boolean deleteBook(String user, String isbn);
	public boolean clearHistory(String user);

}
