package de.andrew.demoZITF.myDataModels;

/**
 * Created by Andrew on 4/14/16.
 */

import java.util.ArrayList;
import java.util.List;

public class Group {

    public String string;
    public final List<String> children = new ArrayList<String>();

    public Group(String string) {
        this.string = string;
    }

}