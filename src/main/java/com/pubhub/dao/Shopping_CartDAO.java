package com.pubhub.dao;

import java.util.List;

import com.pubhub.model.Book;
import com.pubhub.model.Shopping_Cart;

/**
 * Interface for our Data Access Object to handle database queries related to Shopping_Cart.
 */
public interface Shopping_CartDAO {

	public List<Shopping_Cart> getCart(String user);
	
	public boolean createCart(String user);
	public boolean addBook(String user, Book book);
	public boolean deleteBook(String user, String isbn);
	public boolean clearCart(String user);

}
