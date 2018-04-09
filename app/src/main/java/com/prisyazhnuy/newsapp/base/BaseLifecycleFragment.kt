package com.prisyazhnuy.newsapp.base

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment

/**
 * max.pr on 01.04.2018.
 */
abstract class BaseLifecycleFragment<T : AndroidViewModel> : Fragment() {
    abstract val viewModelClass: Class<T>

    protected val viewModel: T by lazy { ViewModelProviders.of(this).get(viewModelClass) }

//    private val registry = LifecycleRegistry(this)
//
//    override fun getLifecycle() = registry
}