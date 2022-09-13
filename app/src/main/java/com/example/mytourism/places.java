package com.example.mytourism;

import android.util.Log;

public class places {
    private static final String NULL_VALUE = "No value";
    private String mPlaceStateName;
    private String mPlaceName;
    private String mCatagory;
    private String mGeneralInfo;
    private String mSpots;
    private int mNoOfSightsToExplore;
    private int mApproxCost;
    private String mDirection;
    private String mImage1;
    private String mImage2;
    private String mImage3;
    private String mDate;
    private String mPushKey;
    private int isApproved;

    public places(){
        //if not given error will bw thrown
    }


    public  places(String State, String Pname, String catagory,String GI, String spots,int NS, int cost, String direction, String img1, String img2, String img3,String date,String key,int isApproved){
        if(State.trim().equals("")){
            State = "Delhi";  //default value is Delhi
        }
        if (Pname.trim().equals("")){
            Pname = NULL_VALUE;
        }
        if (catagory.trim().equals("")){
            catagory = "Mountains";
        }
        if(GI.trim().equals("")){
            GI = NULL_VALUE;
        }
        if(spots.trim().equals("")){
            spots = NULL_VALUE;
        }
        if (direction.trim().equals("")){
            direction = NULL_VALUE;
        }
        mPlaceStateName = State;
        mPlaceName = Pname;
        mCatagory = catagory;
        mGeneralInfo = GI;
        mSpots = spots;
        mNoOfSightsToExplore = NS;
        mApproxCost = cost;
        mDirection = direction;
        mImage1 = img1;
        mImage2 = img2;
        mImage3 = img3;
        mDate = date;
        mPushKey = key;
        this.isApproved = isApproved;
    }

    public String getmPlaceStateName() {
        return mPlaceStateName;
    }

    public void setmPlaceStateName(String mPlaceStateName) {
        this.mPlaceStateName = mPlaceStateName;
    }

    public String getmPlaceName() {
        Log.d("inside",mPlaceName);
        return mPlaceName;
    }

    public void setmPlaceName(String mPlaceName) {
        this.mPlaceName = mPlaceName;
    }

    public String getmCatagory() {
        return mCatagory;
    }

    public void setmCatagory(String mCatagory) {
        this.mCatagory = mCatagory;
    }

    public String getmGeneralInfo() {
        return mGeneralInfo;
    }

    public void setmGeneralInfo(String mGeneralInfo) {
        this.mGeneralInfo = mGeneralInfo;
    }

    public int getmNoOfSightsToExplore() {
        return mNoOfSightsToExplore;
    }

    public void setmNoOfSightsToExplore(int mNoOfSightsToExplore) {
        this.mNoOfSightsToExplore = mNoOfSightsToExplore;
    }

    public String getmSpots() {
        return mSpots;
    }

    public void setmSpots(String mSpots) {
        this.mSpots = mSpots;
    }

    public int getmApproxCost() {
        return mApproxCost;
    }

    public void setmApproxCost(int mApproxCost) {
        this.mApproxCost = mApproxCost;
    }

    public String getmDirection() {
        return mDirection;
    }

    public void setmDirection(String mDirection) {
        this.mDirection = mDirection;
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
