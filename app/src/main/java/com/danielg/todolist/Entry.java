package com.danielg.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Entry implements java.io.Serializable {
    private String title;
    private String notes;
    private boolean isComplete;
    private long dateAdded;

    public Entry(String title, String notes, boolean complete) {
        this.title = title;
        this.notes = notes;
        this.isComplete = complete;

        this.dateAdded = new Date().getTime();
    }

    public String getTitle() {
        return title;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    /**
     * Returns a list with test data for the purpose of testing
     * @return List of test date
     */
    public static List<Entry> initializeTestData() {
        List<Entry> li = new ArrayList<>();
        li.add(new Entry("Foo", "Bar", false));
        li.add(new Entry("Foo", "Bar", false));
        li.add(new Entry("Foo", "Bar", false));
        li.add(new Entry("Foo", "Bar", true));
        li.add(new Entry("Foo", "Bar", false));
        li.add(new Entry("Foo", "Bar", false));
        li.add(new Entry("Foo", "Bar", true));
        li.add(new Entry("Foo", "Bar", false));
        li.add(new Entry("Foo", "Bar", true));
        li.add(new Entry("Foo", "Bar", true));
        return li;
    }
}
