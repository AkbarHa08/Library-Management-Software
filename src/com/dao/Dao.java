package com.dao;

import com.model.Books;
import com.model.Users;
import java.util.List;

public interface Dao {
    public boolean createAccount(Users users);
    
    public boolean checkUserByUsername(String username);
    
    public Users checkUser(String username,String password);
    
    public List<Books> getAllBooks();
    
    public boolean addBook(Books newBook);
    
    public boolean updateBook(Books books);
    
    public List<Books> searchBook(String keyword);
    
    public List<Books> filterByAmount(double minAmount,double maxAmount);
    
    public List<Books> filterByPageCount(double minPageCount,double maxPageCount);
    
    public boolean buyBook(int selectedId);
    
    public boolean deleteBook(int selectedId);
    
    public List<Books> filterByStatus(String status);
}