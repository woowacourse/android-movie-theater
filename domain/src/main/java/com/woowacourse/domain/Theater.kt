package com.woowacourse.domain

data class Theater(
    val name: String,
    val schedules: List<ScreeningSchedule>,
)
