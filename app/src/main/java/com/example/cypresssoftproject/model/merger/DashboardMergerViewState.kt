package com.example.cypresssoftproject.model.merger

import com.example.cypresssoftproject.base.BaseViewState
import com.example.cypresssoftproject.base.Status

class DashboardMergerViewState(
    val status: Status,
    val error: String? = null,
    val response: List<DashboardMergerResponse>? = null
) : BaseViewState(status, error)