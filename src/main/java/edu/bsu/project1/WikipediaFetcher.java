// src/main/java/edu/bsu/project1/WikipediaFetcher.java
package edu.bsu.project1;

import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class WikipediaFetcher {

    public InputStream fetch(String article) throws Exception {
        String base = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles=";
        String url = base + URLEncoder.encode(article, StandardCharsets.UTF_8)
                + "&rvprop=timestamp|user&rvlimit=15&redirects";

        URLConnection conn = new java.net.URL(url).openConnection();
        conn.setRequestProperty("User-Agent", "FirstProject/0.1 (student use; you@school.edu)");
        return conn.getInputStream();
    }
}