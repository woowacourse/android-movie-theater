package domain

import java.time.LocalDate

data class Movie(
    val imagePath: String,
    val title: String,
    val theaters: Theaters,
    val runningTime: Int,
    val description: String,
) {
}
