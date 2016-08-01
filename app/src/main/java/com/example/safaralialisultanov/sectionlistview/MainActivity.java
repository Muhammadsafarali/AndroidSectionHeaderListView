package com.example.safaralialisultanov.sectionlistview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MainActivity extends ListActivity implements View.OnClickListener {

    static class SimpleAdapter extends ArrayAdapter<Item> implements PinnedSectionListView.PinnedSectionListAdapter {

        private static final int[] COLORS = new int[] {
                R.color.green_light /*, R.color.orange_light,
                R.color.blue_light, R.color.red_light*/ };

        public SimpleAdapter(Context context, int resource, int textViewResourceId) {
            super(context, resource, textViewResourceId);
//            generateDataset('A', 'Z', false);

            generateDataset('A', 'Z', false);
        }

        public static
        <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
            List<T> list = new ArrayList<T>(c);
            java.util.Collections.sort(list);
            return list;
        }

        public void generateDataset(char from, char to, boolean clear) {

            Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
            ArrayList<String> proj = new ArrayList<String>();
            proj.add("row0");
            proj.add("row1");
            proj.add("row2");

            map.put("AXP", proj);

            proj = new ArrayList<String>();
            proj.add("row3");
            proj.add("row4");
            proj.add("row5");

            map.put("CRM", proj);

            proj = new ArrayList<String>();
            proj.add("row6");
            proj.add("row7");
            proj.add("row8");
            proj.add("row9");
            proj.add("row10");
            proj.add("row11");
            proj.add("row12");
            proj.add("row13");
            proj.add("row14");
            proj.add("row15");
            proj.add("row16");
            proj.add("row17");

            map.put("DSR", proj);

            System.out.println(map.size());
            System.out.println(map.get("AXP"));
            System.out.println(map.get("CRM"));

            if (clear) clear();

            final int sectionsNumber = map.size();
            prepareSections(sectionsNumber);

            int sectionPosition = 0, listPosition = 0;
            Set<String> keys = map.keySet();

            // Сортировка
            List<String> s = asSortedList(keys);

            for (int i = 0; i < sectionsNumber; i++) {
                Item section = new Item(Item.SECTION, String.valueOf(s.get(i)));
                section.sectionPosition = sectionPosition;
                section.listPosition = listPosition++;
                onSectionAdded(section, sectionPosition);
                add(section);

                for ( String val : map.get(s.get(i)) ) {
                    Item item = new Item(Item.ITEM, val);
                    item.sectionPosition = sectionPosition;
                    item.listPosition = listPosition++;
                    add(item);
                }

//                final int itemsNumber = (int) Math.abs((Math.cos(2f*Math.PI/3f * sectionsNumber / (i+1f)) * 25f));
//                for (int j=0;j<itemsNumber;j++) {
//                    Item item = new Item(Item.ITEM, section.text.toUpperCase(Locale.ENGLISH) + " - " + j);
//                    item.sectionPosition = sectionPosition;
//                    item.listPosition = listPosition++;
//                    add(item);
//                }

                sectionPosition++;
            }
        }

        protected void prepareSections(int sectionsNumber) { }
        protected void onSectionAdded(Item section, int sectionPosition) { }


        @Override public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setTextColor(Color.DKGRAY);
            view.setTag("" + position);
            Item item = getItem(position);
            if (item.type == Item.SECTION) {
                //view.setOnClickListener(PinnedSectionListActivity.this);
                view.setBackgroundColor(parent.getResources().getColor(COLORS[item.sectionPosition % COLORS.length]));
            }
            return view;
        }

        @Override public int getViewTypeCount() {
            return 2;
        }

        @Override public int getItemViewType(int position) {
            return getItem(position).type;
        }

        @Override
        public boolean isItemViewTypePinned(int viewType) {
            return viewType == Item.SECTION;
        }
    }


    static class FastScrollAdapter extends SimpleAdapter implements SectionIndexer {

        private Item[] sections;

        public FastScrollAdapter(Context context, int resource, int textViewResourceId) {
            super(context, resource, textViewResourceId);
        }

        @Override protected void prepareSections(int sectionsNumber) {
            sections = new Item[sectionsNumber];
        }

        @Override protected void onSectionAdded(Item section, int sectionPosition) {
            sections[sectionPosition] = section;
        }

        @Override public Item[] getSections() {
            return sections;
        }

        @Override public int getPositionForSection(int section) {
            if (section >= sections.length) {
                section = sections.length - 1;
            }
            return sections[section].listPosition;
        }

        @Override public int getSectionForPosition(int position) {
            if (position >= getCount()) {
                position = getCount() - 1;
            }
            return getItem(position).sectionPosition;
        }

    }

    private boolean hasHeaderAndFooter;
    private boolean isFastScroll;
    private boolean addPadding;
    private boolean isShadowVisible = true;
    private int msetDatasetUpdateCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            isFastScroll = savedInstanceState.getBoolean("isFastScroll");
            addPadding = savedInstanceState.getBoolean("addPadding");
            isShadowVisible = savedInstanceState.getBoolean("isShadowVisible");
            hasHeaderAndFooter = savedInstanceState.getBoolean("hasHeaderAndFooter");
        }
//        initializeHeaderAndFooter();
        initializeAdapter();
        initializePadding();

        Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        ArrayList<String> proj = new ArrayList<String>();
        proj.add("row0");
        proj.add("row1");
        proj.add("row2");

        map.put("AXP", proj);

        proj = new ArrayList<String>();
        proj.add("row3");
        proj.add("row4");
        proj.add("row5");

        map.put("CRM", proj);

        System.out.println(map.size());
        System.out.println(map.get("AXP"));
        System.out.println(map.get("CRM"));
    }

    private void initializePadding() {
        float density = getResources().getDisplayMetrics().density;
        int padding = addPadding ? (int) (16 * density) : 0;
        getListView().setPadding(padding, padding, padding, padding);
    }

    private void initializeHeaderAndFooter() {
        setListAdapter(null);
        if (hasHeaderAndFooter) {
            ListView list = getListView();

            LayoutInflater inflater = LayoutInflater.from(this);
            TextView header1 = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, list, false);
            header1.setText("First header");
            list.addHeaderView(header1);

            TextView header2 = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, list, false);
            header2.setText("Second header");
            list.addHeaderView(header2);

            TextView footer = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, list, false);
            footer.setText("Single footer");
            list.addFooterView(footer);
        }
        initializeAdapter();
    }

    @SuppressLint("NewApi")
    private void initializeAdapter() {
        getListView().setFastScrollEnabled(isFastScroll);
        if (isFastScroll) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                getListView().setFastScrollAlwaysVisible(true);
            }
            setListAdapter(new FastScrollAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1));
        } else {
            setListAdapter(new SimpleAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1));
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Item: " + v.getTag() , Toast.LENGTH_SHORT).show();
    }

    static class Item {

        public static final int ITEM = 0;
        public static final int SECTION = 1;

        public final int type;
        public final String text;

        public int sectionPosition;
        public int listPosition;

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }

        @Override
        public String toString() { return text; }
    }

//    @Override
//    public void onItemClick(AdapterView arg0, View arg1, int position, long arg3) {
//
//        EntryItem item = (EntryItem)items.get(position);
//        Toast.makeText(this, "You clicked " + item.title , Toast.LENGTH_SHORT).show();
//    }
}

