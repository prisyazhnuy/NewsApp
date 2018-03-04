package com.prisyazhnuy.newsapp.data.local.preferences;

/**
 * max.pr on 04.03.2018.
 */

public interface PreferencesSource {
    void setSort(int sortType);

    int getSort();

    void setCheckedSources(String sources);

    String getCheckedSources();

    void setDateFrom(String from);

    String getDateFrom();

    void setDateTo(String to);

    String getDateTo();
}
