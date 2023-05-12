package com.example.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Movie(
    val imgUri: String,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val screeningTimes: List<LocalTime>,
    val runningTime: Int,
    val description: String
)
