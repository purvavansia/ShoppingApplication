package com.example.purva.shoppingapplication;

/**
 * Created by purva on 4/11/18.
 */

public class Category {

    String name, image,id,cid;

    public Category(String name, String image,String id, String cid) {
        this.name = name;
        this.image = image;
        this.cid = cid;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
