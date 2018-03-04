package com.prisyazhnuy.newsapp.sort;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * max.pr on 04.03.2018.
 */

public interface SortContract {

    interface SortView extends MvpView {
        void setPublishedDateChecked();
        void setPopularityChecked();
    }

    interface SortPresenter extends MvpPresenter<SortView> {
        void onLoad();
        void setPublishedSort();
        void setPopularitySort();
    }
}
