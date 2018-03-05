package com.prisyazhnuy.newsapp.data.local.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.prisyazhnuy.newsapp.R;

/**
 * max.pr on 04.03.2018.
 */

public class DataRepository implements PreferencesSource {

    private static final String SORT_BY = "sort_by";
    private static final String SOURCES = "sources";
    private static final String FROM = "from";
    private static final String TO = "to";
    private static PreferencesSource sInstance;

    private final SharedPreferences mSharedPreferences;

    private DataRepository(Context context) {
        mSharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    public static PreferencesSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DataRepository(context);
        }
        return sInstance;
    }

    @Override
    public void setSort(int sortType) {
        mSharedPreferences.edit().putInt(SORT_BY, sortType).apply();
    }

    @Override
    public int getSort() {
        return mSharedPreferences.getInt(SORT_BY, 0);
    }

    @Override
    public void setCheckedSources(String sources) {
        mSharedPreferences.edit().putString(SOURCES, sources).apply();
    }

    @Override
    public String getCheckedSources() {
        return mSharedPreferences.getString(SOURCES, null);
    }

    @Override
    public void setDateFrom(String from) {
        mSharedPreferences.edit().putString(FROM, from).apply();
    }

    @Override
    public String getDateFrom() {
        return mSharedPreferences.getString(FROM, null);
    }

    @Override
    public void setDateTo(String to) {
        mSharedPreferences.edit().putString(TO, to).apply();
    }

    @Override
    public String getDateTo() {
        return mSharedPreferences.getString(TO, null);
    }
}
