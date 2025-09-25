package edu.bsu.project1;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class RevisionFormatter {
    // this is the formatter. takes a single revision and turns it into the correct format
    public String format(Revision r, int index) {
        String utc = DateTimeFormatter.ISO_INSTANT.format(Instant.parse(r.getTimestamp()));
        return index + "  " + utc + "  " + r.getUsername();
    }
}