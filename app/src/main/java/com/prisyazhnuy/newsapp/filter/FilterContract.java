package com.prisyazhnuy.newsapp.filter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.prisyazhnuy.newsapp.data.pojo.Source;

import java.util.List;

/**
 * max.pr on 04.03.2018.
 */

public interface FilterContract {
    interface FilterView extends MvpView {
        void showSources(List<Source> sources);

        void setDateFrom(String from);

        void setDateTo(String to);

        void showNoSources();

        void showError(String message);

        void setCheckedSources(List<String> sources);
    }

    interface FilterPresenter extends MvpPresenter<FilterView> {
        void loadSources();

        void setDateFrom(int year, int month, int day);

        void setDateTo(int year, int month, int day);

        void loadCacheFilter();

        void setSource(String id);
    }
}
