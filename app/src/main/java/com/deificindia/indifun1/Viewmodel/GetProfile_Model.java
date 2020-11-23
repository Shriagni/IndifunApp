package com.deificindia.indifun1.Viewmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProfile_Model {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("profile_picture")
    @Expose
    private String profile_picture;

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getcontact() {
        return contact;
    }

    public void setcontact(String contact) {
        this.contact = contact;
    }

    public String getcountry() {
        return country;
    }

    public void setcountry(String country) {
        this.country = country;
    }

    public String getbirthday() {
        return birthday;
    }

    public void setbirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getage() {
        return age;
    }

    public void setage(String age) {
        this.age = age;
    }

    public String getprofile_picture() {
        return profile_picture;
    }

    public void setprofile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

}
