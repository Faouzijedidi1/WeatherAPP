package com.jedidi.weatherapp.Handlers;

import android.util.Log;
import android.util.Xml;

import com.jedidi.weatherapp.Models.Day;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Faouzi Jedidi - S1719017
 */

public class XMLPullParser {
    public static List<Day> parseFeed(InputStream inputStream) throws XmlPullParserException,
            IOException {
        String title = null;
        String description = null;
        String pubDate = null;
        String imgURL = null;

        boolean isImage =false;
        boolean isItem = false;

        List<Day> items = new ArrayList<>();

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if(name == null)
                    continue;

                if (eventType == XmlPullParser.START_TAG) {
                    if(name.equalsIgnoreCase("image")) {
                        isImage = true;
                    }
                    if(name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                if(eventType == XmlPullParser.END_TAG) {
                    if(name.equalsIgnoreCase("image")) {
                        isImage = false;
                    }
                    if(name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                Log.d("MyXmlParser", "Parsing ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (isItem && name.equalsIgnoreCase("description")) {
                    description = result;
                } else if (isItem && name.equalsIgnoreCase("pubDate")) {
                    pubDate = result;
                }else if (isImage && name.equalsIgnoreCase("url")) {
                    imgURL = result;
                }

                if (title != null && pubDate != null && description != null && imgURL != null) {
                    Day day = new Day(title, description, pubDate, imgURL);
                    items.add(day);
                    title = null;
                    description = null;
                    pubDate = null;
                    isImage = false;
                    isItem = false;
                }
            }

            return items;
        } finally {
            inputStream.close();
        }
    }
}
