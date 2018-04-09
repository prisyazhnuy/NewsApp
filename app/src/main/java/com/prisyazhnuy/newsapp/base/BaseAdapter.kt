package com.prisyazhnuy.newsapp.base

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * max.pr on 31.03.2018.
 */
abstract class BaseAdapter<T, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH>() {

    var dataSource = mutableListOf<T>()
        set(value) {
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun getItemCount() = dataSource.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(getItemViewId(), parent, false)
        return initiateViewHolder(view)
    }

    abstract fun initiateViewHolder(view: View?): VH

    abstract fun getItemViewId(): Int

    fun getItem(position: Int) = dataSource[position]

    fun clean() {
        val size = itemCount
        dataSource.clear()
        notifyItemRangeRemoved(0, size)
    }
}