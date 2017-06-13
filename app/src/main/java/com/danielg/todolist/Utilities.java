package com.danielg.todolist;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Utilities {

    private static final String TAG = "Utilities";

    public static void serializeList(Context context, List<Entry> list, String fileName) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
        }
        catch(IOException ex) {
            Log.e(TAG, "serializeList: Unable to save list", ex);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Entry> deserializeList(Context context, String fileName) {
        List<Entry> li;
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            li = (List<Entry>) ois.readObject();
            return li;
        }
        catch(IOException | ClassNotFoundException ex) {
            Log.e(TAG, "deserializeList: Unable to load list", ex);
            return new ArrayList<>();
        }
    }

}
