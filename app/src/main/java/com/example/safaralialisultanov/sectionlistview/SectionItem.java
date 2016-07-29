package com.example.safaralialisultanov.sectionlistview;

/**
 * Created by safarali.alisultanov on 29.07.2016.
 */
public class SectionItem implements Item {

    private final String title;
    public SectionItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean isSection() {
        return true;
    }
}
