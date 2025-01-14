package com.dev.sst.webcrawler.Crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AutoConfiguration
public class Crawler {

    public Object Helper(String url,int depth){
        HashSet<String> visited = new HashSet<>();
        Map<String, Object> result = start(url, depth, visited);
        return result;
    }

    private  Map<String, Object> start(String url, int level, HashSet<String> vis) {
        if (level == 0 || vis.contains(url)) return null;

        System.out.println("Debug : \n url = " + url + " level = " + level);
        vis.add(url); // Mark the URL as visited
        Map<String, Object> data = new HashMap<>();

        Document doc = fetch(url);
        if (doc != null) {
            int count = 0;
            var elements = doc.select("a[href]");
            for (Element ele : elements) {
                String hrefValue = ele.absUrl("href");
                if (!vis.contains(hrefValue)) {
                    // Recursive call with decremented level
                    var res = start(hrefValue, level - 1, vis);
                    data.put(hrefValue, res);
                }
                count++;
                if (count > level) break; // Limit number of links to level
            }
        }
        return data;
    }

    private  Document fetch(String url) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();
            String title = doc.title();
            if (con.response().statusCode() == 200) {
                System.out.println("Url : " + url);
                System.out.println("Title : " + title);
            }
            return doc;
        } catch (Exception e) {
            System.err.println("Failed to fetch URL: " + url + " Error: " + e.getMessage());
        }
        return null;
    }
}
