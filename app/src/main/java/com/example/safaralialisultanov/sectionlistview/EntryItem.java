package com.example.safaralialisultanov.sectionlistview;

/**
 * Created by safarali.alisultanov on 29.07.2016.
 */
public class EntryItem implements Item {

    public final String title;
    public final String subtitle;

    public EntryItem(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    @Override
    public boolean isSection() {
        return false;
    }
}
