package com.example.mytourism;

public class Reviews {
    private String mPlaceName;
    private String mCustomerName;
    private String mReview;
    private int mRating;

    public Reviews(){
        //
    }

    public Reviews(String mPlaceName, String mCustomerName, String mReview, int mRating) {
        if (mPlaceName.trim().equals("")){
            mPlaceName = "Delhi";  //default place is delhi
        }
        if (mCustomerName.trim().equals("")){
            mCustomerName = "No name";
        }
        if (mReview.trim().equals("")){
            mReview = "No review";
        }
        if (mRating<1 || mRating>5){
            mRating = 1;
        }
        this.mPlaceName = mPlaceName;
        this.mCustomerName = mCustomerName;
        this.mReview = mReview;
        this.mRating = mRating;
    }

    public String getmPlaceName() {
        return mPlaceName;
    }

    public void setmPlaceName(String mPlaceName) {
        this.mPlaceName = mPlaceName;
    }

    public String getmCustomerName() {
        return mCustomerName;
    }

    public void setmCustomerName(String mCustomerName) {
        this.mCustomerName = mCustomerName;
    }

    public String getmReview() {
        return mReview;
    }

    public void setmReview(String mReview) {
        this.mReview = mReview;
    }

    public int getmRating() {
        return mRating;
    }

    public void setmRating(int mRating) {
        this.mRating = mRating;
    }
}
