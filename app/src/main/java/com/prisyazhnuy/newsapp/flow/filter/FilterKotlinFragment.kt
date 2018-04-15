package com.prisyazhnuy.newsapp.flow.filter

import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.prisyazhnuy.newsapp.R
import com.prisyazhnuy.newsapp.base.BaseLifecycleFragment
import com.prisyazhnuy.newsapp.dataKotlin.models.Source
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

        if (savedInstanceState == null) {
            viewModel.loadSources()
        }
    }

    private fun observeLiveData() {
        viewModel.sourceLiveData.observe(this, Observer {sources ->
            lvSources?.let {
                val adapter = ArrayAdapter<Source>(context!!, android.R.layout.simple_list_item_multiple_choice, sources)
                it.adapter = adapter
            }
        })
        viewModel.sourceErrorLiveData.observe(this, Observer {
            Toast.makeText(context, it?.localizedMessage, Toast.LENGTH_LONG).show()
        })


        viewModel.filterLiveData.observe(this, Observer<Filter> {
            it?.let {
                tvFrom?.text = it.from
                tvTo?.text = it.to
            }
        })
    }
}