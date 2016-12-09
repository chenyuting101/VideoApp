package com.cs.group.Utils;

import android.content.SharedPreferences;

import com.cs.group.provider.SharedPrefFiles;
import com.cs.group.provider.SharedPrefKeys;
import com.cs.group.videoapp.ListBuddies;
import com.jpardogo.listbuddies.lib.views.ListBuddiesLayout;

public class SharePreferences {

    public static void saveCustomization(SharedPrefKeys prefKey, int progress) {
        SharedPreferences customize_pref = getCustomizePref();
        SharedPreferences.Editor editor = customize_pref.edit();
        editor.putInt(prefKey.toString(), progress);
        editor.commit();
    }

    public static int getValue(SharedPrefKeys prefKey) {
        SharedPreferences customize_pref = getCustomizePref();
        int defaultValue = getDefaultValue(prefKey);
        return customize_pref.getInt(prefKey.toString(), defaultValue);
    }

    private static SharedPreferences getCustomizePref() {
        return ListBuddies.getAppContext().getSharedPreferences(SharedPrefFiles.CUSTOMIZE_SETTINGS.toString(), 0);
    }

    private static int getDefaultValue(SharedPrefKeys prefKey) {
        int defaultValue = 0;
        switch (prefKey) {
            case GAP_PROGRESS:
                defaultValue = ListBuddies.getAppContext().getResources().getDimensionPixelSize(com.jpardogo.listbuddies.lib.R.dimen.default_margin_between_lists);
//                defaultValue = ListBuddies.getAppContext().getResources().getDimensionPixelSize(com.jpardogo.listbuddies.lib.R.dimen.default_margin_between_lists);
                break;
            case SPEED_PROGRESS:
                defaultValue = ListBuddiesLayout.DEFAULT_SPEED;
                break;
            case DIV_HEIGHT_PROGRESS:
                defaultValue = ListBuddies.getAppContext().getResources().getDimensionPixelSize(com.jpardogo.listbuddies.lib.R.dimen.default_margin_between_lists);
                break;
        }
        return defaultValue;
    }

    public static void reset() {
        for (SharedPrefKeys key : SharedPrefKeys.values()) {
            saveCustomization(key, getDefaultValue(key));
        }
    }
}