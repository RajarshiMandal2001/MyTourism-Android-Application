package com.example.mytourism;

import android.widget.EditText;

public class Hotels {
    private static final String NULL_VALUE = "No value";
    private String mHotelPlaceStateName;
    private String mHotelPlaceName;
    private String mHotelName;
    private String mHotelBHK;
    private String mHotelCost;
    private String mHotelDescription;
    private String mHotelWebsiteLink;
    private String mImage1;
    private String mImage2;
    private String mImage3;
    private String mDate;
    private String mPushKey;
    private int isApproved;

    public Hotels(){
        //required
    }

    public Hotels(String mHotelPlaceStateName, String mHotelPlaceName, String mHotelName, String mHotelBHK, String mHotelCost, String mHotelDescription,String mHotelWebsiteLink,String mImage1, String mImage2, String mImage3,String mDate,String mPushKey,int isApproved) {
        if (mHotelPlaceStateName.trim().equals("")){
            mHotelPlaceStateName = "Delhi";
        }
        if (mHotelPlaceName.trim().equals("")){
            mHotelPlaceName = NULL_VALUE;
        }
        if (mHotelBHK.trim().equals("")){
            mHotelBHK = NULL_VALUE;
        }
        if (mHotelName.trim().equals("")){
            mHotelName = NULL_VALUE;
        }
        if (mHotelCost.trim().equals("")){
            mHotelCost = "0";
        }
        if(mHotelDescription.trim().equals("")){
            mHotelDescription = NULL_VALUE;
        }
        if (mHotelWebsiteLink.trim().equals("")){
            mHotelWebsiteLink = NULL_VALUE;
        }
        this.mHotelPlaceStateName = mHotelPlaceStateName;
        this.mHotelPlaceName = mHotelPlaceName;
        this.mHotelName = mHotelName;
        this.mHotelBHK = mHotelBHK;
        this.mHotelCost = mHotelCost;
        this.mHotelDescription = mHotelDescription;
        this.mHotelWebsiteLink = mHotelWebsiteLink;
        this.mImage1 = mImage1;
        this.mImage2 = mImage2;
        this.mImage3 = mImage3;
        this.mDate = mDate;
        this.mPushKey = mPushKey;
        this.isApproved = isApproved;
    }

    public String getmHotelPlaceStateName() {
        return mHotelPlaceStateName;
    }

    public void setmHotelPlaceStateName(String mHotelPlaceStateName) {
        this.mHotelPlaceStateName = mHotelPlaceStateName;
    }

    public String getmHotelPlaceName() {
        return mHotelPlaceName;
    }

    public void setmHotelPlaceName(String mHotelPlaceName) {
        this.mHotelPlaceName = mHotelPlaceName;
    }

    public String getmHotelName() {
        return mHotelName;
    }

    public void setmHotelName(String mHotelName) {
        this.mHotelName = mHotelName;
    }

    public String getmHotelBHK() {
        return mHotelBHK;
    }

    public void setmHotelBHK(String mHotelBHK) {
        this.mHotelBHK = mHotelBHK;
    }

    public String getmHotelCost() {
        return mHotelCost;
    }

    public void setmHotelCost(String mHotelCost) {
        this.mHotelCost = mHotelCost;
    }

    public String getmHotelDescription() {
        return mHotelDescription;
    }

    public void setmHotelDescription(String mHotelDescription) {
        this.mHotelDescription = mHotelDescription;
    }

    public String getmHotelWebsiteLink() {
        return mHotelWebsiteLink;
    }

    public void setmHotelWebsiteLink(String mHotelWebsiteLink) {
        this.mHotelWebsiteLink = mHotelWebsiteLink;
    }

    public String getmImage1() {
        return mImage1;
    }

    public void setmImage1(String mImage1) {
        this.mImage1 = mImage1;
    }

    public String getmImage2() {
        return mImage2;
    }

    public void setmImage2(String mImage2) {
        this.mImage2 = mImage2;
    }

    public String getmImage3() {
        return mImage3;
    }

    public void setmImage3(String mImage3) {
        this.mImage3 = mImage3;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmPushKey() {
        return mPushKey;
    }

    public void setmPushKey(String mPushKey) {
        this.mPushKey = mPushKey;
    }

    public int getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(int isApproved) {
        this.isApproved = isApproved;
    }
}
