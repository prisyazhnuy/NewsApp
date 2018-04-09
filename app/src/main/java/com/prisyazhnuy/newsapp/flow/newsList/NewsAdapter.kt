package com.prisyazhnuy.newsapp.flow.newsList

import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.prisyazhnuy.newsapp.R
import com.prisyazhnuy.newsapp.base.BaseAdapter
import com.prisyazhnuy.newsapp.base.BaseViewHolder
import com.prisyazhnuy.newsapp.dataKotlin.entity.News

/**
 * max.pr on 01.04.2018.
 */
class NewsAdapter(val newsIteration: NewsIterationListener) : BaseAdapter<News, NewsAdapter.ViewHolder>() {
    override fun initiateViewHolder(view: View?): ViewHolder = ViewHolder(view, newsIteration)

    override fun getItemViewId(): Int = R.layout.news_item

    class ViewHolder(view: View?, val newsIteration: NewsIterationListener) : BaseViewHolder<News>(view) {

        val tvTitle by lazy { view?.findViewById<TextView>(R.id.tvTitle) }
        val ivIcon by lazy { view?.findViewById<ImageView>(R.id.ivIcon) }
        val tvDescription by lazy { view?.findViewById<TextView>(R.id.tvDescription) }
        val btnShare by lazy { view?.findViewById<ImageButton>(R.id.btnShare) }
        val cbFavourite by lazy { view?.findViewById<CheckBox>(R.id.cbIsFavourite) }

        override fun onBind(item: News) {
            tvTitle?.text = item.title
            tvDescription?.text = item.description
            ivIcon?.let {
                Glide
                        .with(itemView)
                        .load(item.urlToImage)
                        .into(it)
            }
            btnShare?.setOnClickListener { newsIteration.shareNews(item) }
            cbFavourite?.setOnCheckedChangeListener{ view, isChecked -> newsIteration.setFavourite(item, isChecked)}
        }

    }

    interface NewsIterationListener {
        fun shareNews(news: News)
        fun setFavourite(news: News, isFavourite: Boolean)
    }
}