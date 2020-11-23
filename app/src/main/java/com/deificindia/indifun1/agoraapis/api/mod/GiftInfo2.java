package com.deificindia.indifun1.agoraapis.api.mod;

/*
 "id": "4",
            "gift_category": "1",
            "name": "gghgrrrff",
            "cover": "1603435590Penguins.jpg",
            "point": "1",
            "entry_date": "2020-09-25"

* */
public class GiftInfo2 {
    int id;
    String gift_category;
    String name;
    String cover;
    String json_animation;
    String point;
    String entry_date;

    public GiftInfo2() { }

    public GiftInfo2(int id, String gift_category, String name, String cover, String json_animation, String point, String entry_date) {
        this.id = id;
        this.gift_category = gift_category;
        this.name = name;
        this.cover = cover;
        this.json_animation = json_animation;
        this.point = point;
        this.entry_date = entry_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGift_category() {
        return gift_category;
    }

    public void setGift_category(String gift_category) {
        this.gift_category = gift_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public String getJson_animation() {
        return json_animation;
    }

    public void setJson_animation(String json_animation) {
        this.json_animation = json_animation;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }
}
