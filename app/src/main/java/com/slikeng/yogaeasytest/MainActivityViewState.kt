package com.slikeng.yogaeasytest

import androidx.annotation.StringRes

internal data class MainActivityViewState(
    val isShowingInputError: Boolean = false,
    @StringRes val inputErrorRes: Int?,
    val isShowingProgressBar: Boolean = true,
    val isShowingRecyclerView: Boolean = false
) : ViewModelState