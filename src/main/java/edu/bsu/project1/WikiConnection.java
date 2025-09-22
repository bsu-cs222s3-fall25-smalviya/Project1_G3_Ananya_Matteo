package edu.bsu.project1;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

// Pulls wikipedia article
public class WikiConnection {
    public static String fetchArticle(String title) throws Exception {
        String encodedUrlString = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles=" +
                        URLEncoder.encode(title, StandardCharsets.UTF_8) + "&rvprop=timestamp|user&rvlimit=15&redirects";

        URL url = new URL(encodedUrlString);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Project1/0.1 (academic use; https://example.com)");
        connection.connect();

        String jsonData = readJsonAsStringFrom(connection);
        printRawJson(jsonData);

        return jsonData;
    }

    private static String readJsonAsStringFrom(URLConnection connection) throws IOException {
        return new String(connection.getInputStream().readAllBytes(), Charset.defaultCharset());
    }

    private static void printRawJson(String jsonData) {
        System.out.println(jsonData);
    }
}