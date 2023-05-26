package domain

import java.time.LocalTime

data class Theater(
    val name: String,
    val timeTable: List<LocalTime>
)
