package de.andrew.demoZITF.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.andrew.demoZITF.R;

/**
 * Just dummy content. Nothing special.
 *
 * Created by Andreas Schrade on 14.12.2015.
 */
public class DummyContent {

    /**
     * An array of sample items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample items. Key: sample ID; Value: Item.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<>(5);

    static {
        addItem(new DummyItem("1", R.drawable.p1, "Victoria Falls", "Water falls", "Focusing is about saying No."));
        addItem(new DummyItem("2", R.drawable.p2, "Great Zimbabwe", "Historic Site","A quitter never wins and a winner never quits."));
        addItem(new DummyItem("3", R.drawable.p3, "Gonarezhou", "Home of Big 5", "Action is the foundational key to all success."));
        addItem(new DummyItem("4", R.drawable.p4, "Inyangani", "Mountain","Our only limitations are those we set up in our own minds."));
        addItem(new DummyItem("5", R.drawable.p5, "ZITF", "Trade fair","Deciding what not do do is as important as deciding what to do."));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class DummyItem {
        public final String id;
        public final int photoId;
        public final String title;
        public final String author;
        public final String content;

        public DummyItem(String id, int photoId, String title, String author, String content) {
            this.id = id;
            this.photoId = photoId;
            this.title = title;
            this.author = author;
            this.content = content;
        }
    }
}
