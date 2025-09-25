package edu.bsu.project1;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WikipediaClient {

    private static final String API_URL =
            "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions"
                    + "&rvprop=timestamp|user&rvlimit=15&redirects&titles=";

    public String fetchRevisions(String article) throws Exception {
        try {
            String encoded = URLEncoder.encode(article, StandardCharsets.UTF_8);
            URL url = new URL(API_URL + encoded);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "RevisionReporter/0.1 (me@bsu.edu)");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            if (conn.getResponseCode() == 404) {
                throw new PageNotFoundException();
            }

            try (InputStream in = conn.getInputStream()) {
                return new String(in.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (PageNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new NetworkException();
        }
    }

    public static class PageNotFoundException extends Exception {}
    public static class NetworkException extends Exception {}
}