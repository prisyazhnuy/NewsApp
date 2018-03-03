package com.prisyazhnuy.newsapp.news_list;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.prisyazhnuy.newsapp.data.NewsModel;
import com.prisyazhnuy.newsapp.data.pojo.NewsResponse;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * max.pr on 02.03.2018.
 */

public class NewsListPresenterImpl extends MvpBasePresenter<NewsListContract.NewsListView>
        implements NewsListContract.NewsListPresenter {

    private NewsModel mNewsModel;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public NewsListPresenterImpl(NewsModel newsModel) {
        this.mNewsModel = newsModel;
    }

    @Override
    public void onLoad() {
        Disposable disposable = mNewsModel.getTopHeadlinesByCountry("ua", null, null,
                20, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsResponse>() {
                    @Override
                    public void accept(final NewsResponse newsResponse) throws Exception {
                        ifViewAttached(new ViewAction<NewsListContract.NewsListView>() {
                            @Override
                            public void run(@NonNull NewsListContract.NewsListView view) {
                                view.showNews(newsResponse.getArticles());
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ifViewAttached(new ViewAction<NewsListContract.NewsListView>() {
                            @Override
                            public void run(@NonNull NewsListContract.NewsListView view) {
                                view.showError();
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
