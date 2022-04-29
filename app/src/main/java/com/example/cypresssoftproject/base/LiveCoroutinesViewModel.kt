package com.example.cypresssoftproject.base

import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers

abstract class LiveCoroutinesViewModel : ViewModel() {

    inline fun <T> launchOnViewModelScope(crossinline block: suspend () -> LiveData<T>): LiveData<T> {
        return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(block())
        }
    }

    abstract fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?)
    abstract fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?)
}