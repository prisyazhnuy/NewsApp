package com.prisyazhnuy.newsapp.favourite;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.prisyazhnuy.newsapp.data.local.db.NewsDAO;
import com.prisyazhnuy.newsapp.data.pojo.Article;
import com.prisyazhnuy.newsapp.news_list.NewsListContract;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * max.pr on 04.03.2018.
 */

public class FavouriteListPresenterImpl extends MvpBasePresenter<NewsListContract.NewsListView>
        implements NewsListContract.NewsListPresenter {

    private final NewsDAO mNewsDAO;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public FavouriteListPresenterImpl(NewsDAO dao) {
        this.mNewsDAO = dao;
    }

    @Override
    public void loadBreakNews() {
        Disposable disposable = mNewsDAO.getAll()
                .subscribe(new Consumer<List<Article>>() {
                               @Override
                               public void accept(final List<Article> articles) throws Exception {
                                   ifViewAttached(new ViewAction<NewsListContract.NewsListView>() {
                                       @Override
                                       public void run(@NonNull NewsListContract.NewsListView view) {
                                           if (articles == null || articles.isEmpty()) {
                                               view.showEmptyList();
                                           } else {
                                               view.showNews(articles);
                                           }
                                       }
                                   });
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(final Throwable throwable) throws Exception {
                                ifViewAttached(new ViewAction<NewsListContract.NewsListView>() {
                                    @Override
                                    public void run(@NonNull NewsListContract.NewsListView view) {
                                        view.showError(throwable.getMessage());
                                    }
                                });
                            }
                        });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void loadNextNews() {

    }

    @Override
    public void loadFavourites() {

    }

    @Override
    public void saveNews(Article item) {

    }

    @Override
    public void delete(final String url) {
        Disposable disposable = mNewsDAO.delete(url)
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        ifViewAttached(new ViewAction<NewsListContract.NewsListView>() {
                            @Override
                            public void run(@NonNull NewsListContract.NewsListView view) {
                                view.delete(url);
                            }
                        });
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void detachView() {
        mCompositeDisposable.clear();
        super.detachView();
    }
}
