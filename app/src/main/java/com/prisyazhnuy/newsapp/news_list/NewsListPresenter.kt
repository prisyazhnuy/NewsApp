package com.prisyazhnuy.newsapp.news_list

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.prisyazhnuy.newsapp.data.NewsRepository
import com.prisyazhnuy.newsapp.data.pojo.Article
import io.reactivex.disposables.CompositeDisposable

/**
 * max.pr on 18.03.2018.
 */
class NewsListPresenter(private val dataSource: NewsRepository) : MvpBasePresenter<NewsListContract.NewsListView>(), NewsListContract.NewsListPresenter {
    private val disposable = CompositeDisposable()

    override fun loadBreakNews() {
//        val subscribe = dataSource.getBreakingNews("")
//                .flatMap { t ->
//                    val articles: MutableList<Article> = arrayListOf()
//                    t!!.mapTo(articles) { Article(it) }
//                    Observable.just(articles)
//                }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { onNext -> ifViewAttached { view -> view.showNews(onNext) } }
//        disposable.add(subscribe)
    }

    override fun loadNextNews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadFavourites() {

    }

    override fun saveNews(item: Article?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(url: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun detachView() {
        disposable.clear()
        super.detachView()
    }
}