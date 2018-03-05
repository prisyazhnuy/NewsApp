package com.prisyazhnuy.newsapp.data.local.db;

import com.prisyazhnuy.newsapp.data.local.db.model.News;
import com.prisyazhnuy.newsapp.data.pojo.Article;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * max.pr on 04.03.2018.
 */

public class NewsDAORealm implements NewsDAO {

    private static NewsDAO sInstance;
    private final RealmConfiguration mConfig;
    private final String mCachePath;

    private NewsDAORealm(String cachePath) {
        this.mCachePath = cachePath;
        mConfig = new RealmConfiguration.Builder()
                .build();
    }

    public static NewsDAO getInstance(String cachePath) {
        if (sInstance == null) {
            sInstance = new NewsDAORealm(cachePath);
        }
        return sInstance;
    }

    @Override
    public Completable insert(Article article) {
        return Single.just(article)
                .map(new Function<Article, Article>() {
                    @Override
                    public Article apply(Article article) throws Exception {
                        if (article.getUrlToImage() != null) {
                            URL url = new URL(article.getUrlToImage());
                            InputStream input = url.openStream();
                            try {
                                String path = mCachePath + url.getPath();
                                OutputStream output = new FileOutputStream(path);
                                try {
                                    byte[] buffer = new byte[2024];
                                    int bytesRead = 0;
                                    while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                                        output.write(buffer, 0, bytesRead);
                                    }
                                } finally {
                                    article.setUrlToImage(path);
                                    output.close();
                                }
                            } finally {
                                input.close();
                            }
                        }
                        return article;
                    }
                })
                .flatMap(new Function<Article, SingleSource<News>>() {
                    @Override
                    public SingleSource<News> apply(Article article) throws Exception {
                        News news = new News();
                        news.setAuthor(article.getAuthor());
                        news.setDescription(article.getDescription());
                        news.setPublishedAt(article.getPublishedAt());
                        news.setTitle(article.getTitle());
                        news.setUrl(article.getUrl());
                        return Single.just(news);
                    }
                })
                .flatMapCompletable(new Function<News, Completable>() {
                    @Override
                    public Completable apply(News news) throws Exception {
                        Realm realm = Realm.getInstance(mConfig);
                        realm.beginTransaction();
                        long id;
                        try {
                            id = realm.where(News.class).max("id").longValue() + 1;
                        } catch (Exception e) {
                            id = 0L;
                        }
                        news.setId(id);
                        realm.copyToRealm(news);
                        realm.commitTransaction();
                        realm.close();
                        return Completable.complete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable delete(long id) {
        return Single.just(id)
                .flatMapMaybe(new Function<Long, MaybeSource<String>>() {
                    @Override
                    public MaybeSource<String> apply(Long id) throws Exception {
                        String path = "";
                        Realm realm = Realm.getInstance(mConfig);
                        realm.beginTransaction();
                        News news = realm.where(News.class).equalTo("id", id).findFirst();
                        if (news != null) {
                            path = news.getPathToImage();
                            news.deleteFromRealm();
                        } else {
                            realm.close();
                        }
                        realm.commitTransaction();
                        realm.close();
                        if (path == null) {
                            return Maybe.empty();
                        } else {
                            return Maybe.just(path);
                        }
                    }
                })
                .flatMapCompletable(new Function<String, Completable>() {
                    @Override
                    public Completable apply(String path) throws Exception {
                        if (path != null) {
                            File myFile = new File(path);
                            if (myFile.exists()) {
                                myFile.delete();
                            }
                        }
                        return Completable.complete();
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Article>> getAll() {
        return Observable.create(new ObservableOnSubscribe<List<Article>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Article>> e) throws Exception {
                Realm realm = Realm.getInstance(mConfig);
                realm.beginTransaction();
                RealmResults<News> newsList = realm.where(News.class)
                        .findAll()
                        .sort("id", Sort.DESCENDING);
                List<Article> articles = new ArrayList<>(newsList.size());
                for (News news : newsList) {
                    Article article = new Article();
                    article.setAuthor(news.getAuthor());
                    article.setTitle(news.getTitle());
                    article.setDescription(news.getDescription());
                    article.setId(news.getId());
                    article.setPublishedAt(news.getPublishedAt());
                    article.setUrl(news.getUrl());
                    article.setUrlToImage(news.getPathToImage());
                    articles.add(article);
                }
                realm.commitTransaction();
                realm.close();
                if (!e.isDisposed()) {
                    e.onNext(articles);
                    e.onComplete();
                }
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
