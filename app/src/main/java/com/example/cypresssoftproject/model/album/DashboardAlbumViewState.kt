package com.example.cypresssoftproject.model.album

import com.example.cypresssoftproject.base.BaseViewState
import com.example.cypresssoftproject.base.Status

class DashboardAlbumViewState(
    val status: Status,
    val error: String? = null,
    val response: List<DashboardAlbumResponse>? = null
) : BaseViewState(status, error)