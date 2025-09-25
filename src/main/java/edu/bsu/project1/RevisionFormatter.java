// src/main/java/edu/bsu/project1/RevisionFormatter.java
package edu.bsu.project1;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class RevisionFormatter {
    public String format(Revision r, int index) {
        String utc = DateTimeFormatter.ISO_INSTANT.format(Instant.parse(r.getTimestamp()));
        return index + "  " + utc + "  " + r.getUsername();
    }
}