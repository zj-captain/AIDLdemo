package com.zoujiang.aidltest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BookManager mBookManager = null;
    private List<Book> bookList;
    private boolean isConnection;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            isConnection = true;
            mBookManager = BookManager.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isConnection = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setAction("com.zoujiang.aidltest");
        intent.setPackage("com.zoujiang.aidltest");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void addBook(View view) {
        Book book = new Book();
        book.setName("邹江的Android大神之路");
        book.setPrice(96);
        if (mBookManager == null) return;
        try {
            mBookManager.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getBooks(View view) {
        if (mBookManager == null) return;
        try {
            bookList = mBookManager.getBooks();
            for (Book book : bookList) {
                Log.i("zoujiang", "书名：" + book.getName() + " 价格：" + book.getPrice() + "\n");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isConnection) {
            unbindService(serviceConnection);
            isConnection = false;
        }
    }
}
