package com.prisyazhnuy.newsapp.flow.newsList

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.prisyazhnuy.newsapp.R
import com.prisyazhnuy.newsapp.base.BaseLifecycleFragment
import com.prisyazhnuy.newsapp.dataKotlin.database.entity.NewsEntity
import com.prisyazhnuy.newsapp.dataKotlin.models.News
import com.prisyazhnuy.newsapp.flow.filter.Filter
import com.prisyazhnuy.newsapp.flow.filter.FilterViewModel
import com.prisyazhnuy.newsapp.flow.sort.SortViewModel

/**
 * max.pr on 01.04.2018.
 */
class NewsListKotlinFragment : BaseLifecycleFragment<NewsViewModel>(), NewsAdapter.NewsIterationListener {

    companion object {
        val VISIBLE_THRESHOLD = 5
    }

    override val viewModelClass = NewsViewModel::class.java
    val sortViewModel: SortViewModel by lazy { ViewModelProviders.of(this).get(SortViewModel::class.java) }
    val filterViewModel: FilterViewModel by lazy {ViewModelProviders.of(this).get(FilterViewModel::class.java)}
    private var recyclerView: RecyclerView? = null
    private val adapter = NewsAdapter(this)
    private var totalItemCount: Int = 0
    private var lastVisibleItem: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.list)
        initRecyclerView()
        recyclerView?.adapter = adapter

        if (savedInstanceState == null) {
//            viewModel.setFilter(Filter("lenta", null, null))
//            viewModel.loadFavourite()
        }

        observeLiveData()
    }

    private fun initRecyclerView() {
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = recyclerView?.layoutManager?.itemCount ?: 0
                lastVisibleItem = (recyclerView?.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                    viewModel.increasePage()
                }
            }
        })
    }

    private fun observeLiveData() {
        viewModel.isLoadingLiveData.observe(this, Observer<Boolean> {
            Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
        })
        viewModel.newsLiveData.observe(this, Observer<List<News>> {
            it?.let {
                adapter.dataSource = it as MutableList<News>
            }
        })
        viewModel.throwableLiveData.observe(this, Observer<Throwable?> {
            it?.let { Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show() }
        })
//        sortViewModel.getSort().observe(this, Observer<String> {
//            adapter.clean()
//            viewModel.setSort(it)
//        })
//        filterViewModel.filterLiveData.observe(this, Observer<Filter> {
//            adapter.clean()
//            viewModel.setFilter(it)
//        })
    }

    override fun shareNews(news: News) {
        val content = ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(news.url))
                .build()
        val shareDialog = ShareDialog(this)
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC)
    }

    override fun setFavourite(news: News, isFavourite: Boolean) {
        viewModel.setFavourite(news, isFavourite)
    }
}