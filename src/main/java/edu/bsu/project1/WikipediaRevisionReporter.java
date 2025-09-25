package edu.bsu.project1;

import java.util.List;
import java.util.Scanner;

public class WikipediaRevisionReporter {
    public static void main(String[] args) {
        String article;

        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Wikipedia article name: ");
            if (scanner.hasNextLine()) {
                article = scanner.nextLine().trim();
            } else {
                System.err.println("Error: No article name provided.");
                System.exit(1);
                return;
            }
        } else {
            article = args[0];
        }

        try {
            WikipediaClient client = new WikipediaClient();
            String jsonResponse = client.fetchRevisions(article);

            RevisionParser parser = new RevisionParser();
            WikipediaResponse response = parser.parse(jsonResponse);

            if (response.getRedirectTarget() != null) {
                System.out.println("Redirected to " + response.getRedirectTarget());
            }

            List<Revision> revisions = response.getRevisions();
            RevisionFormatter formatter = new RevisionFormatter();

            for (int i = 0; i < revisions.size(); i++) {
                System.out.println(formatter.format(revisions.get(i), i + 1));
            }

        } catch (WikipediaClient.PageNotFoundException e) {
            System.err.println("Error: No Wikipedia page found for '" + article + "'");
            System.exit(1);
        } catch (WikipediaClient.NetworkException e) {
            System.err.println("Error: Network issue occurred.");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Error: Unexpected issue - " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}