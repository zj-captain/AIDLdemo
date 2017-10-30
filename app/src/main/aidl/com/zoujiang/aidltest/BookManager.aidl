// BookManager.aidl
package com.zoujiang.aidltest;

// Declare any non-default types here with import statements
import com.zoujiang.aidltest.Book;

interface BookManager {
    List<Book> getBooks();
    void addBook(in Book book);
}
