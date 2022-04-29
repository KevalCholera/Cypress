package com.example.cypresssoftproject.model.images

import com.example.cypresssoftproject.base.BaseViewState
import com.example.cypresssoftproject.base.Status

class DashboardImagesViewState(
    val status: Status,
    val error: String? = null,
    val response: List<DashboardImagesResponse>? = null
) : BaseViewState(status, error)