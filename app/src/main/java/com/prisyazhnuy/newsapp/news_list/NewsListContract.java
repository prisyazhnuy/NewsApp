package com.prisyazhnuy.newsapp.news_list;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.prisyazhnuy.newsapp.data.pojo.Article;

import java.util.List;

/**
 * max.pr on 02.03.2018.
 */

public interface NewsListContract {

    interface NewsListView extends MvpView {
        void showNews(List<Article> articles);

        void showError(String error);

        void showEmptyList();

        void clearList();

        void delete(String url);

        void addFavourites(List<Article> articles);

    }

    interface NewsListPresenter extends MvpPresenter<NewsListView> {
        void loadBreakNews();

        void loadNextNews();

        void loadFavourites();

        void saveNews(Article item);

        void delete(String url);

    }
}
