package com.jedidi.weatherapp.Models;

/**
 * @author Faouzi Jedidi - S1719017
 */

public class Day {
    public String title;
    public String description;
    public String pubDate;
    public String img;

    public Day(String title, String description, String pubDate, String imgURL) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.img = imgURL;
    }
}
