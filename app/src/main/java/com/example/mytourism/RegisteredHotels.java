package com.example.mytourism;

public class RegisteredHotels {
    private String mEmailAddress;
    private String mPassword;

    public RegisteredHotels(){
        //
    }

    public RegisteredHotels(String mEmailAddress, String mPassword) {
        this.mEmailAddress = mEmailAddress;
        this.mPassword = mPassword;
    }

    public String getmEmailAddress() {
        return mEmailAddress;
    }

    public void setmEmailAddress(String mEmailAddress) {
        this.mEmailAddress = mEmailAddress;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
