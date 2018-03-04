package com.prisyazhnuy.newsapp.favourite;

import com.prisyazhnuy.newsapp.news_list.NewsListContract;

/**
 * max.pr on 04.03.2018.
 */

public interface FavouriteListContract {
    interface FavouriteListView extends NewsListContract.NewsListView {
        void delete(long id);
    }

    interface FavouriteListPresenter extends NewsListContract.NewsListPresenter<FavouriteListView> {
        void delete(long id);
    }
}
