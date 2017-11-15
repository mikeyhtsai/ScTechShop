package com.sctech.sctechshop;

import android.net.Uri;

/**
 * Created by miketsai on 11/15/2017.
 */

public class Items {
    private String mName;
    private Double mPrice;
    private int mQty;
    private Uri mPicture;

    //instance of this class for inventory items
    public Items(String name, Double price, int qty, Uri pic) {
        mName = name;
        mPrice = price;
        mQty = qty;
        mPicture = pic;
    }

    public String getName() { return mName;}
    public Double getPrice() { return mPrice;}
    public int getQty() { return mQty;}
    public Uri getPicture() { return mPicture;}


}
