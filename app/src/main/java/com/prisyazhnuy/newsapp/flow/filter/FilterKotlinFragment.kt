package com.prisyazhnuy.newsapp.flow.filter

import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.prisyazhnuy.newsapp.R
import com.prisyazhnuy.newsapp.base.BaseLifecycleFragment
import java.util.*

/**
 * max.pr on 08.04.2018.
 */
class FilterKotlinFragment: BaseLifecycleFragment<FilterViewModel>() {
    override val viewModelClass = FilterViewModel::class.java
    private var lvSources: ListView? = null
    private var tvFrom: TextView? = null
    private var tvTo: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lvSources = view.findViewById(R.id.lvSources)
        tvFrom = view.findViewById(R.id.tvDateFrom)
        tvFrom?.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(context, DatePickerDialog.OnDateSetListener {
                datePicker, y, m, d -> viewModel.setFromDate(y, m, d)
            }, year, month, day)
            datePicker.show()
        }
        tvTo = view.findViewById(R.id.tvDateTo)
        tvTo?.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(context, DatePickerDialog.OnDateSetListener {
                datePicker, y, m, d -> viewModel.setToDate(y, m, d)
            }, year, month, day)
            datePicker.show()
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.filterLiveData.observe(this, Observer<Filter> {
            it?.let {
                tvFrom?.text = it.from
                tvTo?.text = it.to
            }
        })
    }
}