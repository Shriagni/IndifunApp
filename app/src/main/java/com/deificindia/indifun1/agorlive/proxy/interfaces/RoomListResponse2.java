package com.deificindia.indifun1.agorlive.proxy.interfaces;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
 "result": [
        {
            "id": "477",
            "full_name": "jameel",
            "age": "30",
            "gender": "Male",
            "profile_picture": "a95014e55946e51fc2440ff176a35cd4.png",
            "title": "124",
            "room_id": "129793763265486848",
            "broadcasting_type": "0"
        }
    ],
    "message": "success",
    "status": 1
* */
public class RoomListResponse2 {

    List<RoomList> result;
    String message;
    int status;

    public static class RoomList{
        @SerializedName("id")
        String user_id;
        String full_name;
        String age;
        String gender;
        String profile_picture;
        String title;
        String room_id;
        String broadcasting_type;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getProfile_picture() {
            return profile_picture;
        }

        public void setProfile_picture(String profile_picture) {
            this.profile_picture = profile_picture;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getBroadcasting_type() {
            return broadcasting_type;
        }

        public void setBroadcasting_type(String broadcasting_type) {
            this.broadcasting_type = broadcasting_type;
        }
    }

    public List<RoomList> getResult() {
        return result;
    }

    public void setResult(List<RoomList> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
