package edu.bsu.project1;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class WikipediaRevisionReporter {

    public static void main(String[] args) {
        String article;
        if (args.length == 0) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Wikipedia article name: ");
            article = sc.nextLine();
        } else {
            article = args[0];
        }

        try {
            WikipediaFetcher fetcher = new WikipediaFetcher();
            InputStream jsonStream = fetcher.fetch(article);

            byte[] jsonBytes = readAll(jsonStream);


            if (isMissingPage(jsonBytes)) {
                String msg = "Error: No Wikipedia page found for '" + article + "'";
                System.err.println(msg);
                System.out.println(msg);
                System.exit(1);
            }

            RevisionParser parser = new RevisionParser();
            List<Revision> revisions = parser.parse(new ByteArrayInputStream(jsonBytes));

            RevisionFormatter formatter = new RevisionFormatter();
            for (int i = 0; i < revisions.size(); i++) {
                System.out.println(formatter.format(revisions.get(i), i + 1));
            }
        } catch (Exception e) {
            String msg = "Error: " + e.getMessage();
            System.err.println(msg);
            System.out.println(msg);
            System.exit(1);
        }
    }

    private static boolean isMissingPage(byte[] jsonBytes) {
        String json = new String(jsonBytes, StandardCharsets.UTF_8);
        ReadContext ctx = JsonPath.parse(json);
        try {

            List<Object> missing = ctx.read("$.query.pages.*.missing");
            return !missing.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] readAll(InputStream in) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        in.transferTo(bos);
        return bos.toByteArray();
    }
}