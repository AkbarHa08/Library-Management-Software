package com.dao;

import com.model.Books;
import com.model.Users;
import com.page.PageController;
import com.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DaoImpl implements Dao{

    @Override
    public boolean createAccount(Users users) {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "insert into library.users(name,surname,username,password,mail,address)\n" +
"values('"+users.getName()+"','"+users.getSurname()+"','"+users.getUsername()+"','"+users.getPassword()+"',"
                + "'"+users.getEmail()+"','"+users.getAddress()+"')";
        c = DBhelper.GetConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                ps.execute();
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, null);
            }  
        } else {
            System.err.println("Not Connection!");
        }
        return result;
    }

    @Override
    public boolean checkUserByUsername(String username) {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from library.users\n" +
"where name='"+username+"'";
        c = DBhelper.GetConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, rs);
            }
        }else{
            System.err.println("Not Connection!");
        }
        
        return result;
    }

    @Override
    public Users checkUser(String username, String password) {
        Users u = new Users();
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select id,name,surname,username,password,mail,address from library.users\n" +
"where username='"+username+"' and password='"+password+"'";
        c = DBhelper.GetConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    u.setId(rs.getInt("id"));
                    u.setName(rs.getString("name"));
                    u.setSurname(rs.getString("surname"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setEmail(rs.getString("mail"));
                    u.setAddress(rs.getString("address"));
                } else{
                    u = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, rs);
            }
        }else{
            System.err.println("Not Connection!");
        }
        
        return u;
    }

    @Override
    public List<Books> getAllBooks() {
        List<Books> allBooks = new ArrayList<Books>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "Select id,name,thema,author,page_count,amount,language,status From library.books;";
        c = DBhelper.GetConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    Books books = new Books();
                    books.setId(rs.getInt("id"));
                    books.setName(rs.getString("name"));
                    books.setThema(rs.getString("thema"));
                    books.setAuthor(rs.getString("author"));
                    books.setPageCount(rs.getInt("page_count"));
                    books.setAmount(rs.getDouble("amount"));
                    books.setLanguage(rs.getString("language"));
                    books.setStatus(rs.getString("status"));
                    allBooks.add(books);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                Util.closeAll(c, ps, rs);
            }
        } else {
            System.err.println("Not Connection");
        }
        return allBooks;
    }

    @Override
    public boolean addBook(Books newBook) {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "insert into library.books(name,thema,author,page_count,amount,language)\n" +
"values('"+newBook.getName()+"','"+newBook.getThema()+"','"+newBook.getAuthor()+"',"+newBook.getPageCount()+","+newBook.getAmount()+",'"+newBook.getLanguage()+"')";
        c = DBhelper.GetConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                ps.execute();
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                Util.closeAll(c, ps, null);
            }
        } else {
            System.err.println("Not Connection!");
        }
        
        return result;
    }

    @Override
    public boolean updateBook(Books books) {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "update library.books\n" +
"set name = '"+books.getName()+"',thema = '"+books.getThema()+"',author = '"+books.getAuthor()+
                "',page_count = "+books.getPageCount()+",amount = "+books.getAmount()+",language = '"+books.getLanguage()+"'\n" +
"where id="+books.getId()+"";
        c = DBhelper.GetConnection();
        if (c != null) {
            try {
            ps = c.prepareStatement(sql);
            ps.execute();
            result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                Util.closeAll(c, ps, null);
            }
            
        } else {
            System.err.println("Not Connection!");
        }
        return result;
    }

    @Override
    public List<Books> searchBook(String keyword) {
        List<Books> resultBook = new ArrayList<Books>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select id,name,thema,author,page_count,amount,language,status from library.books\n" +
"where name like('%"+keyword+"%') or thema like('%"+keyword+"%')"
                + " or author like('%"+keyword+"%') or page_count like('%"+keyword+"%') or amount like('%"+keyword+"%')\n" +
"or language like('%"+keyword+"%') or status like('%"+keyword+"%')";
        c = DBhelper.GetConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    Books book = new Books();
                    book.setId(rs.getInt("id"));
                    book.setName(rs.getString("name"));
                    book.setThema(rs.getString("thema"));
                    book.setAuthor(rs.getString("author"));
                    book.setPageCount(rs.getInt("page_count"));
                    book.setAmount(rs.getDouble("amount"));
                    book.setLanguage(rs.getString("language"));
                    book.setStatus(rs.getString("status"));
                    resultBook.add(book);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, rs);
            }
        } else {
            System.err.println("Not Connection!");
        }
        
        return resultBook;
    }

    @Override
    public List<Books> filterByAmount(double minAmount, double maxAmount) {
        List<Books> resultBook = new ArrayList<Books>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select id,name,thema,author,page_count,amount,language,status from library.books\n" +
"where amount between "+minAmount+" and "+maxAmount+"";
        c = DBhelper.GetConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    Books book = new Books();
                    book.setId(rs.getInt("id"));
                    book.setName(rs.getString("name"));
                    book.setThema(rs.getString("thema"));
                    book.setAuthor(rs.getString("author"));
                    book.setPageCount(rs.getInt("page_count"));
                    book.setAmount(rs.getDouble("amount"));
                    book.setLanguage(rs.getString("language"));
                    book.setStatus(rs.getString("status"));
                    resultBook.add(book);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, rs);
            }
        } else {
            System.err.println("Not Connection!");
        }
        
        return resultBook;
    }

    @Override
    public List<Books> filterByPageCount(double minPageCount, double maxPageCount) {
        List<Books> resultBook = new ArrayList<Books>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select id,name,thema,author,page_count,amount,language,status from library.books\n" +
"where page_count between "+minPageCount+" and "+maxPageCount+"";
        c = DBhelper.GetConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    Books book = new Books();
                    book.setId(rs.getInt("id"));
                    book.setName(rs.getString("name"));
                    book.setThema(rs.getString("thema"));
                    book.setAuthor(rs.getString("author"));
                    book.setPageCount(rs.getInt("page_count"));
                    book.setAmount(rs.getDouble("amount"));
                    book.setLanguage(rs.getString("language"));
                    book.setStatus(rs.getString("status"));
                    resultBook.add(book);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, rs);
            }
        } else {
            System.err.println("Not Connection!");
        }
        
        return resultBook;
    }

    @Override
    public boolean buyBook(int selectedId) {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "update library.books\n" +
"set status = 'SOLD'\n" +
"where id = "+selectedId+"";
        c = DBhelper.GetConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                ps.execute();
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, null);
            }
        } else {
            System.err.println("Not Connection!");
        }
        return result;
    }

    @Override
    public boolean deleteBook(int selectedId) {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "delete from library.books\n" +
"where id = "+selectedId+"";
        c = DBhelper.GetConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                ps.execute();
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                Util.closeAll(c, ps, null);
            }
        } else {
            System.err.println("Not Connection!");
        }
        
        return result;
    }   

    @Override
    public List<Books> filterByStatus(String status) {
        List<Books> resultList = new ArrayList<Books>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select id,name,thema,author,page_count,amount,language,status from library.books\n" +
"where status='"+status+"'";
        c = DBhelper.GetConnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    Books books = new Books();
                    books.setId(rs.getInt("id"));
                    books.setName(rs.getString("name"));
                    books.setThema(rs.getString("thema"));
                    books.setAuthor(rs.getString("author"));
                    books.setPageCount(rs.getInt("page_count"));
                    books.setAmount(rs.getDouble("amount"));
                    books.setLanguage(rs.getString("language"));
                    books.setStatus(rs.getString("status"));
                    resultList.add(books);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Util.closeAll(c, ps, rs);
            }
        } else {
            System.err.println("Not Connection!");
        }
        
        
        return resultList;
    }
}