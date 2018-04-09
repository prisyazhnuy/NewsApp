package com.prisyazhnuy.newsapp.base

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * max.pr on 31.03.2018.
 */
abstract class BaseViewHolder<T>(viewItem: View?) : RecyclerView.ViewHolder(viewItem) {
    abstract fun onBind(item: T)
}