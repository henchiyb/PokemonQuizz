package com.example.nhan.hack2pokemon.models;

/**
 * Created by Nhan on 9/26/2016.
 */

public class Pokemon {
    private String name;
    private String tag;
    private String gen;
    private String img;
    private String color;

    public Pokemon(String name, String tag, String img, String color) {
        this.name = name;
        this.tag = tag;
        this.img = img;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
