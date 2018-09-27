// BookHandler.aidl
package com.example.momo.myapplication;

// Declare any non-default types here with import statements
//import com.example.momo.myapplication.pojo.Book;
interface BookHandler {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

//    void addBook(Book book);
//
//    List<Book> getBooks();
//
//    void removeBook(Book book);
}
