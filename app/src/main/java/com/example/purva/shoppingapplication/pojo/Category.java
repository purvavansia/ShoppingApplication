package com.example.purva.shoppingapplication.pojo;

/**
 * Created by purva on 4/11/18.
 */

public class Category {

    String name, image,desc,cid;

    public Category(String name, String image,String desc, String cid) {
        this.name = name;
        this.image = image;
        this.cid = cid;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String id) {
        this.desc = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
