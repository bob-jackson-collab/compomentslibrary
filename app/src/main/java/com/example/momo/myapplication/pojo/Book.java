package com.example.momo.myapplication.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/14
 *   desc: MyApplication
 * </pre>
 */
public class Book implements Parcelable {

    private double price;

    private String bookName;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.price);
        dest.writeString(this.bookName);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.price = in.readDouble();
        this.bookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
