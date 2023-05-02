package com.woowacourse.domain

object BookHistories {
    private val _bookHistories = mutableListOf<MovieBookingSeatInfo>()
    val items: List<MovieBookingSeatInfo>
        get() = _bookHistories.toList()

    fun saveBookingInfo(info: MovieBookingSeatInfo) {
        _bookHistories.add(info)
    }
}
