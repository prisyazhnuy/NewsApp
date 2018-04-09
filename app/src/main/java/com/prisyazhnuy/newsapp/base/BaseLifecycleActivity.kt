package com.prisyazhnuy.newsapp.base

import android.arch.lifecycle.*
import android.support.v7.app.AppCompatActivity

/**
 * max.pr on 01.04.2018.
 */
abstract class BaseLifecycleActivity<T: AndroidViewModel> : AppCompatActivity(), LifecycleRegistryOwner {
    abstract val viewModelClass: Class<T>

    protected val viewModel: T by lazy { ViewModelProviders.of(this).get(viewModelClass) }

    private val registry = LifecycleRegistry(this)

    override fun getLifecycle() = registry
}