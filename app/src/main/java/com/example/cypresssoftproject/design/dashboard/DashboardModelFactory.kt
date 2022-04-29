package com.example.cypresssoftproject.design.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cypresssoftproject.repository.DashboardRepository

class DashboardModelFactory(
    private val context: DashboardActivity,
    private val repository: DashboardRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DashboardActivityViewModel::class.java)) {
            DashboardActivityViewModel(
                context,
                this.repository
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}