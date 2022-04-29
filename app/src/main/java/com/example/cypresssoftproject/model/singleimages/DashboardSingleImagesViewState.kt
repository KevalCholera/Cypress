package com.example.cypresssoftproject.model.singleimages

import com.example.cypresssoftproject.base.BaseViewState
import com.example.cypresssoftproject.base.Status

class DashboardSingleImagesViewState(
    val status: Status,
    val error: String? = null,
    val response: List<DashboardSingleImagesResponse>? = null
) : BaseViewState(status, error)