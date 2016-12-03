package com.cs.group.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by chenyuting on 12/3/16.
 */

public class SharedPreferenceTool {
    private static String name = "Videoapp";

    /**
     * Add data to SharedPreferences
     * @param key
     * @param value
     * @param con the context
     */
    public static void write(String key, String value, Context con)
    {
        SharedPreferences sharedPreferences = con.getSharedPreferences(name, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    /**
     * Read data from SharedPreferences
     * @param key
     * @param con
     * @return
     */
    public static String read(String key, Context con)
    {
        SharedPreferences sharedPreferences = con.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }

}
