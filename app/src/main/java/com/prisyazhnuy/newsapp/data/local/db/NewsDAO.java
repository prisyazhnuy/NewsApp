package com.prisyazhnuy.newsapp.data.local.db;

import com.prisyazhnuy.newsapp.data.pojo.Article;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * max.pr on 04.03.2018.
 */

public interface NewsDAO {

    Completable insert(Article article);

    Completable delete(long id);

    Observable<List<Article>> getAll();
}
