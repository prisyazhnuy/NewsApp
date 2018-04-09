package com.prisyazhnuy.newsapp.sort;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.prisyazhnuy.newsapp.data.local.preferences.PreferencesSource;

/**
 * max.pr on 04.03.2018.
 */

public class SortPresenterImpl extends MvpBasePresenter<SortContract.SortView>
        implements SortContract.SortPresenter {

    private static final int PUBLISHED_DATE = 0;
    private static final int POPULARITY = 1;
    private PreferencesSource mPreferencesSource;

    public SortPresenterImpl(PreferencesSource preferencesSource) {
        this.mPreferencesSource = preferencesSource;
    }

    @Override
    public void onLoad() {
        final int sortType = mPreferencesSource.getSort();
        ifViewAttached(view -> {
            switch (sortType) {
                case PUBLISHED_DATE:
                    view.setPublishedDateChecked();
                    break;
                case POPULARITY:
                    view.setPopularityChecked();
                    break;
                default:
                    view.setPublishedDateChecked();
            }
        });
    }

    @Override
    public void setPublishedSort() {
        mPreferencesSource.setSort(PUBLISHED_DATE);
    }

    @Override
    public void setPopularitySort() {
        mPreferencesSource.setSort(POPULARITY);
    }
}
