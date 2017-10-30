package com.zoujiang.aidltest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.zoujiang.aidltest.Book;
import com.zoujiang.aidltest.BookManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zj on 2017/10/30.
 */

public class AIDLService extends Service {

    private List<Book> bookList = new ArrayList<>();

    private BookManager.Stub mBookManager = new BookManager.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            synchronized (this) {
                if (bookList != null) {
                    return bookList;
                }
                return new ArrayList<>();
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (bookList == null) {
                bookList = new ArrayList<>();
            }
            if (!bookList.contains(book)) {
                bookList.add(book);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Book book = new Book();
        book.setName("Android艺术开发探索");
        book.setPrice(26);
        bookList.add(book);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBookManager;
    }
}
