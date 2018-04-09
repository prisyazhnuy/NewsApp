package com.prisyazhnuy.newsapp.flow.sort

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.prisyazhnuy.newsapp.R
import com.prisyazhnuy.newsapp.base.BaseLifecycleFragment
import com.prisyazhnuy.newsapp.dataKotlin.preferences.PreferencesRepository

/**
 * max.pr on 05.04.2018.
 */
class SortKotlinFragment : BaseLifecycleFragment<SortViewModel>() {
    override val viewModelClass = SortViewModel::class.java

    lateinit var rGroupSort: RadioGroup

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sort, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rGroupSort = view.findViewById(R.id.rgSort)
        rGroupSort.setOnCheckedChangeListener { group, id ->
            run {
                when (id) {
                    R.id.rBtnDate -> viewModel.setPublishedDateSort()
                    R.id.rBtnPopularity -> viewModel.setPopularitySort()
                }
            }
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.getSort()?.observe(this, Observer<String> {
            when (it) {
                "publishedAt" -> {
                    rGroupSort.check(R.id.rBtnDate)
                    Log.d("SortFragment", "byDate")
                }
                "popularity" -> {
                    rGroupSort.check(R.id.rBtnPopularity)
                    Log.d("SortFragment", "byPopularity")
                }
            }
        })
//
//        viewModel.dateSortLiveData.observe(this, Observer<Boolean> {
//            if (it?.not() == false) rGroupSort.check(R.id.rBtnDate)
//        })
//        viewModel.popularitySortLiveData.observe(this, Observer<Boolean> {
//            if (it?.not() == false) rGroupSort.check(R.id.rBtnPopularity)
//        })
    }
}