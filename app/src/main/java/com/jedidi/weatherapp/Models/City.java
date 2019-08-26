package com.jedidi.weatherapp.Models;

/**
 * @author Faouzi Jedidi - S1719017
 */

public class City {
    private String name;

    private int id;

    private int image;

    public City(String name, int id, int image){
        this.name = name;
        this.id = id;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getCityImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", image=" + image +
                '}';
    }
}
