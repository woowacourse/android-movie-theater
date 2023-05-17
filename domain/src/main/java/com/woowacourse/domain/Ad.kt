package com.woowacourse.domain

data class Ad(
    val adImage: Int,
    val url: String,
    val viewType: ViewType = ViewType.AD,
)
