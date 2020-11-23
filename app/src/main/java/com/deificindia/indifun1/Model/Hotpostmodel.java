
package com.deificindia.indifun1.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hotpostmodel {

    /*
    id": "2",
            "image": [
                {
                    "id": "1",
                    "post_id": "2",
                    "image": "11.jpeg",
                    "post_by": "12"
                }
            ],
            "content": "dgdh",
            "entry_date": "2020-09-29",
            "time": "04:05:45 PM",
            "total_likes": "3",
            "total_comments": "3",
            "post_by": "12",
            "user_name": "priya",
            "is_likes": 0
        },
    * */

    List<MyResult> result;
    String message;
    int status;

    public class MyResult{
        String id;
        List<MyImages> image;
        String content;
        String entry_date;
        String time;
        String total_likes;
        String total_comments;
        String post_by;
        String user_name;
        int is_likes;
        String age;
        String gender;

        public class MyImages{
            String id;
            String post_id;
            String image;
            String post_by;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPost_id() {
                return post_id;
            }

            public void setPost_id(String post_id) {
                this.post_id = post_id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getPost_by() {
                return post_by;
            }

            public void setPost_by(String post_by) {
                this.post_by = post_by;
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<MyImages> getImage() {
            return image;
        }

        public void setImage(List<MyImages> image) {
            this.image = image;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getEntry_date() {
            return entry_date;
        }

        public void setEntry_date(String entry_date) {
            this.entry_date = entry_date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTotal_likes() {
            return total_likes;
        }

        public void setTotal_likes(String total_likes) {
            this.total_likes = total_likes;
        }

        public void updateTotal_Likes(){
            int tlikes = Integer.parseInt(this.total_likes);
            this.total_likes= String.valueOf(tlikes+1);
        }

        public String getTotal_comments() {
            return total_comments;
        }

        public void setTotal_comments(String total_comments) {
            this.total_comments = total_comments;
        }

        public void updateTotal_comments(){
            int tlikes = Integer.parseInt(this.total_comments);
            this.total_comments= String.valueOf(tlikes+1);
        }

        public String getPost_by() {
            return post_by;
        }

        public void setPost_by(String post_by) {
            this.post_by = post_by;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public int getIs_likes() {
            return is_likes;
        }

        public void setIs_likes(int is_likes) {
            this.is_likes = is_likes;
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
    }



    public List<MyResult> getResult() {
        return result;
    }

    public void setResult(List<MyResult> result) {
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
