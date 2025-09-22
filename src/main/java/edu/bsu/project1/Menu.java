package edu.bsu.project1;

import java.util.Scanner;

// User interface and output
public class Menu {
    Scanner scanner = new Scanner(System.in);

    //asks the user a wikipedia page name
    public void menuInterface() throws Exception{
        String userInput;

        System.out.println("Enter the name of a Wikipedia page: ");
        userInput = scanner.nextLine();

        WikiConnection.fetchArticle(userInput);
    }


}
