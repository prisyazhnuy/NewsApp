package com.prisyazhnuy.newsapp.news_list;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.prisyazhnuy.newsapp.data.NewsModel;
import com.prisyazhnuy.newsapp.data.local.db.NewsDAO;
import com.prisyazhnuy.newsapp.data.local.preferences.PreferencesSource;
import com.prisyazhnuy.newsapp.data.pojo.Article;
import com.prisyazhnuy.newsapp.data.pojo.NewsResponse;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * max.pr on 02.03.2018.
 */

public class NewsListPresenterImpl extends MvpBasePresenter<NewsListContract.NewsListView>
        implements NewsListContract.NewsListPresenter {

    private static final int PUBLISHED_DATE_ID = 0;
    private static final int POPULARITY_ID = 1;

    private static final String PUBLISHED_DATE = "publishedAt";
    private static final String POPULARITY = "popularity";
    private static final int PAGE_SIZE = 25;
    private NewsModel mNewsModel;
    private PreferencesSource mDataSource;
    private NewsDAO mNewsDAO;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private int mPage = 1;
    private boolean mLoading;

    public NewsListPresenterImpl(NewsModel newsModel, PreferencesSource source, NewsDAO dao) {
        this.mNewsModel = newsModel;
        this.mDataSource = source;
        this.mNewsDAO = dao;
    }

    private void loadNews() {
        mLoading = true;
        String checkedSources = mDataSource.getCheckedSources();
        String dateFrom = mDataSource.getDateFrom();
        String dateTo = mDataSource.getDateTo();
        int sort = mDataSource.getSort();
        String sortBy = PUBLISHED_DATE;
        switch (sort) {
            case PUBLISHED_DATE_ID:
                sortBy = PUBLISHED_DATE;
                break;
            case POPULARITY_ID:
                sortBy = POPULARITY;
                break;
        }
        Disposable disposable = mNewsModel.getNewsByFilter(checkedSources, dateFrom, dateTo, sortBy,
                PAGE_SIZE, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsResponse>() {
                    @Override
                    public void accept(final NewsResponse newsResponse) throws Exception {
                        ifViewAttached(new ViewAction<NewsListContract.NewsListView>() {
                            @Override
                            public void run(@NonNull NewsListContract.NewsListView view) {
                                mLoading = false;
                                view.showNews(newsResponse.getArticles());
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(final Throwable throwable) throws Exception {
                        ifViewAttached(new ViewAction<NewsListContract.NewsListView>() {
                            @Override
                            public void run(@NonNull NewsListContract.NewsListView view) {
                                mLoading = false;
                                if (throwable instanceof HttpException) {
                                    try {
                                        view.showError(((HttpException) throwable).response().errorBody().string());
                                    } catch (IOException e) {
                                        view.showError(e.getMessage());
                                    }
                                } else {
                                    view.showError(throwable.getMessage());
                                }
                            }
                        });
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void loadBreakNews() {
        if (!mLoading) {
            mPage = 1;
            ifViewAttached(new ViewAction<NewsListContract.NewsListView>() {
                @Override
                public void run(@NonNull NewsListContract.NewsListView view) {
                    view.clearList();
                }
            });
            loadNews();
        }
    }

    @Override
    public void loadNextNews() {
        if (!mLoading) {
            mPage++;
            loadNews();
        }
    }

    @Override
    public void loadFavourites() {
        Disposable disposable = mNewsDAO.getAll()
                .subscribe(new Consumer<List<Article>>() {
                    @Override
                    public void accept(final List<Article> articles) throws Exception {
                        ifViewAttached(new ViewAction<NewsListContract.NewsListView>() {
                            @Override
                            public void run(@NonNull NewsListContract.NewsListView view) {
                                view.addFavourites(articles);
                            }
                        });
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void saveNews(Article item) {
        Disposable disposable = mNewsDAO.insert(item)
                .subscribe();
        mCompositeDisposable.add(disposable);
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
