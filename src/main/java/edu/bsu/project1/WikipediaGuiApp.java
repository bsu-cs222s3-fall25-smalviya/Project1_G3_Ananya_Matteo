// src/main/java/edu/bsu/project1/WikipediaGuiApp.java
package edu.bsu.project1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.jayway.jsonpath.JsonPath;

public class WikipediaGuiApp extends Application {
    private TextField articleField;
    private Button fetchButton;
    private TextArea outputArea;

    @Override
    public void start(Stage stage) {
        articleField = new TextField();
        articleField.setPromptText("Enter article name");

        fetchButton = new Button("Fetch");
        fetchButton.setOnAction(e -> fetchArticle());

        outputArea = new TextArea();
        outputArea.setEditable(false);

        ToolBar toolbar = new ToolBar(articleField, fetchButton);

        BorderPane root = new BorderPane();
        root.setTop(toolbar);
        root.setCenter(outputArea);

        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Wikipedia Revision Reporter");
        stage.show();
    }

    private void fetchArticle() {
        String article = articleField.getText().trim();
        if (article.isEmpty()) {
            alert("Missing Article", "Please enter an article name.");
            return;
        }

        setBusy(true);
        outputArea.clear();
        outputArea.setText("Fetching data...");

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    WikipediaFetcher fetcher = new WikipediaFetcher();
                    byte[] bytes = readAll(fetcher.fetch(article));

                    if (isMissingPage(bytes)) {
                        Platform.runLater(() -> {
                            outputArea.setText("");
                            alert("No Page Found", "No Wikipedia page found for '" + article + "'");
                        });
                        return null;
                    }

                    String redirect = findRedirect(bytes);

                    RevisionParser parser = new RevisionParser();
                    List<Revision> revisions = parser.parse(new ByteArrayInputStream(bytes));

                    RevisionFormatter formatter = new RevisionFormatter();
                    StringBuilder sb = new StringBuilder();
                    if (redirect != null) sb.append("Redirected to ").append(redirect).append("\n\n");

                    for (int i = 0; i < revisions.size() && i < 15; i++) {
                        sb.append(formatter.format(revisions.get(i), i + 1)).append("\n");
                    }

                    Platform.runLater(() -> outputArea.setText(sb.toString()));

                } catch (Exception ex) {
                    Platform.runLater(() -> alert("Error", "Could not load data."));
                } finally {
                    Platform.runLater(() -> setBusy(false));
                }
                return null;
            }
        };
        new Thread(task).start();
    }

    private void setBusy(boolean busy) {
        fetchButton.setDisable(busy);
        articleField.setDisable(busy);
    }

    private static byte[] readAll(InputStream in) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        in.transferTo(bos);
        return bos.toByteArray();
    }

    private static boolean isMissingPage(byte[] json) {
        try {
            return !JsonPath.parse(new String(json, StandardCharsets.UTF_8))
                    .read("$.query.pages.*.missing", List.class)
                    .isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private static String findRedirect(byte[] json) {
        try {
            List<?> arr = JsonPath.parse(new String(json, StandardCharsets.UTF_8))
                    .read("$.query.redirects[0:1]");
            if (arr == null || arr.isEmpty()) return null;
            return ((java.util.Map<?, ?>) arr.get(0)).get("to").toString();
        } catch (Exception e) {
            return null;
        }
    }

    private void alert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}